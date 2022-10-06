package repbot.commands.repadmin.handler.resetdate;

import jdautil.interactions.slash.structure.handler.SlashHandler;
import jdautil.wrapper.EventContext;
import repbot.dao.provider.Guilds;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class RemoveResetDate implements SlashHandler {
    private final Guilds guilds;

    public RemoveResetDate(Guilds guilds) {
        this.guilds = guilds;
    }

    @Override
    public void onSlashCommand(SlashCommandInteractionEvent event, EventContext context) {
        guilds.guild(event.getGuild()).settings().general().resetDate(null);

        event.reply(context.localize("command.repadmin.resetdate.remove.message.removed")).setEphemeral(true).queue();
    }
}
