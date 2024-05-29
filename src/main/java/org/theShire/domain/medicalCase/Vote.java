package org.theShire.domain.medicalCase;

import org.theShire.domain.medicalDoctor.User;
import org.theShire.domain.medicalCase.Case;
import org.theShire.foundation.DomainAssertion;
import org.theShire.repository.CaseRepository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.theShire.domain.exception.MedicalCaseException.exTypeCase;
import static org.theShire.foundation.DomainAssertion.*;

public class Vote {
    //saves an answer with the verified Rich type Answer
    private Answer answer;
    //determines how much percent a member wants to add to an answer
    private double percent;


    public Vote(Answer answer, double percent) {
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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(answer).append(", ");
        sb.append(percent);
        return sb.toString();
    }
}
