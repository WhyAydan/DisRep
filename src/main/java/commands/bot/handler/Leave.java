package repbot.commands.bot.handler;

import jdautil.interactions.slash.structure.handler.SlashHandler;
import jdautil.util.Guilds;
import jdautil.wrapper.EventContext;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Leave implements SlashHandler {
    @Override
    public void onSlashCommand(SlashCommandInteractionEvent event, EventContext context) {
        var guild = event.getJDA().getShardManager().getGuildById(event.getOption("guild_id").getAsLong());

        if (guild == null) {
            event.reply("Guild not present.").setEphemeral(true).queue();
            return;
        }

        event.reply("Leaving guild " + Guilds.prettyName(guild)).setEphemeral(true).queue();
        guild.leave().queue();
    }
}
