package org.theShire.domain.medicalCase;


import java.util.*;
import java.util.stream.Collectors;

import static org.theShire.domain.exception.MedicalCaseException.exTypeCase;
import static org.theShire.foundation.DomainAssertion.*;

public class CaseVote {
    //a LinkedHashSet (it's simply a sorted HashSet) of the answers related to a specific case
    private LinkedHashSet<Answer> answers = new LinkedHashSet<>();
    //a HashMap of a Set of Votes (so we can differentiate between the members)
    private HashMap<UUID, Set<Vote>> votes = new HashMap<>();



    public CaseVote(LinkedHashSet<Answer> answers) {
        addAnswers(answers);
    }

    public CaseVote(LinkedHashSet<Answer> answers, HashMap<UUID, Set<Vote>> votes) {
        addAnswers(answers);
        this.votes = votes;
    }

    public void voting(UUID voter, Answer answerChosen, double percent) {
        lesserThan(percent,101.0,"percent",exTypeCase);
        Vote vote = new Vote(answerChosen, percent);
        isInCollection(vote.getAnswer(), answers, "vote", exTypeCase);
        if (votes.containsKey(voter)) {
            double sumVotes = votes.get(voter).stream().mapToDouble(Vote::getPercent).sum();
            if(sumVotes <= 100.0){
                votes.get(voter).add(vote);
            }
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


    public Map<Answer,Double> getTop3Answer(){
        Map<Answer, Double> answerPercentageMap = new HashMap<>();

        for (Set<Vote> voteSet : votes.values()) {
            for (Vote vote : voteSet) {
                Answer answer = vote.getAnswer();
                double percent = vote.getPercent();
                answerPercentageMap.put(answer, answerPercentageMap.getOrDefault(answer, 0.0) + percent);
            }
        }
        return answerPercentageMap.entrySet().stream()
                .sorted(Map.Entry.<Answer, Double>comparingByValue().reversed())
                .limit(3)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("answers: ").append(answers);
        sb.append("votes: ").append(votes);

        return sb.toString();
    }

    public String toCSVString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(answers).append(",");
        sb.append(votes);
        return sb.toString();
    }
}
