package org.theShire.domain.medicalCase;

import org.theShire.domain.medicalDoctor.User;
import org.theShire.domain.medicalCase.Case;
import org.theShire.foundation.DomainAssertion;

import java.util.UUID;

import static org.theShire.domain.exception.MedicalCaseException.exTypeCase;
import static org.theShire.foundation.DomainAssertion.*;

public class Vote {
    //saves an answer with the verified Rich type Answer
    private Answer answer;
    //determines how much percent a member wants to add to an answer
    private double percent;

    private UUID voter;


    public Vote(Answer answer, double percent, UUID voter) {
        this.answer = answer;
        this.percent = percent;
        this.voter = voter;
        setAnswer(answer);
        setPercent(percent);
    }

    //getter & setter-----------------------
    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = isNotNull(answer,"answer", exTypeCase);
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        greaterEqualsZero(percent, "percent",exTypeCase);
        this.percent = percent;
    }

    public UUID getVoter() {
        return voter;
    }

    public void setVoter(UUID voter) {
//        this.voter = isInCollection(voter, ,"voter", exTypeCase);
        this.voter = voter ;
    }
}
