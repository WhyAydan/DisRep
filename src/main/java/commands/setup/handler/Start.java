package repbot.commands.setup.handler;

import jdautil.conversation.Conversation;
import jdautil.conversation.builder.ConversationBuilder;
import jdautil.conversation.elements.ButtonDialog;
import jdautil.conversation.elements.ComponenAction;
import jdautil.conversation.elements.ConversationContext;
import jdautil.conversation.elements.Result;
import jdautil.conversation.elements.Step;
import jdautil.interactions.slash.structure.handler.SlashHandler;
import jdautil.localization.util.Format;
import jdautil.localization.util.Replacement;
import jdautil.parsing.ArgumentUtil;
import jdautil.parsing.DiscordResolver;
import jdautil.parsing.ValueParser;
import jdautil.wrapper.EventContext;
import repbot.dao.provider.Guilds;
import repbot.serialization.ThankwordsContainer;
import repbot.util.PermissionErrorHandler;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Start implements SlashHandler {
    private final Guilds guilds;
    private final ThankwordsContainer thankwordsContainer;

    public Start(Guilds guilds, ThankwordsContainer thankwordsContainer) {
        this.guilds = guilds;
        this.thankwordsContainer = thankwordsContainer;
    }

    @Override
    public void onSlashCommand(SlashCommandInteractionEvent event, EventContext context) {
        PermissionErrorHandler.assertPermissions(event.getChannel()
                                                      .asGuildMessageChannel(), Permission.MESSAGE_SEND, Permission.VIEW_CHANNEL);
        event.reply(context.localize("command.setup.message.starting")).queue();
        context.conversationService()
               .startDialog(event.getUser(), event.getChannel().asGuildMessageChannel(), getConversation(context));
    }

    private Conversation getConversation(EventContext context) {
        var builder = ConversationBuilder.builder(
                        Step.button("**$%s$**%n$%s$".formatted("command.setup.dialog.welcome", "command.setup.message.continueToProceed"),
                                    buttons -> buttons
                                            .add(Button.success("continue", "word.continue"), ctx -> Result.proceed(1)))
                            .build())
                .addStep(1, buildSelectLanguage(context))
                .addStep(3, buildRoles())
                .addStep(4, buildLoadDefaults())
                .addStep(5, buildChannels());

        return builder.build();
    }

    private Step buildSelectLanguage(EventContext context) {
        return Step.button("command.setup.message.language", but -> buildLanguageButtons(but, context))
                   .build();
    }

    private void buildLanguageButtons(ButtonDialog buttons, EventContext context) {
        for (var language : context.guildLocalizer().localizer().languages()) {
            buttons.add(Button.of(ButtonStyle.PRIMARY, language.getLocale(), language.getNativeName()),
                    con -> {
                        guilds.guild(con.getGuild()).settings().general().language(language);
                        con.reply(con.localize("command.locale.set.message.set",
                                Replacement.create("LOCALE", language.getNativeName()))).queue();
                        return Result.proceed(3);
                    });
        }
    }

    private Step buildRoles() {
        return Step
                .message("command.setup.message.roles".stripIndent(), this::buildRolesButton)
                .button(buttons -> buttons
                        .add(Button.success("done", "word.done"), ctx -> Result.proceed(4)))
                .build();
    }

    private Result buildRolesButton(ConversationContext context) {
        var args = ArgumentUtil.parseQuotedArgs(context.getContentRaw(), true);
        if (args.length != 2) {
            return responseInvalid(context, "command.setup.message.rolesformat");
        }
        var role = DiscordResolver.getRole(context.getGuild(), args[0]);
        if (role.isEmpty()) {
            return responseInvalid(context, "error.invalidRole");
        }
        var optionalReputation = ValueParser.parseInt(args[1]);
        return optionalReputation
                .map(reputation -> responseRolesSubAdded(context, role.get(), reputation))
                .orElseGet(() -> responseInvalid(context, "error.invalidNumber"));
    }

    @NotNull
    private Result responseRolesSubAdded(ConversationContext context, Role role, Integer reputation) {
        guilds.guild(context.getGuild()).settings().ranks().add(role, reputation);
        context.reply(context.localize("command.roles.add.message.added",
                       Replacement.createMention(role),
                       Replacement.create("POINTS", reputation, Format.BOLD)))
               .queue();
        return Result.freeze();
    }

    private Step buildLoadDefaults() {
        return Step.button("command.setup.message.loadDefaults",
                           this::buildLoadDefaultsButton)
                   .build();
    }

    private void buildLoadDefaultsButton(ButtonDialog buttons) {
        var languages = thankwordsContainer.getAvailableLanguages();
        for (var language : languages) {
            buttons.add(Button.of(ButtonStyle.PRIMARY, language, language),
                    context -> {
                        var words = thankwordsContainer.get(language.toLowerCase(Locale.ROOT));
                        words.forEach(word -> guilds.guild(context.getGuild()).settings().thanking().thankwords()
                                                    .add(word));
                        var wordsJoined = words.stream().map(w -> StringUtils.wrap(w, "`"))
                                               .collect(Collectors.joining(", "));
                        context.reply(context.localize("command.thankwords.loaddefault.message.added") + wordsJoined)
                               .queue();
                        return Result.freeze();
                    });
        }
        buttons.add(Button.success("done", "word.done"), ctx -> Result.proceed(5));
    }

    private Step buildChannels() {
        return Step.button("command.setup.message.channels", this::buildChannelsButton)
                   .message(this::handleChannels)
                   .build();
    }

    private void buildChannelsButton(ButtonDialog buttons) {
        buttons.add(new ComponenAction(Button.success("done", "word.done"), ctx -> {
            ctx.reply(ctx.localize("command.setup.message.complete"))
               .queue();
            return Result.finish();
        })).add(Button.primary("all", "command.setup.message.allchannel"), ctx -> {
            var guild = ctx.getGuild();
            guilds.guild(guild).settings().thanking().channels().listType(false);
            ctx.reply(ctx.localize("command.channel.list.message.blacklist")).queue();
            return Result.finish();
        });
    }

    private Result handleChannels(ConversationContext context) {
        var args = context.getContentRaw().replaceAll("\\s+", " ").split("\\s");
        var channels = DiscordResolver.getTextChannels(context.getGuild(), List.of(args));
        var addedChannel = channels.stream()
                                   .map(channel -> {
                                       guilds.guild(context.getGuild()).settings().thanking().channels().add(channel);
                                       return channel.getAsMention();
                                   })
                                   .collect(Collectors.joining(", "));
        context.reply(
                       context.localize("command.channel.add.message.added",
                               Replacement.create("CHANNEL", addedChannel)))
               .mention(Collections.emptyList())
               .queue();
        return Result.freeze();
    }

    @NotNull
    private Result responseInvalid(ConversationContext context, String s) {
        context.reply(context.localize(s)).queue();
        return Result.freeze();
    }
}
