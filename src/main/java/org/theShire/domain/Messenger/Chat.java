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

    public Chat(Instant createdAt, Set<User> people, List<Message> chatHistory) {
        super(createdAt);
        this.people = people;
        this.chatHistory = chatHistory;
    }

    public Set<User> getPeople() {
        return people;
    }

    public void setPeople(Set<User> people) {
        this.people = people;
    }

    public List<Message> getChatHistory() {
        return chatHistory;
    }

    public void setChatHistory(List<Message> chatHistory) {
        this.chatHistory = chatHistory;
    }

    enum ChatType{
        SINGLECHAT,
        GROUPCHAT
    }
}
