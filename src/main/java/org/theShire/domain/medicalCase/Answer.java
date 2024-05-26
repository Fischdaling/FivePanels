package org.theShire.domain.medicalCase;

import org.theShire.domain.BaseEntity;
import org.theShire.domain.richType.Name;

import java.util.UUID;

public class Answer extends BaseEntity {
    //the name of an answer
    private Name name;

    public Answer(String name) {
        super();
        this.name = new Name(name);
    }

    //getter & setter--------------------------


    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("id: ").append(getEntityId());
        sb.append("name: ").append(name);
        return sb.toString();
    }
}
