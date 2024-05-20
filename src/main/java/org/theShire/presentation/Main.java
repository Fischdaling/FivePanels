package org.theShire.presentation;

import org.theShire.domain.media.Content;
import org.theShire.domain.media.ContentText;
import org.theShire.domain.media.Media;
import org.theShire.domain.medicalDoctor.User;
import org.theShire.domain.medicalDoctor.UserProfile;
import org.theShire.domain.medicalDoctor.UserRelationShip;
import org.theShire.domain.messenger.Chat;
import org.theShire.domain.messenger.Message;
import org.theShire.domain.richType.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //TODO Relation && builder
        //Creating ProfilePic
        Media media = new Media(2000,1500,"I am a Picture", "2000x1500");
        //Create Profile
        UserProfile profile = new UserProfile(new Language("German"),
                new Location("Gondor"),media,new Name("Boromir"),
                new Name("Aragorn"),new EducationalTitle("Dr."),
                new EducationalTitle("arathorn"));


        //Create User
        User user = new User(new Password("Spengergasse"),
                new Email("Boromir@gamil.com"),profile);


        //Create ProfilePic
        Media media1 = new Media(1500,100,"I am a Father", "1500x100");
        //Create Profile
        UserProfile profile1 = new UserProfile(new Language("German"),
                new Location("Gondor"),media1,new Name("Aarathorn"),
                new Name("Aragorn"),new EducationalTitle("Dr."),
                new EducationalTitle("Arathorn"),new EducationalTitle("mag"));
        //Create USer
        User user1 = new User(new Password("Spengergasse123"),
                new Email("Arathorn@gamil.com"),profile1);

        //init Content
        List<Content> contents = new ArrayList<>();
        //add texts
        contents.add(new Content(new ContentText("My First Text")));
        contents.add(new Content(new ContentText("My Second Text")));
        //add Media
        contents.add(new Content(new Media(200,100,"My First Media", "200x100")));

        //Create a Case with user 1 as member and user as owner
        user.createCase("my First Case", contents,user1);

        // TODO NO GOOD Solution
        UserRelationShip relationShip = new UserRelationShip(user,user1);
        relationShip.acceptRequest(user.getEntityId(),user1.getEntityId());

            user.getChatByUser(user).getFirst().sendMessage(new Message(user.getEntityId(),new Content(new ContentText("Hi"))));

//            chat.sendMessage(new Message(user.getEntityId(),new Content(new ContentText("Yo"))));


        System.out.println(user);

    }

}
