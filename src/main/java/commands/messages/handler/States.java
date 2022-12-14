package repbot.commands.messages.handler;

import jdautil.interactions.slash.structure.handler.SlashHandler;
import jdautil.localization.util.LocalizedEmbedBuilder;
import jdautil.menus.EntryContext;
import jdautil.menus.MenuAction;
import jdautil.menus.entries.MenuEntry;
import jdautil.wrapper.EventContext;
import repbot.dao.access.guild.settings.Settings;
import repbot.dao.provider.Guilds;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;

import java.awt.Color;
import java.util.Collections;
import java.util.function.Consumer;

public class States implements SlashHandler {
    private final Guilds guilds;

    public States(Guilds guilds) {
        this.guilds = guilds;
    }

    @Override
    public void onSlashCommand(SlashCommandInteractionEvent event, EventContext context) {
        var settings = guilds.guild(event.getGuild()).settings();
        var setting = SelectMenu.create("setting")
                .setPlaceholder("command.messages.states.message.choose")
                .setRequiredRange(1, 1)
                .addOption("command.messages.states.message.option.reactionconfirmation.name", "reaction_confirmation", "command.messages.states.message.option.reactionConfirmation.description")
                .build();
        var reactions = getMenu("reaction_confirmation",
                "command.messages.states.message.option.reactionconfirmation.name",
                "command.messages.states.message.choice.reactionConfirmation.true",
                "command.messages.states.message.choice.reactionConfirmation.false",
                settings.reputation().isReactionActive());

        context.registerMenu(MenuAction.forCallback(getSettings(context, settings), event)
                                       .addComponent(MenuEntry.of(setting, ctx -> {
                                           var option = ctx.event().getValues().get(0);
                                           var entry = ctx.container().entry(option).get();
                                           ctx.container().entries().forEach(MenuEntry::hidden);
                                           ctx.entry().visible(true);
                                           entry.visible(true);
                                           var copy = ctx.entry().component().createCopy();
                                           copy.setDefaultValues(Collections.singleton(option));
                                           ctx.entry().component(copy.build());
                                           ctx.refresh();
                                       }))
                                       .addComponent(MenuEntry.of(reactions, ctx -> refresh(ctx, res -> settings.messages()
                                                                                                                .reactionConfirmation(res), context, settings))
                                               .hidden())
                                       .asEphemeral()
                                       .build());
    }

    private SelectMenu getMenu(String id, String placeholder, String enabledDescr, String disabledDescr, boolean state) {
        return SelectMenu.create(id)
                .setPlaceholder(placeholder)
                .setRequiredRange(1, 1)
                .addOption("words.enabled", "enabled", enabledDescr)
                .addOption("words.disabled", "disabled", disabledDescr)
                .setDefaultValues(Collections.singleton(state ? "enabled" : "disabled"))
                .build();
    }

    private void refresh(EntryContext<SelectMenuInteractionEvent, SelectMenu> ctx, Consumer<Boolean> result, EventContext context, Settings guildSettings) {
        var value = ctx.event().getValues().get(0);
        var copy = ctx.entry().component().createCopy();
        copy.setDefaultValues(Collections.singleton(value));
        result.accept("enabled".equals(value));
        var settings = getSettings(context, guildSettings);
        ctx.entry().component(copy.build());
        ctx.refresh(settings);
    }

    private MessageEmbed getSettings(EventContext context, Settings guildSettings) {
        var messages = guildSettings.messages();

        return new LocalizedEmbedBuilder(context.guildLocalizer())
                .setTitle("command.messages.states.message.title")
                .appendDescription(messages.toLocalizedString())
                .setColor(Color.GREEN)
                .build();
    }
}
