package repbot.dao.snapshots;

import jdautil.localization.util.LocalizedEmbedBuilder;
import jdautil.localization.util.Replacement;
import jdautil.wrapper.EventContext;
import repbot.dao.snapshots.analyzer.ResultSnapshot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

public record ResultEntry(ResultSnapshot result, long channelId, long messageId) {
    public MessageEmbed embed(Guild guild, EventContext context) {
        var builder = new LocalizedEmbedBuilder(context.guildLocalizer())
                .setAuthor("command.log.analyzer.message.author",
                        Message.JUMP_URL.formatted(guild.getIdLong(), channelId, messageId),
                        Replacement.create("ID", messageId()));
        result.add(guild, this, builder);
        return builder.build();
    }
}
