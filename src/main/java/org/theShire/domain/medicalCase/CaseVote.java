package org.theShire.domain.medicalCase;

import static org.theShire.domain.exception.MedicalCaseException.exTypeCase;
import static org.theShire.foundation.DomainAssertion.*;

import java.util.*;
import java.util.stream.Stream;

public class CaseVote {
    //a LinkedHashSet (it's simply a sorted HashSet) of the answers related to a specific case
    private LinkedHashSet<Answer> answers;
    //a HashMap of a Set of Votes (so we can differentiate between the members)
    private HashMap<UUID, Set<Vote>> votes;
    //determines the maximum amount of answers choosable
    private int maxAnswers;
    //counts the percent in correlation to  maxPercentCount
    private double percentCount;
    //the percent limit that cannot be exceeded
    private final double maxPercentCount = 100.0;


    public CaseVote(LinkedHashSet<Answer> answers, HashMap<UUID, Set<Vote>> votes) {
        addAnswers(answers);
        setMaxAnswers();
        this.votes = votes;
        setpercentCount(0);
    }

    public void voting(UUID voter, Answer answerChosen, double percent) {
        Vote vote = new Vote(answerChosen, percent);
        isInCollection(vote.getAnswer(), answers, "vote", exTypeCase);
        percentCount += vote.getPercent();
        if (percentCount <= maxPercentCount) {
            if (votes.containsKey(voter)) {
                votes.get(voter).add(vote);
            } else {
                votes.put(voter, (Set<Vote>) vote);
            }
        } else {
            System.err.println("The number you chose exceeds the limit of 100% total");
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

    public int getMaxAnswers() {
        return maxAnswers;
    }

    public void setMaxAnswers() {
        greaterZero(maxAnswers, "maxAnswers", exTypeCase);
        maxAnswers = answers.size();
    }

    public double getpercentCount() {
        return percentCount;
    }

    public void setpercentCount(double percentCount) {
        greaterZero(percentCount, "percentCount", exTypeCase);
        this.percentCount = percentCount;
    }

    public double getmaxPercentCount() {
        return maxPercentCount;
    }

    public static void main(String[] args) {
        CaseVote caseVote = new CaseVote();
    }
}
