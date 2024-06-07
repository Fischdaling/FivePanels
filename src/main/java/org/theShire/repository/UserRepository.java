package org.theShire.repository;

import org.theShire.domain.medicalDoctor.User;

import org.theShire.domain.richType.*;

import java.util.*;
import java.util.stream.Collectors;

public class UserRepository extends AbstractRepository<User> {

    public Set<User> findByName(Name name) {
        return entryMap.values().stream()
                .filter(user -> user.getProfile().getFirstName().value().equalsIgnoreCase(name.value()))
                .collect(Collectors.toSet());
    }

    public Optional<User> findByEmail(Email email) {
        return entryMap.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

}
