package repbot.commands.abuseprotection.handler;

import jdautil.interactions.slash.structure.handler.SlashHandler;
import jdautil.localization.util.Replacement;
import jdautil.wrapper.EventContext;
import repbot.dao.provider.Guilds;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class DonorLimit implements SlashHandler {
    private final Guilds guilds;

    public DonorLimit(Guilds guilds) {
        this.guilds = guilds;
    }

    @Override
    public void onSlashCommand(SlashCommandInteractionEvent event, EventContext context) {
        var guild = guilds.guild(event.getGuild());
        var protection = guild.settings().abuseProtection();
        var limit = event.getOption("limit");
        if (limit != null) {
            protection.maxGiven(limit.getAsInt());
        }

        var hours = event.getOption("hours");
        if (hours != null) {
            protection.maxGivenHours(hours.getAsInt());
        }

        if (protection.maxGiven() == 0) {
            event.reply(context.localize("command.abuseprotection.donorlimit.message.disabled")).setEphemeral(true)
                 .queue();
            return;
        }

        event.reply(context.localize("command.abuseprotection.donorlimit.message.set",
                     Replacement.create("AMOUNT", protection.maxGiven()),
                     Replacement.create("HOURS", protection.maxGivenHours())))
             .setEphemeral(true)
             .queue();
    }
}
