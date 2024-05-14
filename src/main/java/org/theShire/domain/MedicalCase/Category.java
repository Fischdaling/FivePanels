package org.theShire.domain.MedicalCase;

import static org.theShire.foundation.DomainAssertion.*;

import java.util.Set;
import java.util.UUID;

public class Category {
    private UUID categoryID;
    private Set<UUID> setOfCategory;

    //getter & setter -------------------
    public UUID getCategoryID() {
        return categoryID;
    }

    public Set<UUID> getSetOfCategory() {
        return setOfCategory;
    }

    public void addSetOfCategory(UUID setOfCategory) {
        this.setOfCategory.add(setOfCategory);
    }
}
