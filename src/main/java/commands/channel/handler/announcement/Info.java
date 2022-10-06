package repbot.commands.channel.handler.announcement;

import jdautil.interactions.slash.structure.handler.SlashHandler;
import jdautil.localization.util.Replacement;
import jdautil.util.MentionUtil;
import jdautil.wrapper.EventContext;
import repbot.dao.provider.Guilds;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Info implements SlashHandler {
    private final Guilds guilds;

    public Info(Guilds guilds) {
        this.guilds = guilds;
    }

    @Override
    public void onSlashCommand(SlashCommandInteractionEvent event, EventContext context) {
        var announcements = guilds.guild(event.getGuild()).settings().announcements();
        if (!announcements.isActive()) {
            event.reply(context.localize("command.channel.announcement.state.message.inactive")).queue();
            return;
        }
        if (announcements.isSameChannel()) {
            event.reply(context.localize("command.channel.announcement.location.message.samechannel")).queue();
            return;
        }

        event.reply(context.localize("command.channel.announcement.channel.message.set",
                Replacement.create("CHANNEL", MentionUtil.channel(announcements.channelId())))).queue();
    }
}
