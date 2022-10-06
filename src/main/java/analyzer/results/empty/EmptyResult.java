package repbot.analyzer.results.empty;

import jdautil.localization.util.LocalizedEmbedBuilder;
import repbot.analyzer.results.Result;
import repbot.analyzer.results.ResultType;
import repbot.dao.snapshots.analyzer.ResultSnapshot;
import repbot.dao.snapshots.ResultEntry;
import repbot.util.Colors;
import net.dv8tion.jda.api.entities.Guild;

import javax.annotation.Nullable;

public record EmptyResult(@Nullable String match, EmptyResultReason reason) implements Result, ResultSnapshot {

    @Override
    public ResultType resultType() {
        return ResultType.NO_MATCH;
    }

    @Override
    public ResultSnapshot toSnapshot() {
        return this;
    }

    @Override
    public void add(Guild guild, ResultEntry entry, LocalizedEmbedBuilder builder) {
        builder.setTitle(reason.localeKey())
                .setColor(Colors.Pastel.ORANGE);
        if (match != null) {
            builder.addField("command.log.analyzer.message.field.matchedWord", match, false);
        }
    }
}
