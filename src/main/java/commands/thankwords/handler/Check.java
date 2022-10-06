package repbot.commands.thankwords.handler;

import jdautil.interactions.slash.structure.handler.SlashHandler;
import jdautil.localization.util.LocalizedEmbedBuilder;
import jdautil.localization.util.Replacement;
import jdautil.parsing.Verifier;
import jdautil.wrapper.EventContext;
import repbot.analyzer.MessageAnalyzer;
import repbot.analyzer.results.match.MatchResult;
import repbot.analyzer.results.match.ThankType;
import repbot.dao.provider.Guilds;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Check implements SlashHandler {
    private final Guilds guilds;
    private final MessageAnalyzer messageAnalyzer;

    public Check(Guilds guilds, MessageAnalyzer messageAnalyzer) {
        this.guilds = guilds;
        this.messageAnalyzer = messageAnalyzer;
    }

    @Override
    public void onSlashCommand(SlashCommandInteractionEvent event, EventContext context) {
        var settings = guilds.guild(event.getGuild()).settings();
        var guildSettings = settings.thanking().thankwords();
        var messageId = event.getOption("message").getAsString();

        if (!Verifier.isValidId(messageId)) {
            event.reply(context.localize("error.invalidMessage")).queue();
            return;
        }

        var message = event.getChannel().retrieveMessageById(messageId).complete();
        var result = messageAnalyzer.processMessage(guildSettings.thankwordPattern(), message, settings, true, settings.abuseProtection()
                                                                                                                       .maxMessageReputation());
        if (result.isEmpty()) {
            event.reply(context.localize("command.thankwords.check.message.noMatch")).queue();
            return;
        }

        var builder = new LocalizedEmbedBuilder(context.guildLocalizer());
        processMessage(result.asMatch(), builder);
        event.replyEmbeds(builder.build()).queue();
    }

    private void processMessage(MatchResult result, LocalizedEmbedBuilder builder) {
        if (result.thankType() == ThankType.FUZZY) {
            for (var receiver : result.asFuzzy().weightedReceiver()) {
                builder.addField("command.thankwords.check.message.fuzzy",
                        "$%s$%n$%s$".formatted("command.thankwords.check.message.result", "command.thankwords.check.message.confidence"),
                        false, Replacement.create("DONATOR", result.donor().getAsMention()),
                        Replacement.create("RECEIVER", receiver.getReference().getAsMention()),
                        Replacement.create("SCORE", String.format("%.3f", receiver.getWeight())));

            }
        } else {
            for (var receiver : result.receivers()) {
                switch (result.thankType()) {
                    case MENTION -> builder.addField("command.thankwords.check.message.mention",
                            "command.thankwords.check.message.result",
                            false, Replacement.create("DONATOR", result.donor().getAsMention()),
                            Replacement.create("RECEIVER", receiver.getAsMention()));
                    case ANSWER -> builder.addField("command.thankwords.check.message.answer",
                            "$%s$%n$%s$".formatted("command.thankwords.check.message.result", "command.thankwords.check.message.reference"),
                            false, Replacement.create("URL", result.asAnswer().referenceMessage().getJumpUrl()),
                            Replacement.create("DONATOR", result.donor().getAsMention()),
                            Replacement.create("RECEIVER", receiver.getAsMention()));
                }
            }
        }
    }
}
