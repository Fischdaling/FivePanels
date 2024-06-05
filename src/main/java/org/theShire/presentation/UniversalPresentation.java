package org.theShire.presentation;

import org.theShire.domain.media.Content;
import org.theShire.domain.media.ContentText;
import org.theShire.domain.media.Media;

import java.util.List;

import static org.theShire.presentation.Main.scanner;

public class UniversalPresentation {
    public static List<Content> contentUtil(List<Content> content) {
        while(true) {
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
}
