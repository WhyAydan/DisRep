package repbot.commands.roles;

import jdautil.interactions.slash.Argument;
import jdautil.interactions.slash.Slash;
import jdautil.interactions.slash.SubCommand;
import jdautil.interactions.slash.provider.SlashProvider;
import repbot.commands.roles.handler.Add;
import repbot.commands.roles.handler.AddDonor;
import repbot.commands.roles.handler.AddReceiver;
import repbot.commands.roles.handler.List;
import repbot.commands.roles.handler.Refresh;
import repbot.commands.roles.handler.Remove;
import repbot.commands.roles.handler.RemoveDonor;
import repbot.commands.roles.handler.RemoveReceiver;
import repbot.commands.roles.handler.StackRoles;
import repbot.dao.provider.Guilds;
import repbot.service.RoleAssigner;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;

public class Roles implements SlashProvider<Slash> {
    private final Refresh refresh;
    private final Guilds guilds;

    public Roles(Guilds guilds, RoleAssigner roleAssigner) {
        this.guilds = guilds;
        refresh = new Refresh(roleAssigner);
    }

    @Override
    public Slash slash() {
        return Slash.of("roles", "command.roles.description")
                .guildOnly()
                .withPermission(Permission.MANAGE_ROLES)
                .subCommand(SubCommand.of("add", "command.roles.add.description")
                        .handler(new Add(refresh, guilds))
                        .argument(Argument.role("role", "command.roles.add.role.description").asRequired())
                        .argument(Argument.integer("reputation", "command.roles.add.reputation.description")
                                          .asRequired()))
                .subCommand(SubCommand.of("remove", "command.roles.remove.description")
                        .handler(new Remove(refresh, guilds))
                        .argument(Argument.role("role", "command.roles.remove.role.description").asRequired()))
                .subCommand(SubCommand.of("adddonor", "command.roles.adddonor.description")
                        .handler(new AddDonor(guilds))
                        .argument(Argument.role("role", "command.roles.adddonor.role.description").asRequired()))
                .subCommand(SubCommand.of("addreceiver", "command.roles.addreceiver.description")
                        .handler(new AddReceiver(guilds))
                        .argument(Argument.role("role", "command.roles.addreceiver.role.description").asRequired()))
                .subCommand(SubCommand.of("removedonor", "command.roles.removedonor.description")
                        .handler(new RemoveDonor(guilds))
                        .argument(Argument.role("role", "command.roles.removedonor.role.description").asRequired()))
                .subCommand(SubCommand.of("removereceiver", "command.roles.removereceiver.description")
                        .handler(new RemoveReceiver(guilds))
                        .argument(Argument.role("role", "command.roles.removereceiver.role.description").asRequired()))
                .subCommand(SubCommand.of("refresh", "command.roles.refresh.description")
                        .handler(refresh))
                .subCommand(SubCommand.of("list", "command.roles.list.description")
                        .handler(new List(guilds)))
                .subCommand(SubCommand.of("stackroles", "command.roles.stackroles.description")
                        .handler(new StackRoles(refresh, guilds))
                        .argument(Argument.bool("stack", "command.roles.stackroles.stack.description"))
                ).build();
    }

    public boolean refreshActive(Guild guild) {
        return refresh.refreshActive(guild);
    }
}
