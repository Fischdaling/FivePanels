package org.theShire.domain;

import org.theShire.foundation.DomainAssertion;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import static org.theShire.foundation.DomainAssertion.isBeforeTime;


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