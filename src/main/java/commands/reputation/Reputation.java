package repbot.commands.reputation;

import jdautil.interactions.slash.Argument;
import jdautil.interactions.slash.Slash;
import jdautil.interactions.slash.provider.SlashCommand;
import repbot.commands.reputation.handler.Profile;
import repbot.config.Configuration;
import repbot.dao.provider.Guilds;
import repbot.service.RoleAssigner;

public class Reputation extends SlashCommand {
    public Reputation(Guilds guilds, Configuration configuration, RoleAssigner roleAssigner) {
        super(Slash.of("rep", "command.rep.description")
                .guildOnly()
                .command(new Profile(guilds, configuration, roleAssigner))
                .argument(Argument.user("user", "command.rep.user.description"))
        );
    }
}
