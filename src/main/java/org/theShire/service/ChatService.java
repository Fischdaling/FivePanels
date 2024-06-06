package org.theShire.service;

import org.theShire.domain.medicalDoctor.User;
import org.theShire.domain.messenger.Chat;
import org.theShire.domain.messenger.Message;
import org.theShire.repository.MessengerRepository;

import java.time.Instant;
import java.util.List;

public class ChatService {
    public static final MessengerRepository messengerRepo = new MessengerRepository();


    public static List<Chat> findAllChat(){
        return messengerRepo.findAll();
    }

    public static Chat createChat(User...users){
        return messengerRepo.save(new Chat(users));
    }

    public static void sendMessage(Chat chat, Message message){
        chat.sendMessage(message);
        chat.setUpdatedAt(Instant.now());
    }
}
