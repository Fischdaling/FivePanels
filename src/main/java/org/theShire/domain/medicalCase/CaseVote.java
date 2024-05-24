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
    //counts the percent in correlation to  maxProzentCount
    private double prozentCount;
    //the percent limit that cannot be exceeded
    private final double maxProzentCount = 100.0;


    public CaseVote(LinkedHashSet<Answer> answers, HashMap<UUID, Set<Vote>> votes) {
        addAnswers(answers);
        setMaxAnswers();
        this.votes = votes;
        setProzentCount(0);
    }

//    public Vote voting(UUID voter) { //TODO
//        while (prozentCount < maxProzentCount) {
//            System.out.println(voter);
//            Scanner scanner = new Scanner(System.in);
//            System.out.println("Please select an answer!");
//            Answer answer = new Answer();
//            answer = scanner.nextLine();
//            System.out.println("How much percent do you think this is the right Answer?");
//            double answerPercent = scanner.nextDouble();
//            greaterThan(maxProzentCount,(prozentCount += answerPercent), "prozentCount", exTypeCase);
//            Vote vote = new Vote(answer, answerPercent, voter);
//        }
//        return vote;
//    }

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

    public double getProzentCount() {
        return prozentCount;
    }

    public void setProzentCount(double prozentCount) {
        greaterZero(prozentCount, "prozentCount", exTypeCase);
        this.prozentCount = prozentCount;
    }

    public double getMaxProzentCount() {
        return maxProzentCount;
    }
}
