package org.theShire.repository;

import org.theShire.domain.exception.MessengerException;
import org.theShire.domain.media.Content;
import org.theShire.domain.media.ContentText;
import org.theShire.domain.media.Media;
import org.theShire.domain.medicalDoctor.User;
import org.theShire.domain.messenger.Chat;
import org.theShire.domain.messenger.Message;

import java.io.*;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.theShire.service.UserService.userRepo;


public class MessengerRepository extends AbstractRepository<Chat>{

    public Chat findByMembers(Set<User> members){
       return entryMap.values().stream().filter(chat -> chat.getPeople().equals(members)).findFirst().orElse(null);
    }

    @Override
    public void saveEntryMap(String filepath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            for (Chat chat : entryMap.values()) {
                writer.write(chat.toCSVString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(String.format("File %s has a problem", filepath), e);
        }
    }

    @Override
    public void loadEntryMap(String filepath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Chat chat = Chat.fromCSVString(line);
                entryMap.put(chat.getEntityId(), chat);
            }
        } catch (IOException e) {
            throw new MessengerException(e.getMessage());
        }
    }

}
