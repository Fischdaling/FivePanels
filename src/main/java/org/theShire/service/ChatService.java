package org.theShire.service;

import org.theShire.domain.medicalDoctor.User;
import org.theShire.domain.messenger.Chat;
import org.theShire.domain.messenger.Message;
import org.theShire.repository.MessengerRepository;

import java.time.Instant;

public class ChatService {
    public static final MessengerRepository messengerRepo = new MessengerRepository();


    public static void findAllChat(){
        messengerRepo.findAll().forEach(System.out::println);
    }

    public static void createChat(User...users){
        messengerRepo.save(new Chat(users));
    }

    public static void sendMessage(Chat chat, Message message){
        chat.sendMessage(message);
        chat.setUpdatedAt(Instant.now());
    }
}
