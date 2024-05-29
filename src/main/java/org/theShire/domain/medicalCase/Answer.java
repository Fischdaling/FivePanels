package org.theShire.domain.medicalCase;

import org.theShire.domain.richType.Name;


public class Answer{
    //the name of an answer
    private Name answerName;

    public Answer(String name) {
        this.answerName = new Name(name);
    }



    //getter & setter--------------------------


    public Name getName() {
        return answerName;
    }

    public void setName(Name name) {
        this.answerName = name;
    }

    @Override
    public String toString() {
        return answerName.value();
    }

}
