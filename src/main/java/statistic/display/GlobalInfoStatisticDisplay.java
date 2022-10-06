package repbot.statistic.display;

import repbot.statistic.EmbedDisplay;
import repbot.statistic.element.DataStatistic;
import repbot.statistic.element.GlobalShardStatistic;
import net.dv8tion.jda.api.EmbedBuilder;

public record GlobalInfoStatisticDisplay(GlobalShardStatistic globalShardStatistic,
                                         DataStatistic dataStatistic) implements EmbedDisplay {


    @Override
    public void appendTo(EmbedBuilder embedBuilder) {
        embedBuilder.addField("Global Info",
                String.format("""
                              Analyzed: %s
                              Total Reputation: %s
                              Week Reputation: %s
                              AverageWeek Reputation: %s
                              Today Reputation: %s
                              """.stripIndent(),
                        globalShardStatistic.analyzedMessages(), dataStatistic.totalRep(), dataStatistic.weeklyRep(),
                        dataStatistic.weeklyAvgRep(), dataStatistic.today()), false);
    }
}
