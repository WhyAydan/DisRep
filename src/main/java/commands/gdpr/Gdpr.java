package repbot.commands.gdpr;

import jdautil.interactions.slash.Slash;
import jdautil.interactions.slash.SubCommand;
import jdautil.interactions.slash.provider.SlashCommand;
import repbot.commands.gdpr.handler.Delete;
import repbot.commands.gdpr.handler.Request;

public class Gdpr extends SlashCommand {

    public Gdpr(repbot.dao.access.Gdpr gdpr) {
        super(Slash.of("gdpr", "command.gdpr.description")
                .subCommand(SubCommand.of("request", "command.gdpr.request.description")
                        .handler(new Request(gdpr)))
                .subCommand(SubCommand.of("delete", "command.gdpr.delete.description")
                        .handler(new Delete(gdpr))));
    }
}
