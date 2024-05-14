package org.theShire.domain.medicalCase;

import static org.theShire.foundation.DomainAssertion.*;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

public class CaseVote {
    private LinkedHashSet<Answer> answers;
    private HashMap<UUID, Set<Vote>> votes;
    private int maxAnswers;
    private double prozentCount;
    private final double maxProzentCount = 100.0;

    // getter & setter-----------------------------------
    public LinkedHashSet<Answer> getAnswers() {
        return answers;
    }

    public void addAnswers(Answer answers) {
        isNotNull(answers, "answers");
        this.answers.add(answers);
    }

    public HashMap<UUID, Set<Vote>> getVotes() {
        return votes;
    }

//TODO NEEDED?
//    public void setVotes(HashMap<UUID, Set<Vote>> votes) {
//        this.votes = votes;
//    }

    public int getMaxAnswers() {
        return maxAnswers;
    }

    public void setMaxAnswers(int maxAnswers) {
        greaterZero(maxAnswers, "maxAnswers");
        this.maxAnswers = maxAnswers;
    }

    public double getProzentCount() {
        return prozentCount;
    }

    public void setProzentCount(double prozentCount) {
        greaterZero(prozentCount, "prozentCount");
        this.prozentCount = prozentCount;
    }

    public double getMaxProzentCount() {
        return maxProzentCount;
    }
}
