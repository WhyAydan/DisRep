package repbot.commands.prune;

import jdautil.interactions.slash.Argument;
import jdautil.interactions.slash.Slash;
import jdautil.interactions.slash.SubCommand;
import jdautil.interactions.slash.provider.SlashCommand;
import repbot.commands.prune.handler.Guild;
import repbot.commands.prune.handler.User;
import repbot.service.GdprService;
import net.dv8tion.jda.api.Permission;

public class Prune extends SlashCommand {

    public Prune(GdprService service) {
        super(Slash.of("prune", "command.prune.description")
                .guildOnly()
                .withPermission(Permission.MESSAGE_MANAGE)
                .subCommand(SubCommand.of("user", "command.prune.user.description")
                        .handler(new User(service))
                        .argument(Argument.user("user", "command.prune.user.user.description"))
                        .argument(Argument.text("userid", "command.prune.user.userid.description")))
                .subCommand(SubCommand.of("guild", "command.prune.guild.description")
                        .handler(new Guild(service)))
        );
    }
}
