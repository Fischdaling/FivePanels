package org.theShire.presentation;

import org.theShire.domain.BaseEntity;
import org.theShire.domain.media.Content;
import org.theShire.domain.media.ContentText;
import org.theShire.domain.media.Media;
import org.theShire.domain.medicalCase.Case;
import org.theShire.domain.medicalDoctor.User;
import org.theShire.domain.messenger.Chat;
import org.theShire.foundation.DomainAssertion;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.theShire.presentation.Main.scanner;
import static org.theShire.service.CaseService.caseRepo;
import static org.theShire.service.ChatService.messengerRepo;
import static org.theShire.service.UserService.userRepo;

public class UniversalPresentation {
    public static List<Content> contentUtil(List<Content> content) {

        while (true) {
            System.out.println("Do you want to add Content true/false");
            boolean addContent = scanner.nextBoolean();
            if (!addContent) {
                break;
            }
            System.out.println("1. Media Content");
            System.out.println("2. Text Content");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Enter Filepath");
                    scanner.nextLine();
                    content.add(new Content(new Media(scanner.nextLine())));
                    break;
                case 2:
                    System.out.println("Enter Text");
                    scanner.nextLine();
                    content.add(new Content(new ContentText(scanner.nextLine())));
                    break;
                default:
                    System.out.println("Invalid choice");
            }

        }
        return content;
    }

    public static void findAll() {
        System.out.println("Enter Entity");
        System.out.println("1. Doctor");
        System.out.println("2. Case");
        System.out.println("3. Chat");
        int entityId = scanner.nextInt();
        switch (entityId) {
            case 1:
                UserPresentation.findAllUser();
                break;
            case 2:
                CasePresentation.findAllCase();
                break;
            case 3:
                ChatPresentation.findAllChat();
                break;
            default:
                System.out.println("invalid command");
        }

    }


    public static <T extends BaseEntity> UUID enterUUID(String enterMessage, Class<T> clazz) {
        DomainAssertion.isNotNull(clazz, "entity", RuntimeException.class);
        StringBuilder str = new StringBuilder();
        if (clazz == User.class) {
            str.append("User").repeat('-', 20).append(System.lineSeparator());
            for (User user : userRepo.findAll()) {
                str.append(user.getProfile().getFirstName()).append(" ").append(user.getProfile().getLastName()).append(System.lineSeparator());
                str.append(user.getEntityId()).append(System.lineSeparator());
            }
        } else if (clazz == Case.class) {
            str.append("Cases").repeat('-', 20).append(System.lineSeparator());
            for (Case medCase : caseRepo.findAll()) {
                str.append(medCase.getTitle()).append(System.lineSeparator());
                str.append(medCase.getEntityId()).append(System.lineSeparator());
            }
        } else if (clazz == Chat.class) {
            str.append("Chat").repeat('-', 20).append(System.lineSeparator());
            for (Chat chat : messengerRepo.findAll()) {
                str.append(chat.getPeople().stream().map(aChat -> aChat.getProfile().getFirstName()).collect(Collectors.toSet()));
                str.append(chat.getEntityId()).append(System.lineSeparator());
            }
        }
        System.out.println(str);

        System.out.println(enterMessage);
        return UUID.fromString(scanner.nextLine());

    }
}
