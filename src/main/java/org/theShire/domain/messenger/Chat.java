package org.theShire.domain.messenger;

import org.theShire.domain.BaseEntity;
import org.theShire.domain.medicalDoctor.User;

import java.time.Instant;
import java.util.*;

import static org.theShire.domain.exception.MessengerException.exTypeMes;
import static org.theShire.foundation.DomainAssertion.isNotInCollection;
import static org.theShire.foundation.DomainAssertion.isNotNull;

public class Chat extends BaseEntity {
    // The Users in the chat
    private Set<User> people;
    // All past sent messanges
    private static List<Message> chatHistory;

    public Chat(User ... chatters) {
        super();
        people = new HashSet<>();
        chatHistory = new ArrayList<>();

        addChatters(chatters);
    }

    public Chat(UUID uuid, Instant createdAt, Instant updatedAt, Set<User> people) {
        super(uuid, createdAt, updatedAt);
        this.people = people;

    }

    private void addChatters(User...chatters) {
        for (User chatter : chatters) {
            addChatter(chatter);
        }
    }
    private void addChatter(User chatter) {
        people.add(isNotInCollection(chatter,this.people,"chatter",exTypeMes));
        chatter.addChat(this);
    }

    public Set<User> getPeople() {
        return people;
    }

    public List<Message> getChatHistory() {
        return chatHistory;
    }

    public static void addChatHistory(Message message) {
        chatHistory.add(isNotNull(message,"message",exTypeMes));
    }

    public void sendMessage(Message message){
        addChatHistory(isNotNull(message,"content",exTypeMes));
        message.setStage(Message.Stage.SENT);
    }
    public void addPerson(User chatter){
        people.add(isNotNull(chatter,"chatter",exTypeMes));
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Chat:").append(getEntityId()).append(System.lineSeparator());
        sb.append(chatHistory);
        return sb.toString();
    }

    @Override
    public String toCSVString(){
        final StringBuilder sb = new StringBuilder(super.toCSVString());
        sb.append(people).append(";");
        sb.append(chatHistory).append(";").append(System.lineSeparator());
        return sb.toString();
    }

}
