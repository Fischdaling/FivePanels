package org.theShire.service;

import org.theShire.domain.media.Content;
import org.theShire.domain.media.ContentText;
import org.theShire.domain.messenger.Chat;
import org.theShire.domain.messenger.Message;
import org.theShire.presentation.Main;
import org.theShire.repository.MessengerRepository;

import java.util.UUID;

import static org.theShire.presentation.Main.scanner;

public class ChatService {
    public static final MessengerRepository messengerRepo = new MessengerRepository();
    public static void openChat() {
        boolean exit = false;
        UUID uuid = Main.enterUUID("Enter chat uuid");
        Chat chat = messengerRepo.findByID(uuid);
        if (messengerRepo.getEntryMap().containsKey(uuid)) {
            System.out.println("chat with " + chat.getPeople() + " opened");
            System.out.println(chat.getChatHistory());

            while (!exit) {
                System.out.println("Enter your UUID");
                String user = scanner.nextLine();
                System.out.println("Send a message? true/false");
                exit = scanner.nextBoolean();
                System.out.println("Enter your Message?");
                String message = scanner.nextLine();
                chat.sendMessage(new Message(UUID.fromString(user), new Content(new ContentText(message))));
            }
        }else {
            System.out.println("Chat not found");
        }
    }
}
