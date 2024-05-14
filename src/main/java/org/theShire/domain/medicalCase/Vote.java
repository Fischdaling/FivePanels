package org.theShire.domain.medicalCase;

import static org.theShire.foundation.DomainAssertion.*;

public class Vote {
    //saves an answer with the verified Rich type Answer
    private Answer answer;
    //determines how much percent a member wants to add to an answer
    private double percent;


    //getter & setter-----------------------
    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {

        this.percent = percent;
    }
}
