package org.theShire.domain;

import org.theShire.foundation.DomainAssertion;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import static org.theShire.foundation.DomainAssertion.isBeforeTime;


public class BaseEntity {
    // The Unique Identifier of the entity
    private UUID entityId;
    // The time the entity was created at
    private Instant createdAt;
    // The last time the entity was updated
    private Instant updatedAt;

    public BaseEntity() {
        entityId = UUID.randomUUID();
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

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
        isBeforeTime(updatedAt,createdAt,"updatedAt", RuntimeException.class);
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(entityId, that.entityId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(entityId);
    }
}