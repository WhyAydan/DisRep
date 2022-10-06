package repbot.commands.repadmin.handler;

import jdautil.interactions.slash.structure.handler.SlashHandler;
import jdautil.wrapper.EventContext;
import repbot.config.Configuration;
import repbot.dao.provider.Guilds;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Profile implements SlashHandler {
    private final Guilds guilds;
    private final Configuration configuration;

    public Profile(Guilds guilds, Configuration configuration) {
        this.guilds = guilds;
        this.configuration = configuration;
    }

    @Override
    public void onSlashCommand(SlashCommandInteractionEvent event, EventContext context) {
        var user = guilds.guild(event.getGuild()).reputation().user(event.getOption("user").getAsMember());
        var profile = user.profile().adminProfile(configuration, context.guildLocalizer());
        event.replyEmbeds(profile).setEphemeral(true).queue();
    }
}
