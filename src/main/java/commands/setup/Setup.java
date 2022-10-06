package repbot.commands.setup;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdautil.interactions.slash.Slash;
import jdautil.interactions.slash.provider.SlashCommand;
import repbot.commands.setup.handler.Start;
import repbot.commands.thankwords.Thankwords;
import repbot.dao.provider.Guilds;
import repbot.serialization.ThankwordsContainer;
import org.slf4j.Logger;

import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class Setup extends SlashCommand {
    private static final Logger log = getLogger(Setup.class);

    public Setup(Guilds guilds, ThankwordsContainer thankwordsContainer) {
        super(Slash.of("setup", "command.setup.description")
                .guildOnly()
                .adminCommand()
                .command(new Start(guilds, thankwordsContainer)));
    }

    public static Setup of(Guilds guilds) {
        ThankwordsContainer thankwordsContainer;
        try {
            thankwordsContainer = new ObjectMapper()
                    .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                    .readValue(Thankwords.class.getClassLoader().getResourceAsStream("Thankswords.json"),
                            ThankwordsContainer.class);
        } catch (IOException e) {
            thankwordsContainer = null;
            log.error("Could not read thankwords", e);
        }
        return new Setup(guilds, thankwordsContainer);
    }
}
