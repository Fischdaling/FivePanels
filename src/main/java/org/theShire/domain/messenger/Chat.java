package org.theShire.domain.messenger;

import org.theShire.domain.BaseEntity;
import org.theShire.domain.exception.MessengerException;
import org.theShire.domain.media.Content;
import org.theShire.domain.media.ContentText;
import org.theShire.domain.media.Media;
import org.theShire.domain.medicalDoctor.User;
import org.theShire.domain.medicalDoctor.UserProfile;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static org.theShire.domain.exception.MessengerException.exTypeMes;
import static org.theShire.foundation.DomainAssertion.isNotInCollection;
import static org.theShire.foundation.DomainAssertion.isNotNull;
import static org.theShire.service.UserService.userRepo;

public class Chat extends BaseEntity {
    // The Users in the chat
    private final Set<User> people;
    // All past sent messanges
    private List<Message> chatHistory;

    public Chat(User... chatters) {
        super();
        people = new HashSet<>();
        chatHistory = new ArrayList<>();
        chatHistory.add(new Message(UUID.randomUUID(),new Content(new ContentText("Chat Established"))));

        addChatters(chatters);
    }

    public Chat(UUID uuid, Instant createdAt, Instant updatedAt, Set<User> people, List<Message> chatHistory) {
        super(uuid, createdAt, updatedAt);
        this.people = people;
        this.chatHistory = chatHistory;
    }

    private void addChatters(User... chatters) {
        for (User chatter : chatters) {
            addChatter(chatter);
        }
    }

    private void addChatter(User chatter) {
        people.add(isNotInCollection(chatter, this.people, "chatter", exTypeMes));
        chatter.addChat(this);
    }

    public Set<User> getPeople() {
        return people;
    }

    public List<Message> getChatHistory() {
        return chatHistory;
    }

    public void addChatHistory(Message message) {
        chatHistory.add(isNotNull(message, "message", exTypeMes));
    }

    public void sendMessage(Message message) {
        addChatHistory(message);
        message.setStage(Message.Stage.SENT);
    }

    public void removeChatter(UUID chatter) {
        isNotNull(chatter, "chatter", exTypeMes);
        people.remove(userRepo.findByID(chatter));
    }

    public void removeChatters(UUID... chatters) {
        isNotNull(chatters, "chatter", exTypeMes);
        for (UUID chatter : chatters) {
            people.remove(userRepo.findByID(chatter));
        }
    }

    public void addPerson(User chatter) {
        people.add(isNotNull(chatter, "chatter", exTypeMes));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Chat:").append(getEntityId()).append(System.lineSeparator());
        sb.append(getPeople().stream().map(User::getProfile).map(UserProfile::getFirstName).collect(Collectors.toSet()));
        sb.append(chatHistory);
        return sb.toString();
    }

    public static Chat fromCSVString(String csv) {
        String[] parts = csv.split(";");
        UUID entityId = UUID.fromString(parts[0]);
        Instant createdAt = Instant.parse(parts[1]);
        Instant updatedAt = Instant.parse(parts[2]);
        Set<User> users = parseUsers(parts[3]);
        List<Message> chatHistory = parseHistory(parts[4]);
        return new Chat(entityId, createdAt, updatedAt, users, chatHistory);
    }


    private static Set<User> parseUsers(String part) {
        return Arrays.stream(part.split(","))
                .map(uuid -> userRepo.findByID(UUID.fromString(uuid)))
                .collect(Collectors.toSet());
    }

    private static List<Message> parseHistory(String part) {
        return Arrays.stream(part.split(","))
                .map(Chat::parseMessage)
                .collect(Collectors.toList());
    }

    private static Message parseMessage(String messageStr) {
        String[] parts = messageStr.split(";");
        UUID entityId = UUID.fromString(parts[0]);
        Instant createdAt = Instant.parse(parts[1]);
        Instant updatedAt = Instant.parse(parts[2]);

        UUID senderId = UUID.fromString(parts[3]);
        Message.Stage stage = Message.Stage.valueOf(parts[4]);

        List<Content> contents = Arrays.stream(parts[5].split(","))
                .map(Chat::parseContent)
                .collect(Collectors.toList());

        return new Message(entityId, createdAt, updatedAt, senderId, contents, stage);
    }

    private static Content parseContent(String contentStr) {
        if (contentStr.startsWith("text:")) {
            String text = contentStr.substring(5);
            return new Content(new ContentText(text));
        } else if (contentStr.startsWith("media:")) {
            String media = contentStr.substring(6);
            return new Content(new Media(media));
        } else {
            throw new MessengerException("Unknown content format: " + contentStr);
        }
    }


}
