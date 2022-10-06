package repbot.commands.abuseprotection.handler;

import jdautil.interactions.slash.structure.handler.SlashHandler;
import jdautil.localization.util.Replacement;
import jdautil.wrapper.EventContext;
import repbot.dao.provider.Guilds;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class MinMessages implements SlashHandler {
    private final Guilds guilds;

    public MinMessages(Guilds guilds) {
        this.guilds = guilds;
    }

    @Override
    public void onSlashCommand(SlashCommandInteractionEvent event, EventContext context) {
        var guild = guilds.guild(event.getGuild());
        var abuseSettings = guild.settings().abuseProtection();
        if (event.getOptions().isEmpty()) {
            event.reply(context.localize("command.abuseprotection.minmessages.message.get",
                    Replacement.create("AMOUNT", abuseSettings.minMessages()))).queue();
            return;
        }
        var minMessages = event.getOption("messages").getAsLong();

        minMessages = Math.max(0, Math.min(minMessages, 100));
        event.reply(context.localize("command.abuseprotection.minmessages.message.get",
                Replacement.create("AMOUNT", abuseSettings.minMessages((int) minMessages)))).queue();
    }
}
