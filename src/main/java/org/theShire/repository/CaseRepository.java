package org.theShire.repository;


import org.theShire.domain.medicalCase.Case;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


public class CaseRepository extends AbstractRepository<Case> {
    public Set<Case> getCaseByOwner(UUID ownerId) {
        return entryMap.values().stream()
                .filter(aCase -> aCase.getOwner().getEntityId().equals(ownerId))
                .collect(Collectors.toSet());
    }


}
