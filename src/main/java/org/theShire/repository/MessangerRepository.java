package org.theShire.repository;

import org.theShire.domain.exception.MediaException;
import org.theShire.domain.exception.MedicalCaseException;
import org.theShire.domain.exception.MessengerException;
import org.theShire.domain.media.Content;
import org.theShire.domain.media.ContentText;
import org.theShire.domain.media.Media;
import org.theShire.domain.medicalCase.Case;
import org.theShire.domain.medicalDoctor.User;
import org.theShire.domain.messenger.Chat;
import org.theShire.domain.messenger.Message;
import org.theShire.foundation.Knowledges;

import java.io.*;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static org.theShire.presentation.Main.caseRepo;
import static org.theShire.presentation.Main.userRepo;

public class MessangerRepository extends AbstractRepository<Chat>{
    @Override
    public void saveEntryMap(String filepath) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filepath))) {
            for (Chat value : entryMap.values()) {
                String csvFile = value.toCSVString();
                bufferedWriter.write(csvFile);
            }
            System.out.println("Successfully saved " + filepath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(String.format("File %s is not found", filepath), e);

        } catch (IOException e) {
            throw new RuntimeException(String.format("File %s has a problem", filepath), e);
        }
    }
    @Override
    public void loadEntryMap(String filepath) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath))) {
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(";");
                UUID entityId = UUID.fromString(parts[0]);
                Instant createdAt = Instant.parse(parts[1]);
                Instant updatedAt = Instant.parse(parts[2]);
                Set<User> users = saveUser(parts[3]);
                List<Message> chatHistory = saveHistory(parts[4]);
                chatHistory.forEach(Chat::addChatHistory);

                Chat chat = new Chat(entityId,createdAt,updatedAt,users);
            }
        } catch (FileNotFoundException e) {
            throw new MessengerException(e.getMessage());
        } catch (IOException e) {
            throw new MediaException(e.getMessage());
        }
    }

    private Set<User> saveUser(String part) {
        Set<String> str = Arrays.stream(part.split(",")).collect(Collectors.toSet());
        return str.stream().map((s -> userRepo.findByID(UUID.fromString(s)))).collect(Collectors.toSet());
    }

    private List<Message> saveHistory(String part) {
        if (part == null || part.isEmpty()) {
            return Collections.emptyList();
        }

        String[] messageParts = part.split(",");
        List<Message> messages = new ArrayList<>();
        for (String messageStr : messageParts) {

            Message message = saveMessage(messageStr);
            messages.add(message);
        }
        return messages;
    }

    private Message saveMessage(String messageStr) {
        String[] parts = messageStr.split(";");
        UUID entityId = UUID.fromString(parts[0]);
        Instant createdAt = Instant.parse(parts[1]);
        Instant updatedAt = Instant.parse(parts[2]);

        UUID senderId = UUID.fromString(parts[3]);
        Message.Stage stage = Message.Stage.valueOf(parts[4]);

        String[] contentParts = parts[5].split(",");
        List<Content> contents = new ArrayList<>();
        for (String contentStr : contentParts) {

            Content content = saveContent(contentStr);
            contents.add(content);
        }

        return new Message(entityId,createdAt,updatedAt,senderId, contents,stage);
    }

    private Content saveContent(String contentStr) {
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
