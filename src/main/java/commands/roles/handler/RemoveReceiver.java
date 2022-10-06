package repbot.commands.roles.handler;

import jdautil.interactions.slash.structure.handler.SlashHandler;
import jdautil.localization.util.Replacement;
import jdautil.wrapper.EventContext;
import repbot.dao.provider.Guilds;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.Collections;

public class RemoveReceiver implements SlashHandler {
    private final Guilds guilds;

    public RemoveReceiver(Guilds guilds) {
        this.guilds = guilds;
    }

    @Override
    public void onSlashCommand(SlashCommandInteractionEvent event, EventContext context) {
        var role = event.getOption("role").getAsRole();
        guilds.guild(event.getGuild()).settings().thanking().receiverRoles().remove(role);
        event.reply(context.localize("command.roles.removereceiver.message.remove",
                Replacement.createMention(role))).mention(Collections.emptyList()).queue();
    }
}
