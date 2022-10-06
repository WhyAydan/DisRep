package repbot.commands.bot.handler.system;

import jdautil.interactions.slash.structure.handler.SlashHandler;
import jdautil.wrapper.EventContext;
import repbot.config.Configuration;
import repbot.util.LogNotify;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class Restart implements SlashHandler {
    private static final Logger log = getLogger(Restart.class);
    private final Configuration configuration;

    public Restart(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void onSlashCommand(SlashCommandInteractionEvent event, EventContext context) {
        if (!configuration.baseSettings().isOwner(event.getUser().getIdLong())) {
            event.reply("No.").setEphemeral(true).queue();
            return;
        }
        log.info(LogNotify.STATUS, "Restart command received from {}. Attempting restart.", event.getUser().getAsTag());
        event.reply("Restarting. Will be back soon!").complete();
        System.exit(10);
    }
}
