package org.theShire.repository;

import org.theShire.domain.medicalDoctor.User;
import org.theShire.domain.messenger.Chat;

import java.util.Set;


public class MessengerRepository extends AbstractRepository<Chat> {

    public Chat findByMembers(Set<User> members) {
        return entryMap.values().stream().filter(chat -> chat.getPeople().equals(members)).findFirst().orElse(null);
    }


}
