package org.theShire.domain.messenger;

import org.theShire.domain.BaseEntity;
import org.theShire.domain.medicalDoctor.User;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.theShire.domain.exception.MessengerException.exTypeMes;
import static org.theShire.foundation.DomainAssertion.isInCollection;
import static org.theShire.foundation.DomainAssertion.isNotNull;

public class Chat extends BaseEntity { //TODO assertions
    // The Users in the chat
    private Set<User> people;
    // All past sent messanges
    private List<Message> chatHistory;
    // If the chat is a Group or single chat
    private ChatType chatType;

    public Chat() {
        super(Instant.now());
    }

    public Chat(User ... chatters) {
        super(Instant.now());
        people = new HashSet<User>();
        chatHistory = new ArrayList<Message>();

        addChatters(chatters);
    }

    private void addChatters(User...chatters) {
        for (User chatter : chatters) {
            addChatter(chatter);
        }
    }
    private void addChatter(User chatter) {
        people.add(isInCollection(chatter,this.people,"chatter",exTypeMes));
        chatter.addChat(this);
    }

    public Set<User> getPeople() {
        return people;
    }

    public List<Message> getChatHistory() {
        return chatHistory;
    }

    public void addChatHistory(Message message) {
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
        final StringBuilder sb = new StringBuilder("Chat:");
        sb.append(chatHistory);
        return sb.toString();
    }

    enum ChatType{
        SINGLECHAT,
        GROUPCHAT
    }
}
