package repbot.analyzer.results.match;

import repbot.dao.snapshots.analyzer.ResultSnapshot;
import repbot.dao.snapshots.analyzer.match.DirectResultSnapshot;
import net.dv8tion.jda.api.entities.Member;

import java.util.List;

public class DirectResult extends MatchResult {
    private final List<Member> receivers;
    public DirectResult(String match, ThankType type, Member donor, List<Member> receivers) {
        super(type, donor, match);
        this.receivers = receivers;
    }

    protected List<Long> receiverIds(){
        return receivers.stream().map(Member::getIdLong).toList();
    }

    @Override
    public List<Member> receivers() {
        return receivers;
    }

    @Override
    public ResultSnapshot toSnapshot() {
        return new DirectResultSnapshot(thankType(), donor().getIdLong(), match(), receiverIds());
    }
}
