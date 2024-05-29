package org.theShire.domain.medicalCase;

import org.theShire.domain.BaseEntity;
import org.theShire.domain.richType.Name;

import java.time.Instant;
import java.util.UUID;

public class Answer extends BaseEntity {
    //the name of an answer
    private Name name;

    public Answer(String name) {
        super();
        this.name = new Name(name);
    }

    public Answer(UUID uuid, Instant createdAt, Instant updatedAt, Name name) {
        super(uuid, createdAt, updatedAt);
        this.name = name;
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
        sb.append(getEntityId()).append("_").append(getCreatedAt()).append("_").append(getUpdatedAt()).append("_").append(name);
        return sb.toString();
    }

}
