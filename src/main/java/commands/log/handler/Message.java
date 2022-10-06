package repbot.commands.log.handler;

import jdautil.interactions.slash.structure.handler.SlashHandler;
import jdautil.localization.util.LocalizedEmbedBuilder;
import jdautil.localization.util.Replacement;
import jdautil.parsing.ValueParser;
import jdautil.wrapper.EventContext;
import repbot.dao.provider.Guilds;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import static repbot.commands.log.handler.LogFormatter.buildFields;
import static repbot.commands.log.handler.LogFormatter.mapMessageLogEntry;

public class Message implements SlashHandler {
    private final Guilds guilds;

    public Message(Guilds guilds) {
        this.guilds = guilds;
    }

    @Override
    public void onSlashCommand(SlashCommandInteractionEvent event, EventContext context) {
        event.getOption("messageid");
        var optMessageId = ValueParser.parseLong(event.getOption("messageid").getAsString());
        if (optMessageId.isEmpty()) {
            event.reply(context.localize("error.invalidMessage")).setEphemeral(true).queue();
            return;
        }

        event.replyEmbeds(getMessageLog(context, event.getGuild(), event.getOption("messageid").getAsLong()))
             .setEphemeral(true).queue();
    }

    private MessageEmbed getMessageLog(EventContext context, Guild guild, long messageId) {
        var messageLog = guilds.guild(guild).reputation().log().messageLog(messageId, 50);

        var log = mapMessageLogEntry(context, messageLog);

        var builder = new LocalizedEmbedBuilder(context.guildLocalizer())
                .setAuthor("command.log.message.message.log", Replacement.create("ID", messageId));
        buildFields(log, builder);
        return builder.build();
    }
}
