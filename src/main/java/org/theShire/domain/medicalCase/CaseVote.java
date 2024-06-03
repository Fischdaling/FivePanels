package org.theShire.domain.medicalCase;


import java.util.*;

import static org.theShire.domain.exception.MedicalCaseException.exTypeCase;
import static org.theShire.foundation.DomainAssertion.*;

public class CaseVote {
    //a LinkedHashSet (it's simply a sorted HashSet) of the answers related to a specific case
    private LinkedHashSet<Answer> answers = new LinkedHashSet<>();
    //a HashMap of a Set of Votes (so we can differentiate between the members)
    private HashMap<UUID, Set<Vote>> votes = new HashMap<>();

    //counts the percent in correlation to  maxPercentCount
    private double percentCount;


    public CaseVote(LinkedHashSet<Answer> answers) {
        addAnswers(answers);
        setPercentCount(0);
    }

    public CaseVote(LinkedHashSet<Answer> answers, HashMap<UUID, Set<Vote>> votes) {
        addAnswers(answers);
        this.votes = votes;
        setPercentCount(percentCount);
    }

    public void voting(UUID voter, Answer answerChosen, double percent) {
        Vote vote = new Vote(answerChosen, percent);
        lesserThan(percent,100.1,"percent",exTypeCase);
        isInCollection(vote.getAnswer(), answers, "vote", exTypeCase);

        if (votes.containsKey(voter)) {
            double sumVotes = votes.get(voter).stream().mapToDouble(Vote::getPercent).sum();
            isTrue(sumVotes <= 100.0,()->"Vote Limit Reached",exTypeCase);
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

    public void addAnswers(LinkedHashSet<Answer> answers) {
        isNotNull(answers, "answers", exTypeCase);
        this.answers.addAll(answers);
    }

    public HashMap<UUID, Set<Vote>> getVotes() {
        return votes;
    }

    public double getPercentCount() {
        return percentCount;
    }

    public void setPercentCount(double percentCount) {
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
