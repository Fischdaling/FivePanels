package org.theShire.domain.Messenger;

import org.theShire.domain.BaseEntity;
import org.theShire.domain.MedicalDoctor.User;

import java.time.Instant;
import java.util.List;
import java.util.Set;

public class Chat extends BaseEntity {

    private Set<User> people;
    private List<Message> chatHistory;

    public Chat() {
        super(Instant.now());
    }


}
