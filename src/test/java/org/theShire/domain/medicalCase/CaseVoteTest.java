package org.theShire.domain.medicalCase;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CaseVoteTest {

    @Test
    void TestVoting_ShouldBeEqualToOtherVoteObjects_WhenDeterminedAsParameter() {
        //initialization of the LinkedHashSet filled with Answers
        LinkedHashSet<Answer> answerLul = new LinkedHashSet<>();
        UUID userID = UUID.randomUUID();
        Answer answer = new Answer("Krebs");
        Answer answer1 = new Answer("Ebola");
        answerLul.add(answer);
        answerLul.add(answer1);

        //Slowly starting with business by creating a CaseVote Instance
        CaseVote caseVote = new CaseVote(answerLul);
        HashMap<UUID, Set<Vote>> votesSafe = new HashMap<>();

        //testing if the UUID stays the same after voting throughout saving the old values of the votes Hashmap
        votesSafe = caseVote.getVotes();
        caseVote.voting(userID, answer1, 10.0);
        assertEquals(votesSafe.values(), caseVote.getVotes().values());

        Vote vote1 = new Vote(answer1, 10.0);
        Vote vote2 = new Vote(answer1, 5.0);
        Vote vote3 = new Vote(answer, 10.0);
        Vote vote4 = new Vote(answer, 5.0);


        assertEquals(caseVote.getVotes().get(userID).stream().findFirst().orElse(null).getAnswer(), vote1.getAnswer());
        assertEquals(caseVote.getVotes().get(userID).stream().findFirst().orElse(null).getPercent(), vote1.getPercent());

        assertEquals(caseVote.getVotes().get(userID).stream().findFirst().orElse(null).getAnswer(), vote2.getAnswer());
        assertNotEquals(caseVote.getVotes().get(userID).stream().findFirst().orElse(null).getPercent(), vote2.getPercent());

        assertNotEquals(caseVote.getVotes().get(userID).stream().findFirst().orElse(null).getAnswer(), vote3.getAnswer());
        assertEquals(caseVote.getVotes().get(userID).stream().findFirst().orElse(null).getPercent(), vote3.getPercent());

        assertNotEquals(caseVote.getVotes().get(userID).stream().findFirst().orElse(null).getAnswer(), vote4.getAnswer());
        assertNotEquals(caseVote.getVotes().get(userID).stream().findFirst().orElse(null).getPercent(), vote4.getPercent());

    }
}