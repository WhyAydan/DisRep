package repbot.commands.invite;

import jdautil.interactions.slash.Slash;
import jdautil.interactions.slash.provider.SlashCommand;
import repbot.commands.invite.handler.Show;
import repbot.config.Configuration;

public class Invite extends SlashCommand {
    public Invite(Configuration configuration) {
        super(Slash.of("invite", "command.invite.description")
                .command(new Show(configuration)));
    }
}
