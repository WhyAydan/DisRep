package repbot.statistic;

import repbot.dao.provider.Metrics;
import repbot.statistic.element.DataStatistic;
import repbot.statistic.element.ProcessStatistics;
import repbot.statistic.element.ShardStatistic;
import repbot.statistic.element.SystemStatistics;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.slf4j.Logger;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class Statistic {
    private static final Logger log = getLogger(Statistic.class);
    private final ShardManager shardManager;
    private final Metrics metrics;

    private Statistic(ShardManager shardManager, Metrics metrics) {
        this.shardManager = shardManager;
        this.metrics = metrics;
        getSystemStatistic();
    }

    public static Statistic of(ShardManager shardManager, Metrics metrics, ScheduledExecutorService service) {
        var statistic = new Statistic(shardManager, metrics);
        service.scheduleAtFixedRate(statistic::refreshStatistics, 1, 30, TimeUnit.MINUTES);
        return statistic;
    }

    private ShardStatistic getShardStatistic(JDA jda) {
        var shardId = jda.getShardInfo().getShardId();
        var analyzedMessages = metrics.messages().hour(1, 1).join().get(0).count();

        return new ShardStatistic(
                shardId + 1,
                jda.getStatus(),
                analyzedMessages,
                jda.getGuildCache().size());
    }

    public SystemStatistics getSystemStatistic() {
        var shardStatistics = shardManager.getShardCache()
                                          .stream()
                                          .map(this::getShardStatistic)
                                          .collect(Collectors.toList());

        return new SystemStatistics(ProcessStatistics.create(),
                metrics.statistic().getStatistic().orElseGet(DataStatistic::new),
                shardStatistics);
    }

    private void refreshStatistics() {
        metrics.statistic().refreshStatistics();
    }
}
