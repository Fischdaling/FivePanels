package org.theShire.domain.MedicalDoctor;

import org.theShire.domain.BaseEntity;
import org.theShire.domain.Messenger.Chat;

import java.time.Instant;
import java.time.Year;
import java.util.Set;

public class User extends BaseEntity {

    private UserProfile profile;
    private int score;
    private Set<User> contacts;
    private Set<Chat> chats;
    private Set<Cases> ownedCases;
    private Set<Cases> memberOfCase;


    public User() {
        super(Instant.now());
    }

}
