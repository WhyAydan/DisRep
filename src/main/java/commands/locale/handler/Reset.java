package repbot.commands.locale.handler;

import jdautil.interactions.slash.structure.handler.SlashHandler;
import jdautil.wrapper.EventContext;
import repbot.dao.provider.Guilds;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Reset implements SlashHandler {
    private final Guilds guilds;

    public Reset(Guilds guilds) {
        this.guilds = guilds;
    }

    @Override
    public void onSlashCommand(SlashCommandInteractionEvent event, EventContext context) {
        guilds.guild(event.getGuild()).settings().general().language(null);
        event.reply("command.locale.reset.message.changed").queue();
    }
}
