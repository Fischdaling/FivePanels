package org.theShire.service;

import org.theShire.domain.BaseEntity;
import org.theShire.domain.media.Content;
import org.theShire.domain.media.ContentText;
import org.theShire.domain.medicalDoctor.User;
import org.theShire.domain.messenger.Chat;
import org.theShire.domain.messenger.Message;
import org.theShire.repository.MessengerRepository;

import java.time.Instant;
import java.util.UUID;

import static org.theShire.presentation.Main.scanner;
import static org.theShire.service.UniversalService.enterUUID;
import static org.theShire.service.UserService.userLoggedIn;

public class ChatService {
    public static final MessengerRepository messengerRepo = new MessengerRepository();



    public static void createChat(User...users){
        messengerRepo.save(new Chat(users));
    }


    public static void sendMessage(Chat chat, Message message){
        chat.sendMessage(message);
        chat.setUpdatedAt(Instant.now());
    }
}
