package repbot.commands.reactions;

import jdautil.interactions.slash.Argument;
import jdautil.interactions.slash.Slash;
import jdautil.interactions.slash.SubCommand;
import jdautil.interactions.slash.provider.SlashCommand;
import repbot.commands.reactions.handler.Add;
import repbot.commands.reactions.handler.Info;
import repbot.commands.reactions.handler.Main;
import repbot.commands.reactions.handler.Remove;
import repbot.dao.provider.Guilds;

public class Reactions extends SlashCommand {
    public Reactions(Guilds guilds) {
        super(Slash.of("reactions", "command.reactions.description")
                .guildOnly()
                .adminCommand()
                .subCommand(SubCommand.of("main", "command.reactions.main.description")
                        .handler(new Main(guilds))
                        .argument(Argument.text("emote", "command.reactions.main.emote.description").asRequired()))
                .subCommand(SubCommand.of("add", "command.reactions.add.description")
                        .handler(new Add(guilds))
                        .argument(Argument.text("emote", "command.reactions.add.emote.description").asRequired()))
                .subCommand(SubCommand.of("remove", "command.reactions.remove.description")
                        .handler(new Remove(guilds))
                        .argument(Argument.text("emote", "command.reactions.remove.emote.description")
                                          .withAutoComplete().asRequired()))
                .subCommand(SubCommand.of("info", "command.reactions.info.description")
                        .handler(new Info(guilds)))
        );
    }
}
