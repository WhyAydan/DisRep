package repbot.statistic.element;

import jdautil.localization.util.Replacement;
import repbot.statistic.ReplacementProvider;
import net.dv8tion.jda.api.JDA;

import java.util.List;

public record ShardStatistic(int shard, JDA.Status status, long analyzedMessages,
                             long guilds) implements ReplacementProvider {

    @Override
    public List<Replacement> replacements() {
        return List.of(Replacement.create("analyzed_messages_shard", analyzedMessages), Replacement.create("shard_status", status.name()),
                Replacement.create("shard_id", shard), Replacement.create("shard_guilds", guilds));
    }
}
