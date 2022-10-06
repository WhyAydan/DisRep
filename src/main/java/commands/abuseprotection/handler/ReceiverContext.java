package repbot.commands.abuseprotection.handler;

import jdautil.interactions.slash.structure.handler.SlashHandler;
import jdautil.wrapper.EventContext;
import repbot.dao.provider.Guilds;
import repbot.util.Text;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class ReceiverContext implements SlashHandler {
    private final Guilds guilds;

    public ReceiverContext(Guilds guilds) {
        this.guilds = guilds;
    }

    @Override
    public void onSlashCommand(SlashCommandInteractionEvent event, EventContext context) {
        var guild = guilds.guild(event.getGuild());
        var abuseSettings = guild.settings().abuseProtection();
        if (event.getOptions().isEmpty()) {
            event.reply(Text.getBooleanMessage(context, abuseSettings.isReceiverContext(),
                         "command.abuseprotection.receivercontext.message.true", "command.abuseprotection.receivercontext.message.false"))
                 .queue();
            return;
        }
        var state = event.getOption("state").getAsBoolean();

        event.reply(Text.getBooleanMessage(context, abuseSettings.receiverContext(state),
                     "command.abuseprotection.receivercontext.message.true", "command.abuseprotection.receivercontext.message.false"))
             .queue();
    }
}
