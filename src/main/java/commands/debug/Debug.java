package repbot.commands.debug;

import jdautil.interactions.slash.Slash;
import jdautil.interactions.slash.provider.SlashCommand;
import repbot.commands.debug.handler.Show;
import repbot.dao.provider.Guilds;

public class Debug extends SlashCommand {

    public Debug(Guilds guilds) {
        super(Slash.of("debug", "command.debug.description")
                .guildOnly()
                .adminCommand()
                .command(new Show(guilds)));
    }
}
