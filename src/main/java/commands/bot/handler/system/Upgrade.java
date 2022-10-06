package repbot.commands.bot.handler.system;

import jdautil.interactions.slash.structure.handler.SlashHandler;
import jdautil.wrapper.EventContext;
import repbot.config.Configuration;
import repbot.util.LogNotify;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class Upgrade implements SlashHandler {
    private static final Logger log = getLogger(Upgrade.class);
    private final Configuration configuration;

    public Upgrade(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void onSlashCommand(SlashCommandInteractionEvent event, EventContext context) {
        if (!configuration.baseSettings().isOwner(event.getUser().getIdLong())) {
            event.reply("No.").setEphemeral(true).queue();
            return;
        }
        log.info(LogNotify.STATUS, "Upgrade command received from {}. Attempting upgrade.", event.getUser().getAsTag());
        event.reply("Starting upgrade. Will be back soon!").complete();
        System.exit(20);
    }
}
