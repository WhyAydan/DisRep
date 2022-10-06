package repbot.commands.gdpr.handler;

import jdautil.interactions.slash.structure.handler.SlashHandler;
import jdautil.wrapper.EventContext;
import repbot.dao.access.Gdpr;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Delete implements SlashHandler {
    private final Gdpr gdpr;

    public Delete(Gdpr gdpr) {
        this.gdpr = gdpr;
    }

    @Override
    public void onSlashCommand(SlashCommandInteractionEvent event, EventContext context) {
        var success = gdpr.request(event.getUser()).queueDeletion();
        if (success) {
            event.reply(context.localize("command.gdpr.delete.message.received")).setEphemeral(true).queue();
        } else {
            event.reply(context.localize("command.gdpr.delete.message.scheduled")).setEphemeral(true).queue();
        }
    }
}
