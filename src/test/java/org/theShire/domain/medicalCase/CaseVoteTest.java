package org.theShire.domain.medicalCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.theShire.domain.medicalDoctor.UserRelationShip.relationShip;

class CaseVoteTest {
    LinkedHashSet<Answer> answerLul;
    UUID userID;
    Answer answer;
    Answer answer1;
    CaseVote caseVote;
    HashMap<UUID, Set<Vote>> votesSafe;

    @BeforeEach
    public void init() {
        relationShip = new HashMap<>();
        //initialization of the LinkedHashSet filled with Answers
        answerLul = new LinkedHashSet<>();
        userID = UUID.randomUUID();
        answer = new Answer("Krebs");
        answer1 = new Answer("Ebola");
        answerLul.add(answer);
        answerLul.add(answer1);
        caseVote = new CaseVote(answerLul);
        votesSafe = new HashMap<>();
    }


    @Test
    void TestVoting_ShouldReturnTrue_WhenComparedToAnEqualObject() {
        //testing if the UUID stays the same after voting throughout saving the old values of the votes Hashmap
        votesSafe = caseVote.getVotes();
        caseVote.voting(userID, answer1, 10.0);
        assertEquals(votesSafe.values(), caseVote.getVotes().values());

        Vote vote1 = new Vote(answer1, 10.0);


        assertEquals(caseVote.getVotes().get(userID).stream().findFirst().orElse(null).getAnswer(), vote1.getAnswer());
        assertEquals(caseVote.getVotes().get(userID).stream().findFirst().orElse(null).getPercent(), vote1.getPercent());

    }

    @Test
    void TestVoting_ShouldReturnTrue_WhenComparedToASemiEqualObject() {
        caseVote.voting(userID, answer1, 10.0);


        Vote vote2 = new Vote(answer1, 5.0);


        assertEquals(caseVote.getVotes().get(userID).stream().findFirst().orElse(null).getAnswer(), vote2.getAnswer());
        assertNotEquals(caseVote.getVotes().get(userID).stream().findFirst().orElse(null).getPercent(), vote2.getPercent());

    }

    @Test
    void TestVoting_ShouldReturnTrue_WhenComparedToASemiEqualObjectv2() {
        caseVote.voting(userID, answer1, 10.0);


        Vote vote3 = new Vote(answer, 10.0);


        assertNotEquals(caseVote.getVotes().get(userID).stream().findFirst().orElse(null).getAnswer(), vote3.getAnswer());
        assertEquals(caseVote.getVotes().get(userID).stream().findFirst().orElse(null).getPercent(), vote3.getPercent());
    }

    void TestVoting_ShouldReturnTrue_WhenComparedToANonEqualObject() {
        caseVote.voting(userID, answer1, 10.0);

        Vote vote4 = new Vote(answer, 5.0);


        assertNotEquals(caseVote.getVotes().get(userID).stream().findFirst().orElse(null).getAnswer(), vote4.getAnswer());
        assertNotEquals(caseVote.getVotes().get(userID).stream().findFirst().orElse(null).getPercent(), vote4.getPercent());

    }
}