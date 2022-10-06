package repbot.commands.scan.handler;

import jdautil.interactions.slash.structure.handler.SlashHandler;
import jdautil.wrapper.EventContext;
import repbot.commands.scan.util.Scanner;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Cancel implements SlashHandler {
    private final Scanner scanner;

    public Cancel(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void onSlashCommand(SlashCommandInteractionEvent event, EventContext context) {
        if (!scanner.isActive(event.getGuild())) {
            event.reply(context.localize("command.scan.cancel.message.notask")).setEphemeral(true).queue();
            return;
        }
        event.reply(context.localize("command.scan.cancel.message.canceling")).queue();
        scanner.cancelScan(event.getGuild());
    }
}
