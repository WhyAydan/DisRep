package repbot.listener.voting;

import jdautil.localization.util.LocalizedEmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.components.ActionComponent;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class VoteRequest {
    private final Member member;
    private final LocalizedEmbedBuilder embedBuilder;
    private final Message voteMessage;
    private final Message refMessage;
    private final Map<String, VoteComponent> voteTargets;
    private int remainingVotes;

    public VoteRequest(Member member, LocalizedEmbedBuilder embedBuilder, Message voteMessage, Message refMessage, Map<String, VoteComponent> voteTargets, int remainingVotes) {
        this.member = member;
        this.embedBuilder = embedBuilder;
        this.voteMessage = voteMessage;
        this.refMessage = refMessage;
        this.voteTargets = voteTargets;
        this.remainingVotes = remainingVotes;
    }

    public Optional<Member> getTarget(String id) {
        return Optional.ofNullable(voteTargets.get(id).member());
    }

    public void voted() {
        remainingVotes--;
    }

    public MessageEmbed getNewEmbed(String description) {
        return embedBuilder.setDescription(description).build();
    }

    public Member member() {
        return member;
    }

    public LocalizedEmbedBuilder embedBuilder() {
        return embedBuilder;
    }

    public Message voteMessage() {
        return voteMessage;
    }

    public Message refMessage() {
        return refMessage;
    }

    public Map<String, VoteComponent> voteTargets() {
        return voteTargets;
    }

    public List<ActionComponent> components() {
        return voteTargets.values().stream().map(VoteComponent::component).toList();
    }

    public int remainingVotes() {
        return remainingVotes;
    }

    public void remove(String id) {
        voteTargets.remove(id);
    }

    public boolean canVote() {
        return remainingVotes > 0;
    }
}
