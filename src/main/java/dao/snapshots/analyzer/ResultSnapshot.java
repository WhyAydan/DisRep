package repbot.dao.snapshots.analyzer;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jdautil.localization.util.LocalizedEmbedBuilder;
import repbot.dao.snapshots.ResultEntry;
import net.dv8tion.jda.api.entities.Guild;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public interface ResultSnapshot {
    void add(Guild guild, ResultEntry entry, LocalizedEmbedBuilder builder);
}
