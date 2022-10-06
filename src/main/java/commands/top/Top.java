package repbot.commands.top;

import jdautil.interactions.slash.Argument;
import jdautil.interactions.slash.Slash;
import jdautil.interactions.slash.provider.SlashCommand;
import repbot.commands.top.handler.Show;
import repbot.dao.provider.Guilds;

public class Top extends SlashCommand {
    public Top(Guilds guilds) {
        super(Slash.of("top", "command.top.description")
                .guildOnly()
                .command(new Show(guilds))
                .argument(Argument.text("mode", "command.top.mode.description").withAutoComplete()));
    }
}
