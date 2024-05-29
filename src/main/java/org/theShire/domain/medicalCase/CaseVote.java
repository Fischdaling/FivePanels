package org.theShire.domain.medicalCase;

import org.theShire.domain.BaseEntity;

import static org.theShire.domain.exception.MedicalCaseException.exTypeCase;
import static org.theShire.foundation.DomainAssertion.*;

import java.util.*;
import java.util.stream.Stream;

public class CaseVote {
    //a LinkedHashSet (it's simply a sorted HashSet) of the answers related to a specific case
    private LinkedHashSet<Answer> answers = new LinkedHashSet<>();
    //a HashMap of a Set of Votes (so we can differentiate between the members)
    private HashMap<UUID, Set<Vote>> votes = new HashMap<>();

    //counts the percent in correlation to  maxPercentCount
    private double percentCount;

    public CaseVote(LinkedHashSet<Answer> answers) {
        addAnswers(answers);
        setpercentCount(0);
    }

    public CaseVote(LinkedHashSet<Answer> answers, HashMap<UUID, Set<Vote>> votes) {
        addAnswers(answers);
        this.votes = votes;
        setpercentCount(percentCount);

    }

    public void voting(UUID voter, Answer answerChosen, double percent) {
        Vote vote = new Vote(answerChosen, percent);
        isInCollection(vote.getAnswer(), answers, "vote", exTypeCase);
            if (votes.containsKey(voter)) {
                votes.get(voter).add(vote);
            } else {
                Set<Vote> voteSet = new HashSet<>();
                voteSet.add(vote);
                votes.put(voter, voteSet);
            }
    }

    // getter & setter-----------------------------------
    public LinkedHashSet<Answer> getAnswers() {
        return answers;
    }

//    public void addAnswer(Answer answer) {
//        isNotNull(answers, "answers", exTypeCase);
//        this.answers.add(answer);
//    }

    public void addAnswers(LinkedHashSet<Answer> answers) {
        isNotNull(answers, "answers", exTypeCase);
        this.answers.addAll(answers);
    }

    public HashMap<UUID, Set<Vote>> getVotes() {
        return votes;
    }

    public double getpercentCount() {
        return percentCount;
    }

    public void setpercentCount(double percentCount) {
        greaterEqualsZero(percentCount, "percentCount", exTypeCase);
        this.percentCount = percentCount;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("answers: ").append(answers);
        sb.append("votes: ").append(votes);
        sb.append("percentCount: ").append(percentCount);

        return sb.toString();
    }

    public String toCSVString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(answers).append(",");
        sb.append(votes);
        return sb.toString();
    }
}
