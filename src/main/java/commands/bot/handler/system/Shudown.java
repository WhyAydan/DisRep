package repbot.commands.bot.handler.system;

import jdautil.interactions.slash.structure.handler.SlashHandler;
import jdautil.wrapper.EventContext;
import repbot.config.Configuration;
import repbot.util.LogNotify;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class Shudown implements SlashHandler {
    private static final Logger log = getLogger(Shudown.class);
    private final Configuration configuration;

    public Shudown(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void onSlashCommand(SlashCommandInteractionEvent event, EventContext context) {
        if (!configuration.baseSettings().isOwner(event.getUser().getIdLong())) {
            event.reply("No.").setEphemeral(true).queue();
            return;
        }
        log.info(LogNotify.STATUS, "Shutdown command received from {}. Shutting down.", event.getUser().getAsTag());
        event.reply("Initializing shutdown. Good bye :c").complete();
        System.exit(0);
    }
}
