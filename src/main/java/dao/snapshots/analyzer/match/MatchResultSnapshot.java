package repbot.dao.snapshots.analyzer.match;

import jdautil.localization.util.LocalizedEmbedBuilder;
import jdautil.util.Colors;
import repbot.analyzer.results.match.ThankType;
import repbot.dao.snapshots.analyzer.ResultSnapshot;
import repbot.dao.snapshots.ResultEntry;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

public abstract class MatchResultSnapshot implements ResultSnapshot {
    private final ThankType thankType;
    private final long donorId;
    private final String match;

    public MatchResultSnapshot(ThankType thankType, long donorId, String match) {
        this.thankType = thankType;
        this.donorId = donorId;
        this.match = match;
    }

    public ThankType thankType() {
        return thankType;
    }

    public long donorId() {
        return donorId;
    }

    public String match() {
        return match;
    }

    @Override
    public void add(Guild guild, ResultEntry entry, LocalizedEmbedBuilder builder) {
        builder.setTitle(thankType.nameLocaleKey())
                .setColor(Colors.Pastel.DARK_GREEN)
                .addField("command.log.analyzer.message.field.matchedWord", match, true)
                .addField("words.donor", User.fromId(donorId).getAsMention(), true);
    }
}
