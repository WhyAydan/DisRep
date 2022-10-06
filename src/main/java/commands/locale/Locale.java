package repbot.commands.locale;

import jdautil.interactions.slash.Argument;
import jdautil.interactions.slash.Slash;
import jdautil.interactions.slash.SubCommand;
import jdautil.interactions.slash.provider.SlashCommand;
import repbot.commands.locale.handler.List;
import repbot.commands.locale.handler.Reset;
import repbot.commands.locale.handler.Set;
import repbot.dao.provider.Guilds;

public class Locale extends SlashCommand {
    public Locale(Guilds guilds) {
        super(Slash.of("locale", "command.locale.description")
                .guildOnly()
                .adminCommand()
                .subCommand(SubCommand.of("set", "command.locale.set.description")
                        .handler(new Set(guilds))
                        .argument(Argument.text("language", "command.locale.set.language.description").asRequired()
                                          .withAutoComplete()))
                .subCommand(SubCommand.of("reset", "command.locale.reset.description")
                        .handler(new Reset(guilds)))
                .subCommand(SubCommand.of("list", "command.locale.list.description")
                        .handler(new List()))
        );
    }
}
