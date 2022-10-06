package repbot.commands.thankwords.handler;

import jdautil.interactions.slash.structure.handler.SlashHandler;
import jdautil.localization.util.Format;
import jdautil.localization.util.Replacement;
import jdautil.wrapper.EventContext;
import repbot.dao.provider.Guilds;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Add implements SlashHandler {
    private final Guilds guilds;

    public Add(Guilds guilds) {
        this.guilds = guilds;
    }

    @Override
    public void onSlashCommand(SlashCommandInteractionEvent event, EventContext context) {
        var pattern = event.getOption("pattern").getAsString();
        try {
            Pattern.compile(pattern);
        } catch (PatternSyntaxException e) {
            event.reply(context.localize("error.invalidRegex"))
                 .setEphemeral(true)
                 .queue();
            return;
        }
        if (guilds.guild(event.getGuild()).settings().thanking().thankwords().add(pattern)) {
            event.reply(context.localize("command.thankwords.add.message.added",
                    Replacement.create("REGEX", pattern, Format.CODE))).queue();
        }
    }
}
