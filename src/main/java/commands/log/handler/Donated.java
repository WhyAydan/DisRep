package repbot.commands.log.handler;

import jdautil.interactions.slash.structure.handler.SlashHandler;
import jdautil.pagination.bag.PrivatePageBag;
import jdautil.wrapper.EventContext;
import repbot.dao.provider.Guilds;
import repbot.dao.snapshots.ReputationLogEntry;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

import static repbot.commands.log.handler.LogFormatter.PAGE_SIZE;
import static repbot.commands.log.handler.LogFormatter.mapUserLogEntry;
import static repbot.commands.log.handler.LogFormatter.userLogEmbed;

public class Donated implements SlashHandler {
    private final Guilds guilds;

    public Donated(Guilds guilds) {
        this.guilds = guilds;
    }

    @Override
    public void onSlashCommand(SlashCommandInteractionEvent event, EventContext context) {
        var user = event.getOption("user").getAsMember();
        var logAccess = guilds.guild(event.getGuild()).reputation().log().userDonatedLog(user.getUser(), PAGE_SIZE);
        context.registerPage(new PrivatePageBag(logAccess.pages(), event.getUser().getIdLong()) {
            @Override
            public CompletableFuture<MessageEmbed> buildPage() {
                return CompletableFuture.supplyAsync(() -> userLogEmbed(context, user, "command.log.donated.message.log",
                        mapUserLogEntry(context, logAccess.page(current()), ReputationLogEntry::receiverId)));
            }

            @Override
            public CompletableFuture<MessageEmbed> buildEmptyPage() {
                return CompletableFuture.completedFuture(userLogEmbed(context, user, "command.log.donated.message.log",
                        mapUserLogEntry(context, Collections.emptyList(), ReputationLogEntry::receiverId)));
            }
        }, true);
    }
}
