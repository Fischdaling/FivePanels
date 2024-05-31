package org.theShire.service;

import org.theShire.domain.media.Content;
import org.theShire.domain.media.ContentText;
import org.theShire.domain.medicalDoctor.User;
import org.theShire.domain.messenger.Chat;
import org.theShire.domain.messenger.Message;
import org.theShire.repository.MessengerRepository;

import java.util.UUID;

import static org.theShire.presentation.Main.scanner;
import static org.theShire.service.UniversalService.enterUUID;
import static org.theShire.service.UserService.userLoggedIn;

public class ChatService {
    public static final MessengerRepository messengerRepo = new MessengerRepository();

    public static void createChat(User sender, User receiver){
        messengerRepo.save(new Chat(sender,receiver));
    }

    public static void openChat() {
        UUID uuid = enterUUID("Enter chat uuid");
        Chat chat = messengerRepo.findByID(uuid);
        if (messengerRepo.getEntryMap().containsKey(uuid) && messengerRepo.getEntryMap().get(uuid).getPeople().contains(userLoggedIn)) {
            System.out.print("chat with ");
            chat.getPeople().forEach(person -> System.out.print(person.getProfile().getFirstName()));
            System.out.print(" opened");
            chat.getChatHistory().stream().filter(message -> !message.getSenderId().equals(userLoggedIn.getEntityId())).forEach(message -> message.setStage(Message.Stage.READ));
            System.out.println(chat.getChatHistory());

            while (true) {
                System.out.println("Send a message? true/false");
                if (!scanner.nextBoolean()){
                    break;
                }

                System.out.println("Enter your Message: ");
                scanner.nextLine();
                String message = scanner.nextLine();
                chat.sendMessage(new Message(userLoggedIn.getEntityId(), new Content(new ContentText(message))));
            }
        }else {
            System.out.println("Chat not found");
        }
    }
}
