package org.theShire.domain.medicalDoctor;

import org.theShire.domain.messenger.Chat;
import org.theShire.foundation.DomainAssertion;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

import static org.theShire.domain.exception.MedicalDoctorException.exTypeUser;
import static org.theShire.domain.medicalDoctor.Relation.RelationType.*;

public class UserRelationShip {
    /*      UUID(User)    RELATION
     *       User12       User1,User2,TYPE:OUTGOING
     *       User34       User3, User4, TYPE:ESTABLISHED
     */
    public static HashMap<String,Relation> relationShip;
    
    public UserRelationShip() {
        relationShip = new HashMap<>();

    }

   private String createMapKey(User user1, User user2){
        if (user1.getEntityId().compareTo(user2.getEntityId()) < 0){
            return user1.getEntityId().toString() + user2.getEntityId().toString();
        }else{
            return user2.getEntityId().toString() + user1.getEntityId().toString();
        }
   }

    public Relation getRelation(User user1, User user2) {
        String key = createMapKey(user1, user2);
        return relationShip.get(key);
    }


    public void addRequest(User sender, User receiver, Relation.RelationType type) {
        //TODO new assertion isEqual() can also check for not null in there ;);
        DomainAssertion.isEqual(sender, receiver, "sender and receiver", exTypeUser);
        DomainAssertion.isTrue(!sender.equals(receiver), () -> "sender and receiver can't be the same", exTypeUser);

        // TODO Eventual usage of isInColletction

        DomainAssertion.isInCollection(sender, allUsers, "sender", exTypeUser);
        DomainAssertion.isInCollection(receiver, allUsers, "receiver", exTypeUser);

        String key = createMapKey(sender, receiver);
        Relation relation = new Relation(sender, receiver, type);
        relationShip.put(key, relation);
    }

    public void updateRequest(User sender, User receiver, Relation.RelationType type) {
        //TODO Find a better way not to use 3 times the same method
        DomainAssertion.isNotNull(sender, "sender", exTypeUser);
        DomainAssertion.isNotNull(receiver, "receiver", exTypeUser);
        DomainAssertion.isNotNull(type, "type", exTypeUser);
        Relation relation = getRelation(sender, receiver);
        DomainAssertion.isNotNull(relation, "relation", exTypeUser);
        relation.setType(type);
    }

    //TODO Try to understand the logic of those 4 methods below
    public Relation.RelationType getRelationType(User user1, User user2) {
        return Optional.of(getRelation(user1,user2)).map(Relation::getType).orElse(null);
        /*
        creates a collection of Relations and returns the type of Relation
        between 2 Users if a Relation exists (otherwise returns null)
         */
    }

    public boolean messageable(User user1, User user2) {
        return Optional.of(getRelation(user1,user2)).map(Relation::getType).
                filter(relationType -> relationType == ESTABLISHED).isPresent();
        /*
        Basically delivers the relationType of a Relation between 2 users
        if the relation is established.
         With that we can ensure that chatting between those users is possible
        */
    }


    public Map<User, Relation> getRequest(User user1) {
        return relationShip.values().stream().
                filter(relation -> relation.getUser1().equals(user1) && relation.getType() == INCOMING).
                collect(Collectors.toMap(Relation::getUser2,relation -> relation));
        /*
        Takes the values of each User in the Hashmap (the enums), filters out
        the INCOMING enums and returns a Map that wields the user2 and the Relation to User2
        */
    }

    public Map<User, Relation> getSent(User user1) {
        return relationShip.values().stream().
                filter(relation -> relation.getUser1().equals(user1) && relation.getType() == OUTGOING).
                collect(Collectors.toMap(Relation::getUser2,relation -> relation));

        /*
        Takes the values of each User in the Hashmap (the enums), filters out
        the OUTGOING enums and returns a Map that wields the user2 and the Relation to User2
        */
    }

}
