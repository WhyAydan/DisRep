package repbot.commands.abuseprotection;

import jdautil.interactions.slash.Argument;
import jdautil.interactions.slash.Slash;
import jdautil.interactions.slash.SubCommand;
import jdautil.interactions.slash.provider.SlashCommand;
import repbot.commands.abuseprotection.handler.Cooldown;
import repbot.commands.abuseprotection.handler.DonorContext;
import repbot.commands.abuseprotection.handler.DonorLimit;
import repbot.commands.abuseprotection.handler.Info;
import repbot.commands.abuseprotection.handler.MaxMessageAge;
import repbot.commands.abuseprotection.handler.MaxMessageReputation;
import repbot.commands.abuseprotection.handler.MinMessages;
import repbot.commands.abuseprotection.handler.ReceiverContext;
import repbot.commands.abuseprotection.handler.ReceiverLimit;
import repbot.dao.provider.Guilds;

public class AbuseProtection extends SlashCommand {
    public AbuseProtection(Guilds guilds) {
        super(Slash.of("abuseprotection", "command.abuseprotection.description")
                .adminCommand()
                .guildOnly()
                .subCommand(SubCommand.of("info", "command.abuseprotection.info.description")
                        .handler(new Info(guilds)))
                .subCommand(SubCommand.of("maxmessageage", "command.abuseprotection.maxmessageage.description")
                        .handler(new MaxMessageAge(guilds))
                        .argument(Argument.integer("minutes", "command.abuseprotection.maxmessageage.minutes.description")))
                .subCommand(SubCommand.of("maxmessagereputation", "command.abuseprotection.maxmessagereputation.description")
                        .handler(new MaxMessageReputation(guilds))
                        .argument(Argument.integer("amount", "command.abuseprotection.maxmessagereputation.amount.description")))
                .subCommand(SubCommand.of("minmessages", "command.abuseprotection.minmessages.description")
                        .handler(new MinMessages(guilds))
                        .argument(Argument.integer("messages", "command.abuseprotection.minmessages.messages.description")))
                .subCommand(SubCommand.of("cooldown", "command.abuseprotection.cooldown.description")
                        .handler(new Cooldown(guilds))
                        .argument(Argument.integer("minutes", "command.abuseprotection.cooldown.minutes.description")))
                .subCommand(SubCommand.of("donorcontext", "command.abuseprotection.donorcontext.description")
                        .handler(new DonorContext(guilds))
                        .argument(Argument.bool("state", "command.abuseprotection.donorcontext.state.description")))
                .subCommand(SubCommand.of("receivercontext", "command.abuseprotection.receivercontext.description")
                        .handler(new ReceiverContext(guilds))
                        .argument(Argument.bool("state", "command.abuseprotection.receivercontext.state.description")))
                .subCommand(SubCommand.of("donorlimit", "command.abuseprotection.donorlimit.description")
                        .handler(new DonorLimit(guilds))
                        .argument(Argument.integer("limit", "command.abuseprotection.donorlimit.limit.description"))
                        .argument(Argument.integer("hours", "command.abuseprotection.donorlimit.hours.description")))
                .subCommand(SubCommand.of("receiverlimit", "command.abuseprotection.receiverlimit.description")
                        .handler(new ReceiverLimit(guilds))
                        .argument(Argument.integer("limit", "command.abuseprotection.receiverlimit.limit.description"))
                        .argument(Argument.integer("hours", "command.abuseprotection.receiverlimit.hours.description")))
        );
    }
}
