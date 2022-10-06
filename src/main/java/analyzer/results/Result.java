package repbot.analyzer.results;

import jdautil.parsing.WeightedEntry;
import repbot.analyzer.results.empty.EmptyResult;
import repbot.analyzer.results.empty.EmptyResultReason;
import repbot.analyzer.results.match.AnswerResult;
import repbot.analyzer.results.match.DirectResult;
import repbot.analyzer.results.match.FuzzyResult;
import repbot.analyzer.results.match.MatchResult;
import repbot.analyzer.results.match.ThankType;
import repbot.analyzer.results.match.fuzzy.MemberMatch;
import repbot.dao.snapshots.analyzer.ResultSnapshot;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

import java.util.List;

public interface Result {
    ResultType resultType();

    default EmptyResult asEmpty() {
        return (EmptyResult) this;
    }

    default MatchResult asMatch() {
        return (MatchResult) this;
    }

    static Result empty(EmptyResultReason resultReason) {
        return new EmptyResult(null, resultReason);
    }

    static Result empty(String match, EmptyResultReason resultReason) {
        return new EmptyResult(match, resultReason);
    }

    static Result mention(String match, Member donator, List<Member> receiver) {
        return new DirectResult(match, ThankType.MENTION, donator, receiver);
    }

    static Result answer(String match, Member donator, Member receiver, Message referenceMessage) {
        return new AnswerResult(match, donator, receiver, referenceMessage.getIdLong());
    }

    static Result fuzzy(String match, List<String> thankwords, List<MemberMatch> memberMatches, Member donator, List<WeightedEntry<Member>> receivers) {
        return new FuzzyResult(match, thankwords, memberMatches, donator, receivers);
    }

    default boolean isEmpty() {
        return resultType() == ResultType.NO_MATCH;
    }

    ResultSnapshot toSnapshot();
}
