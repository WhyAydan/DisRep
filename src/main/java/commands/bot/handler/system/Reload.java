package repbot.commands.bot.handler.system;

import jdautil.interactions.slash.structure.handler.SlashHandler;
import jdautil.wrapper.EventContext;
import repbot.config.Configuration;
import repbot.config.exception.ConfigurationException;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Reload implements SlashHandler {

    private final Configuration configuration;

    public Reload(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void onSlashCommand(SlashCommandInteractionEvent event, EventContext context) {
        try {
            configuration.reload();
        } catch (ConfigurationException e) {
            event.reply("Config reload failed").setEphemeral(true).queue();
            return;
        }
        event.reply("Config file reloaded.").setEphemeral(true).queue();
    }
}
