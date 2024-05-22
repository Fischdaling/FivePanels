package org.theShire.domain.medicalDoctor;

import org.theShire.domain.messenger.Chat;
import static org.theShire.foundation.DomainAssertion.*;


import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import static org.theShire.domain.exception.MedicalDoctorException.exTypeUser;
import static org.theShire.domain.medicalDoctor.Relation.RelationType.*;

public class UserRelationShip {
    /*      UUID(User)    RELATION
     *       1          TYPE:OUTGOING
     *       2          TYPE:INCOMING
     */
    public HashMap<UUID,Relation> relationShip;
    public User sender,receiver;
    
    public UserRelationShip(User sender, User receiver) {
        relationShip = new HashMap<>();
        setSender(sender);
        setReceiver(receiver);
        sendRequest(sender.getEntityId(), receiver.getEntityId());
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = isNotNull(sender,"receiver",exTypeUser);
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = isNotNull(receiver,"receiver",exTypeUser);
    }

    public HashMap<UUID, Relation> getRelationShip() {
        return relationShip;
    }

    public void setRelationShip(HashMap<UUID, Relation> relationShip) {
        this.relationShip = relationShip; //TODO HELP
    }

    public void sendRequest(UUID sender, UUID receiver) {
        isNotNull(sender, "sender", exTypeUser);
        isNotNull(receiver, "receiver", exTypeUser);

        isTrue(!sender.equals(receiver), () -> "sender and receiver can't be the same!", exTypeUser);

        isTrue(relationShip.containsKey(sender), () -> "sender already has a relationship", exTypeUser);
        isTrue(relationShip.containsKey(receiver), () -> "receiver already has a relationship", exTypeUser);

        relationShip.put(sender, new Relation(OUTGOING,receiver,));
        relationShip.put(receiver, new Relation(INCOMING,sender));
    }

    public void acceptRequest(UUID sender, UUID receiver) {
        isNotNull(sender, "sender", exTypeUser);
        isNotNull(receiver, "receiver", exTypeUser);

        isTrue(relationShip.containsKey(sender), () -> "sender has no relationship", exTypeUser);
        isTrue(relationShip.containsKey(receiver), () -> "receiver has no relationship", exTypeUser);


        //Request incoming TODO

        if (relationShip.containsKey(receiver)){
            if (relationShip.get(receiver).getType().equals(Relation.RelationType.INCOMING)){
                relationShip.replace(sender,new Relation(ESTABLISHED, receiver));
                relationShip.replace(receiver,new Relation(ESTABLISHED, sender));

                new Chat(this.sender,this.receiver);
            }
        }
    }
}
