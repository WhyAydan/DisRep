package repbot.commands.bot;

import jdautil.interactions.slash.Argument;
import jdautil.interactions.slash.Group;
import jdautil.interactions.slash.Slash;
import jdautil.interactions.slash.SubCommand;
import jdautil.interactions.slash.provider.SlashCommand;
import repbot.commands.bot.handler.Debug;
import repbot.commands.bot.handler.Leave;
import repbot.commands.bot.handler.Redeploy;
import repbot.commands.bot.handler.Search;
import repbot.commands.bot.handler.SharedGuilds;
import repbot.commands.bot.handler.system.Metrics;
import repbot.commands.bot.handler.system.Reload;
import repbot.commands.bot.handler.system.Restart;
import repbot.commands.bot.handler.system.Shudown;
import repbot.commands.bot.handler.system.Status;
import repbot.commands.bot.handler.system.Upgrade;
import repbot.config.Configuration;
import repbot.dao.provider.Guilds;
import repbot.statistic.Statistic;

public class BotAdmin extends SlashCommand {
    public BotAdmin(Guilds guilds, Configuration configuration, Statistic statistics) {
        super(Slash.of("bot", "Bot admin commands.")
                .unlocalized()
                .guildOnly()
                .adminCommand()
                .privateCommand()
                .group(Group.of("system", "System management")
                        .subCommand(SubCommand.of("restart", "Restart bot")
                                .handler(new Restart(configuration)))
                        .subCommand(SubCommand.of("upgrade", "Deploy an update")
                                .handler(new Upgrade(configuration)))
                        .subCommand(SubCommand.of("shutdown", "Shutdown the bot.")
                                .handler(new Shudown(configuration)))
                        .subCommand(SubCommand.of("status", "System status")
                                .handler(new Status(statistics)))
                        .subCommand(SubCommand.of("metrics", "System metrics")
                                .handler(new Metrics(configuration)))
                        .subCommand(SubCommand.of("reload", "Reload configuration")
                                .handler(new Reload(configuration))))
                .subCommand(SubCommand.of("debug", "Debug of a guild")
                        .handler(new Debug(guilds))
                        .argument(Argument.text("guild_id", "Id of guild").asRequired()))
                .subCommand(SubCommand.of("shared_guilds", "Shared guilds with a user")
                        .handler(new SharedGuilds())
                        .argument(Argument.text("user_id", "user id"))
                        .argument(Argument.user("user", "user")))
                .subCommand(SubCommand.of("redeploy", "Redeploy guild commands")
                        .handler(new Redeploy())
                        .argument(Argument.text("guild_id", "Guild id").asRequired()))
                .subCommand(SubCommand.of("search", "Search for guilds")
                        .handler(new Search())
                        .argument(Argument.text("term", "Search term").asRequired()))
                .subCommand(SubCommand.of("leave", "Leave a guild")
                        .handler(new Leave())
                        .argument(Argument.text("guild_id", "Guild id").asRequired())));
    }
}
