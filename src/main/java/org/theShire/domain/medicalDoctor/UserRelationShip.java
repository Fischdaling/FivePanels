package org.theShire.domain.medicalDoctor;

import org.theShire.domain.messenger.Chat;
import org.theShire.foundation.DomainAssertion;

import java.util.*;
import java.util.stream.Collectors;

import static org.theShire.domain.exception.MedicalDoctorException.exTypeUser;
import static org.theShire.domain.medicalDoctor.Relation.RelationType.*;
import static org.theShire.presentation.Main.userRepo;

public class UserRelationShip {
    /*      UUID(User)    RELATION
     *       User12       User1,User2,TYPE:OUTGOING
     *       User34       User3, User4, TYPE:ESTABLISHED
     */
    public static HashMap<String,Relation> relationShip;
    
    public UserRelationShip() {
        relationShip = new HashMap<>();

    }

   private static String createMapKey(User user1, User user2){
        if (user1.getEntityId().compareTo(user2.getEntityId()) < 0){
            return user1.getEntityId().toString() + user2.getEntityId().toString();
        }else{
            return user2.getEntityId().toString() + user1.getEntityId().toString();
        }
   }

    public static Relation getRelation(User user1, User user2) {
        String key = createMapKey(user1, user2);
        return relationShip.get(key);
    }


    public static void sendRequest(User sender, User receiver) {
        DomainAssertion.isNotEqual(sender, receiver, "sender and receiver", exTypeUser);
        DomainAssertion.isTrue(!sender.equals(receiver), () -> "sender and receiver can't be the same", exTypeUser);
        DomainAssertion.isInCollection(sender, userRepo.findAll(), "sender", exTypeUser);
        DomainAssertion.isInCollection(receiver, userRepo.findAll(), "receiver", exTypeUser);

        String keyIncoming = createMapKey(sender, receiver);
        String keyOutgoing = createMapKey(receiver, sender);

        Relation relationIncoming = new Relation(sender, receiver, INCOMING);
        Relation relationOutgoing = new Relation(receiver, sender, OUTGOING);

        relationShip.put(keyIncoming, relationIncoming);
        relationShip.put(keyOutgoing, relationOutgoing);
    }


    public static void acceptRequest(User sender, User receiver) {
        DomainAssertion.isNotNull(sender, "sender", exTypeUser);
        DomainAssertion.isNotNull(receiver, "receiver", exTypeUser);

        String keyIncoming = createMapKey(sender, receiver);
        String keyOutgoing = createMapKey(receiver, sender);

        Relation relationIncoming = relationShip.get(keyIncoming);
        Relation relationOutgoing = relationShip.get(keyOutgoing);

        if (relationIncoming != null && relationOutgoing != null) {
            relationIncoming.setType(ESTABLISHED);
            relationOutgoing.setType(ESTABLISHED);

            if (messageable(sender, receiver)) {
                new Chat(sender, receiver);
            }
        }
    }

    public Relation.RelationType getRelationType(User user1, User user2) {
        return Optional.of(getRelation(user1,user2)).map(Relation::getType).orElse(null);
        /*
        creates a collection of Relations and returns the type of Relation
        between 2 Users if a Relation exists (otherwise returns null)
         */
    }

    public static boolean messageable(User user1, User user2) {
        return Optional.of(getRelation(user1,user2)).map(Relation::getType).
                filter(relationType -> relationType == ESTABLISHED).isPresent();
        /*
        Basically delivers the relationType of a Relation between 2 users
        if the relation is established.
         With that we can ensure that chatting between those users is possible
        */
    }


    public static Set<User> getRequest(User user1) {
        return relationShip.values().stream()
                .filter(relation -> relation.getUser1().equals(user1) && relation.getType() == INCOMING).map(Relation::getUser2)
                .collect(Collectors.toSet());
        /*
        Takes the values of each User in the Hashmap (the enums), filters out
        the INCOMING enums and returns a Map that wields the user2 and the Relation to User2
        */
    }

    public static Map<User, Relation> getSent(User user1) {
        return relationShip.values().stream()
                .filter(relation -> relation.getUser1().equals(user1) && relation.getType() == OUTGOING)
                .collect(Collectors.toMap(Relation::getUser2, relation -> relation));

        /*
        Takes the values of each User in the Hashmap (the enums), filters out
        the OUTGOING enums and returns a Map that wields the user2 and the Relation to User2
        */
    }

}
