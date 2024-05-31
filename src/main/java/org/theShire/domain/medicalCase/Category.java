package org.theShire.domain.medicalCase;

import java.util.Set;
import java.util.UUID;

import static org.theShire.domain.exception.MedicalCaseException.exTypeCase;
import static org.theShire.foundation.DomainAssertion.isNotNull;

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

    public void addCategory(UUID category) {
        this.setOfCategory.add(isNotNull(category, "category", exTypeCase));
    }
}
