package org.theShire.domain.medicalCase;

import org.theShire.domain.BaseEntity;

import java.util.UUID;

public class Answer extends BaseEntity {
    //id for answers
    private UUID answerID;
    //the definition of an answer
    private String definition;

    public Answer(UUID answerID) {
        this.answerID = answerID;
    }

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
