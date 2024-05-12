package org.theShire.domain;

import org.theShire.foundation.DomainAssertion;
import java.time.Instant;
import java.util.UUID;


public class BaseEntity {
    private UUID entityId;
    private Instant createdAt;
    private Instant updatedAt;

    public BaseEntity(Instant createdAt) {
        this.entityId = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public UUID getEntityId() {
        return entityId;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        DomainAssertion.isBeforeTime(updatedAt,getCreatedAt(),"updatedAt");
        this.updatedAt = updatedAt;
    }

}