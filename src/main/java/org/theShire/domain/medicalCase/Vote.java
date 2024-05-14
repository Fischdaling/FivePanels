package org.theShire.domain.medicalCase;

import static org.theShire.foundation.DomainAssertion.*;

public class Vote {
    private Answer answer;
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
