package org.theShire.domain.medicalCase;

import static org.theShire.domain.exception.MedicalCaseException.exTypeCase;
import static org.theShire.foundation.DomainAssertion.*;

import java.util.Set;
import java.util.UUID;

public class Category {
    //A UUID for each Category
    private UUID categoryID;
    //A Set of UUIDs (saves all categories)
    private Set<UUID> setOfCategory;

    //getter & setter -------------------
    public UUID getCategoryID() {
        return categoryID;
    }

    public Set<UUID> getSetOfCategory() {
        return setOfCategory;
    }

    public void addSetOfCategory(UUID setOfCategory) {
        this.setOfCategory.add(isNotNull(setOfCategory, "setOfCategory", exTypeCase));
    }
}
