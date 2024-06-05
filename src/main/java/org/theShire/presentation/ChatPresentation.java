package org.theShire.presentation;

import org.theShire.domain.media.Content;
import org.theShire.domain.messenger.Chat;
import org.theShire.domain.messenger.Message;
import org.theShire.service.ChatService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.theShire.presentation.UniversalPresentation.enterUUID;
import static org.theShire.service.ChatService.messengerRepo;
import static org.theShire.service.UserService.userLoggedIn;

public class ChatPresentation {
    public static void findAllChat(){
        messengerRepo.findAll().forEach(System.out::println);
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
            List<Content> contents = new ArrayList<>();

            UniversalPresentation.contentUtil(contents);
            ChatService.sendMessage(chat,new Message(userLoggedIn.getEntityId(), contents.toArray(new Content[0])));
        }else {
            System.out.println("Chat not found");
        }
    }
}