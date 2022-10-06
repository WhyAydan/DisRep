package repbot.commands.abuseprotection.handler;

import jdautil.interactions.slash.structure.handler.SlashHandler;
import jdautil.localization.util.Replacement;
import jdautil.wrapper.EventContext;
import repbot.dao.provider.Guilds;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Cooldown implements SlashHandler {
    private final Guilds guilds;

    public Cooldown(Guilds guilds) {
        this.guilds = guilds;
    }

    @Override
    public void onSlashCommand(SlashCommandInteractionEvent event, EventContext context) {
        var guild = guilds.guild(event.getGuild());
        var abuseSettings = guild.settings().abuseProtection();
        if (event.getOptions().isEmpty()) {
            event.reply(context.localize("command.abuseprotection.cooldown.message.get",
                    Replacement.create("MINUTES", abuseSettings.cooldown()))).queue();
            return;
        }
        var cooldown = event.getOption("minutes").getAsLong();

        event.reply(context.localize("command.abuseprotection.cooldown.message.set",
                Replacement.create("MINUTES", abuseSettings.cooldown((int) cooldown)))).queue();

    }
}
