package repbot.commands.repadmin.handler.reputation;

import jdautil.localization.util.Replacement;
import jdautil.wrapper.EventContext;
import repbot.dao.access.guild.reputation.sub.RepUser;
import repbot.dao.provider.Guilds;
import repbot.service.RoleAssigner;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Add extends BaseReputationModifier {

    public Add(RoleAssigner roleAssigner, Guilds guilds) {
        super(roleAssigner, guilds);
    }

    @Override
    void execute(SlashCommandInteractionEvent event, EventContext context, Member user, RepUser repUser, long rep) {
        repUser.addReputation(rep);
        event.reply(context.localize("command.repadmin.reputation.add.message.added",
                        Replacement.create("VALUE", rep), Replacement.createMention(user)))
                .setEphemeral(true).queue();
    }
}
