package org.theShire.presentation;

import org.theShire.domain.exception.MediaException;
import org.theShire.domain.media.Content;
import org.theShire.domain.media.ContentText;
import org.theShire.domain.media.Media;
import org.theShire.domain.medicalCase.Case;
import org.theShire.domain.medicalDoctor.Relation;
import org.theShire.domain.medicalDoctor.User;
import org.theShire.domain.medicalDoctor.UserProfile;
import org.theShire.domain.medicalDoctor.UserRelationShip;
import org.theShire.domain.messenger.Chat;
import org.theShire.domain.messenger.Message;
import org.theShire.domain.richType.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static User createUser(String firstname, String lastname, String email, String password,String language,String location,String picture,String...educationalTitle){
        Name firstName = new Name(firstname);
        Name lastName = new Name(lastname);
        Email emayl = new Email(email);
        Password passwort = new Password(password);
        Language lang = new Language(language);
        Location loc = new Location(location);
        List<EducationalTitle> titles = Arrays.stream(educationalTitle).map(EducationalTitle::new).toList();
        Media media = new Media(500,400,picture,"500x400");

        UserProfile profile = new UserProfile(lang,loc,media,firstName,lastName,titles);
        User user = new User(passwort,emayl,profile);

        return user;
    }

    public static Case createCase(User owner,String title, List<Content> content, User... members){
           return new Case(owner, title, content, members);
    }

    public static Chat createChat(User...chatters){
         return new Chat(chatters);
    }

    public static void main(String[] args) {

//        Media media = new Media("/Bilbo_Profile.png");
//        BufferedImage img = media.getImage();
        //TODO Relation && builder
        //CREATE USER1 -----------------------------------------------------------------------
        User user1 = createUser("Bilbo","Beutlin","Bilbo@hobbit.com","VerySafe123","Hobbitisch","Auenland","Bilbo Profile","Fassreiter","Meister Dieb");
        //CREATE USER2-----------------------------------------------------------------

        User user2 = createUser("Aragorn","Arathorn","Aragorn@gondor.at","EvenSaver1234","Gondorisch","Gondor","Aragorn Profile","Arathorns Sohn","König von Gondor");
        //CREATE USER3-----------------------------------------------------------------
        User user3 = createUser("Gandalf","Wizardo","Gandalf@Wizardo.out","ICastFireBall!","all","world", "Gandalf Profile","The Gray","The White","Ainur");
        //init Content
        List<Content> contents = new ArrayList<>();
        //add texts
        contents.add(new Content(new ContentText("My First Text")));
        contents.add(new Content(new ContentText("My Second Text")));
        //add Media
        contents.add(new Content(new Media(200,100,"My First Media", "200x100")));

        //Create a Case with user2&user3 as member and user1 as owner
        Case case1 = createCase(user1,"my First Case", contents,user2,user3);

        UserRelationShip relationShip = new UserRelationShip();
        relationShip.addRequest(user1,user2, Relation.RelationType.OUTGOING);
        relationShip.addRequest(user1,user2, Relation.RelationType.INCOMING);
        
        relationShip.addRequest(user2,user3, Relation.RelationType.OUTGOING);
        relationShip.addRequest(user3,user2, Relation.RelationType.INCOMING);



        System.out.println(user1);
        System.out.println(user2);
        System.out.println(user3);
        System.out.println(case1);

    }

}
