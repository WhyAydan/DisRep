package repbot.commands.channel.handler.announcement;

import jdautil.interactions.slash.structure.handler.SlashHandler;
import jdautil.util.Completion;
import jdautil.wrapper.EventContext;
import repbot.dao.provider.Guilds;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

public class Where implements SlashHandler {
    private final Guilds guilds;

    public Where(Guilds guilds) {
        this.guilds = guilds;
    }

    @Override
    public void onSlashCommand(SlashCommandInteractionEvent event, EventContext context) {
        var announcements = guilds.guild(event.getGuild()).settings().announcements();
        if (announcements.sameChannel("same channel".equalsIgnoreCase(event.getOption("where", OptionMapping::getAsString)))) {
            event.reply(context.localize("command.channel.announcement.location.message.samechannel")).queue();
        } else {
            event.reply(context.localize("command.channel.announcement.location.message.otherchannel")).queue();
        }
    }

    @Override
    public void onAutoComplete(CommandAutoCompleteInteractionEvent event, EventContext context) {
        if ("where".equals(event.getFocusedOption().getName())) {
            event.replyChoices(Completion.complete("", "same channel", "custom channel")).queue();
        }
    }
}
