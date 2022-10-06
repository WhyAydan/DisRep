package repbot.commands.bot.handler.system;

import jdautil.interactions.slash.structure.handler.SlashHandler;
import jdautil.wrapper.EventContext;
import repbot.statistic.Statistic;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Status implements SlashHandler {
    private final Statistic statistic;

    public Status(Statistic statistic) {
        this.statistic = statistic;
    }

    @Override
    public void onSlashCommand(SlashCommandInteractionEvent event, EventContext context) {
        var builder = new EmbedBuilder();
        var systemStatistic = statistic.getSystemStatistic();
        systemStatistic.appendTo(builder);
        event.replyEmbeds(builder.build()).queue();
    }
}
