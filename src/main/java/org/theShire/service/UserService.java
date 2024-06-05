package org.theShire.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.theShire.domain.media.Media;
import org.theShire.domain.medicalCase.Case;
import org.theShire.domain.medicalDoctor.User;
import org.theShire.domain.medicalDoctor.UserProfile;
import org.theShire.domain.medicalDoctor.UserRelationShip;
import org.theShire.domain.richType.*;
import org.theShire.foundation.Knowledges;
import org.theShire.repository.UserRepository;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static org.theShire.domain.exception.MedicalDoctorException.exTypeUser;
import static org.theShire.domain.medicalDoctor.Relation.RelationType.ESTABLISHED;
import static org.theShire.foundation.DomainAssertion.isNotNull;
import static org.theShire.foundation.DomainAssertion.isTrue;
import static org.theShire.service.ChatService.messengerRepo;

public class UserService {
    public static final UserRepository userRepo = new UserRepository();
    public static UserRelationShip relations = new UserRelationShip(); // Important or Error
    public static User userLoggedIn = null;

    public static List<User> findAllUser(){
       return userRepo.findAll();

    }

    public static void deleteUserById (UUID userId) {
        User user = userRepo.findByID(userId);
        isTrue(userRepo.existsById(userId),()->"User not found",exTypeUser);
        userRepo.deleteById(userId);
        Set<Case> medCase = user.isMemberOfCases();
        medCase.forEach(mCase -> mCase.removeMember(user));
        if (userLoggedIn.getEntityId().equals(userId));
            //throw user out
        user.isMemberOfCases().forEach(aCase -> aCase.setUpdatedAt(Instant.now()));
    }

    public static Set<User> findByName (String name) {
        Set<User> user = userRepo.findByName(new Name(name));
        return isNotNull(user,"user",exTypeUser);
    }


    public static void cancelFriendship(User sender, User receiver) {
        Set<User> users = new HashSet<>();
        users.add(sender);
        users.add(receiver);
        UserRelationShip.declineRequest(sender,receiver);
//        if (UserRelationShip.getRelation(sender,receiver)!= null) {
//            if (UserRelationShip.getRelation(sender, receiver).getType().equals(ESTABLISHED)) {
//                messengerRepo.deleteById(messengerRepo.findByMembers(users).getEntityId());
//            }
//        }
    }

    public static void acceptRequest(User sender, User receiver) {
        isTrue(UserRelationShip.getRequest(sender).contains(receiver),()->"Receiver not found.", exTypeUser);
        UserRelationShip.acceptRequest(sender, receiver);
        receiver.setUpdatedAt(Instant.now());
        sender.setUpdatedAt(Instant.now());
    }

    public static void sendRequest(User sender, User receiver) {
        isTrue(userRepo.getEntryMap().containsKey(receiver.getEntityId()),()->"Receiver not found.",exTypeUser);
        UserRelationShip.sendRequest(sender, receiver);
    }

    public static Set<User> seeIncoming(User sender) {
        Set<User> incomingRequests = UserRelationShip.getRequest(sender);
        if (incomingRequests.isEmpty()){
            return null;
        }else
            return incomingRequests;
    }


    public static UserProfile updateProfile(Language language, Location location, Media profilePic, Name firstname, Name lastname, List<EducationalTitle> title) {
        return new UserProfile(language, location, profilePic, firstname, lastname, title);
    }


    public static User createUser (UUID uuid, Name firstname, Name lastname, Email email, Password
        password, Language language, Location location, String picture, Set <String> specialization, String... educationalTitle){
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
        Name firstName = firstname;
        Name lastName = lastname;
        Email emayl = email;
        Password passwort = password;
        Language lang = language;
        Location loc = location;
        List<EducationalTitle> titles = Arrays.stream(educationalTitle).map(EducationalTitle::new).toList();
        Media media = new Media(500, 400, picture, "500x400");
        Set<Knowledges> knowledges = specialization.stream().map(Knowledges::new).collect(Collectors.toSet());
        UserProfile profile = updateProfile(lang, loc, media, firstName, lastName, titles);
        User user = new User(uuid, passwort, emayl, profile, knowledges);
        userRepo.save(user);
        return user;
    }


        public static User login (String email, String password) {
            Optional<User> userOpt = userRepo.findByEmail(new Email(email));

            isTrue(userOpt.isPresent(),()->"User not found.", exTypeUser);
                User user = userOpt.get();
                                                            //The Password entered //The Password from the User
            BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword().value());
            isTrue(result.verified,()->"Invalid password.",exTypeUser);
            return user;

    }

}
