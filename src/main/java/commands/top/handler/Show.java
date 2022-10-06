package repbot.commands.top.handler;

import jdautil.interactions.slash.structure.handler.SlashHandler;
import jdautil.localization.util.LocalizedEmbedBuilder;
import jdautil.localization.util.Replacement;
import jdautil.pagination.bag.PageBag;
import jdautil.util.Completion;
import jdautil.wrapper.EventContext;
import repbot.dao.access.guild.settings.sub.ReputationMode;
import repbot.dao.pagination.GuildRanking;
import repbot.dao.provider.Guilds;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.Color;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class Show implements SlashHandler {
    private static final int TOP_PAGE_SIZE = 10;
    private final Guilds guilds;

    public Show(Guilds guilds) {
        this.guilds = guilds;
    }

    @Override
    public void onSlashCommand(SlashCommandInteractionEvent event, EventContext context) {
        var guild = guilds.guild(event.getGuild());
        var reputationMode = guild.settings().general().reputationMode();
        if (event.getOption("mode") != null) {
            var mode = event.getOption("mode").getAsString();
            reputationMode = switch (mode) {
                case "total" -> ReputationMode.TOTAL;
                case "7 days" -> ReputationMode.ROLLING_WEEK;
                case "30 days" -> ReputationMode.ROLLING_MONTH;
                default -> reputationMode;
            };
        }

        var ranking = guild.reputation().ranking().byMode(reputationMode, TOP_PAGE_SIZE);
        registerPage(ranking, event, context);
    }

    public static void registerPage(GuildRanking guildRanking, SlashCommandInteractionEvent event, EventContext context) {
        context.registerPage(new PageBag(guildRanking.pages()) {
            @Override
            public CompletableFuture<MessageEmbed> buildPage() {
                return CompletableFuture.supplyAsync(() -> {
                    var ranking = guildRanking.page(current());

                    var maxRank = ranking.get(ranking.size() - 1).rank();
                    var rankString = ranking.stream().map(rank -> rank.fancyString((int) maxRank))
                                            .collect(Collectors.joining("\n"));

                    return createBaseBuilder(guildRanking, context, event.getGuild())
                            .setDescription(rankString)
                            .build();
                });
            }

            @Override
            public CompletableFuture<MessageEmbed> buildEmptyPage() {
                return CompletableFuture.completedFuture(createBaseBuilder(guildRanking, context, event.getGuild())
                        .setDescription("*" + context.localize("command.top.message.empty") + "*")
                        .build());
            }
        }, true);
    }

    private static LocalizedEmbedBuilder createBaseBuilder(GuildRanking guildRanking, EventContext context, Guild guild) {
        return new LocalizedEmbedBuilder(context.guildLocalizer())
                .setTitle(guildRanking.title(), Replacement.create("GUILD", guild.getName()))
                .setColor(Color.CYAN);
    }

    @Override
    public void onAutoComplete(CommandAutoCompleteInteractionEvent event, EventContext context) {
        var option = event.getFocusedOption();
        if ("mode".equalsIgnoreCase(option.getName())) {
            event.replyChoices(Completion.complete(option.getValue(), "total", "7 days", "30 days")).queue();
        }
    }
}
