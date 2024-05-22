package org.theShire.repository;

import org.theShire.domain.medicalDoctor.User;
import org.theShire.domain.richType.Email;
import org.theShire.domain.richType.Name;

import java.util.Optional;

public class UserRepository extends AbstractRepository<User>{

    public Optional<User> findByName(Name name) {
        return entryMap.values().stream()
                .filter(user -> user.getProfile().getFirstName().equals(name))
                .findFirst();
    }

    public Optional<User> findByEmail(Email email) {
        return entryMap.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }
}
