package org.theShire.domain.medicalCase;

import java.util.UUID;

public class Answer {
    //id for answers
    private UUID answerID;
    //the definition of an answer
    private String definition;

//getter & setter-------------------------- //TODO inheritance &co
    public UUID getAnswerID() {
        return answerID;
    }

    public void setAnswerID(UUID answerID) {
        this.answerID = answerID;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
