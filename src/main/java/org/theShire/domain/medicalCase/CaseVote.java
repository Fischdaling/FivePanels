package org.theShire.domain.medicalCase;

import org.theShire.domain.exception.MedicalCaseException;
import org.theShire.domain.exception.MedicalDoctorException;

import static org.theShire.foundation.DomainAssertion.*;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

public class CaseVote {
    private static final Class<MedicalCaseException> exType = MedicalCaseException.class;
    //a LinkedHashSet (it's simply a sorted HashSet) of the answers related to a specific case
    private LinkedHashSet<Answer> answers;
    //a HashMap of a Set of Votes (so we can differentiate between the members)
    private HashMap<UUID, Set<Vote>> votes;
    //determines the maximum amount of answers choosable
    private int maxAnswers;
    //counts the percent in correlation to  maxProzentCount
    private double prozentCount;
    //the percent limit that cannot be exceeded
    private final double maxProzentCount = 100.0;


    // getter & setter-----------------------------------
    public LinkedHashSet<Answer> getAnswers() {
        return answers;
    }

    public void addAnswers(Answer answers) {
        isNotNull(answers, "answers", exType);
        this.answers.add(answers);
    }

    public HashMap<UUID, Set<Vote>> getVotes() {
        return votes;
    }

    public int getMaxAnswers() {
        return maxAnswers;
    }

    public void setMaxAnswers(int maxAnswers) {
        greaterZero(maxAnswers, "maxAnswers", exType);
        this.maxAnswers = maxAnswers;
    }

    public double getProzentCount() {
        return prozentCount;
    }

    public void setProzentCount(double prozentCount) {
        greaterZero(prozentCount, "prozentCount", exType);
        this.prozentCount = prozentCount;
    }

    public double getMaxProzentCount() {
        return maxProzentCount;
    }
}
