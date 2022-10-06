package repbot.commands.debug.handler;

import jdautil.interactions.slash.structure.handler.SlashHandler;
import jdautil.localization.util.LocalizedEmbedBuilder;
import jdautil.localization.util.Replacement;
import jdautil.wrapper.EventContext;
import repbot.dao.provider.Guilds;
import repbot.util.Colors;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.StringJoiner;

import static repbot.util.Guilds.prettyName;

public class Show implements SlashHandler {
    private final Guilds guilds;

    public Show(Guilds guilds) {
        this.guilds = guilds;
    }

    @Override
    public void onSlashCommand(SlashCommandInteractionEvent event, EventContext context) {
        var settings = guilds.guild(event.getGuild()).settings();

        var joiner = new StringJoiner("`, `", "`", "`");
        settings.thanking().thankwords().words().forEach(joiner::add);

        event.replyEmbeds(new LocalizedEmbedBuilder(context.guildLocalizer())
                .setTitle("command.debug.message.title",
                        Replacement.create("GUILD", prettyName(event.getGuild())))
                .addField("word.reputationSettings", settings.reputation().toLocalizedString(), false)
                .addField("word.thankWords", joiner.setEmptyValue("none").toString(), true)
                .addField("command.debug.message.channelactive", String.valueOf(
                                settings.thanking().channels().isEnabled(event.getChannel().asTextChannel())),
                        true
                )
                .setColor(Colors.Pastel.DARK_PINK)
                .build()).queue();
    }
}
