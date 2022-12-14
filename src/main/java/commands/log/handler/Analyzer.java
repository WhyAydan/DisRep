package repbot.commands.log.handler;

import jdautil.interactions.slash.structure.handler.SlashHandler;
import jdautil.parsing.ValueParser;
import jdautil.wrapper.EventContext;
import repbot.dao.provider.Guilds;
import repbot.dao.snapshots.ResultEntry;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.Optional;

public class Analyzer implements SlashHandler {
    private final Guilds guilds;

    public Analyzer(Guilds guilds) {
        this.guilds = guilds;
    }

    @Override
    public void onSlashCommand(SlashCommandInteractionEvent event, EventContext context) {
        var optMessageId = ValueParser.parseLong(event.getOption("messageid").getAsString());
        if (optMessageId.isEmpty()) {
            event.reply(context.localize("error.invalidMessage")).setEphemeral(true).queue();
            return;
        }
        var resultEntry = guilds.guild(event.getGuild()).reputation().analyzer()
                                .get(optMessageId.get());

        if (resultEntry.isEmpty()) {
            event.reply(context.localize("command.log.analyzer.notanalyzed")).setEphemeral(true).queue();
            return;
        }

        var embed = resultEntry.get().embed(event.getGuild(), context);

        event.replyEmbeds(embed).setEphemeral(true).queue();
    }
}
