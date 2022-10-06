package repbot.commands.repsettings;

import jdautil.interactions.slash.Slash;
import jdautil.interactions.slash.SubCommand;
import jdautil.interactions.slash.provider.SlashCommand;
import repbot.commands.repsettings.handler.EmojiInfo;
import repbot.commands.repsettings.handler.Info;
import repbot.dao.provider.Guilds;

public class RepSettings extends SlashCommand {

    public RepSettings(Guilds guilds) {
        super(Slash.of("repsettings", "command.repsettings.description")
                .guildOnly()
                .adminCommand()
                .subCommand(SubCommand.of("info", "command.repsettings.info.description")
                        .handler(new Info(guilds)))
                .subCommand(SubCommand.of("emojidebug", "command.repsettings.emojidebug.description")
                        .handler(new EmojiInfo(guilds))));
    }
}
