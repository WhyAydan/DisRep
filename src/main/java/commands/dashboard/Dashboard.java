package repbot.commands.dashboard;

import jdautil.interactions.slash.Slash;
import jdautil.interactions.slash.provider.SlashCommand;
import repbot.commands.dashboard.handler.Show;
import repbot.dao.provider.Guilds;

public class Dashboard extends SlashCommand {
    public Dashboard(Guilds guilds) {
        super(Slash.of("dashboard", "command.dashboard.description")
                .guildOnly()
                .command(new Show(guilds)));
    }
}
