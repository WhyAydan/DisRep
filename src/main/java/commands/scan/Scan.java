package repbot.commands.scan;

import jdautil.interactions.slash.Argument;
import jdautil.interactions.slash.Slash;
import jdautil.interactions.slash.SubCommand;
import jdautil.interactions.slash.provider.SlashProvider;
import repbot.analyzer.MessageAnalyzer;
import repbot.commands.scan.handler.Cancel;
import repbot.commands.scan.handler.Start;
import repbot.commands.scan.util.Scanner;
import repbot.config.Configuration;
import repbot.dao.provider.Guilds;
import net.dv8tion.jda.api.entities.Guild;

public class Scan implements SlashProvider<Slash> {

    private final Scanner scanner;

    public Scan(Guilds guilds, Configuration configuration) {
        scanner = new Scanner(guilds, configuration);
    }

    @Override
    public Slash slash() {
        return Slash.of("scan", "command.scan.description")
                .guildOnly()
                .adminCommand()
                .subCommand(SubCommand.of("start", "command.scan.start.description")
                        .handler(new Start(scanner))
                        .argument(Argument.channel("channel", "command.scan.start.channel.description"))
                        .argument(Argument.integer("numbermessages", "command.scan.start.numbermessages.description")))
                .subCommand(SubCommand.of("cancel", "command.scan.cancel.description")
                        .handler(new Cancel(scanner)))
                .build();
    }

    public void lateInit(MessageAnalyzer messageAnalyzer) {
        scanner.lateInit(messageAnalyzer);
    }

    public boolean isRunning(Guild guild) {
        return scanner.isRunning(guild);
    }
}
