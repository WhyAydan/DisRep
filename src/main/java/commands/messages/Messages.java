package repbot.commands.messages;

import jdautil.interactions.slash.Slash;
import jdautil.interactions.slash.SubCommand;
import jdautil.interactions.slash.provider.SlashCommand;
import repbot.commands.messages.handler.States;
import repbot.dao.provider.Guilds;

public class Messages extends SlashCommand {
    public Messages(Guilds guilds) {
        super(Slash.of("messages", "command.messages.description")
                .guildOnly()
                .adminCommand()
                .subCommand(SubCommand.of("states", "command.messages.states.description")
                        .handler(new States(guilds)))
        );
    }
}
