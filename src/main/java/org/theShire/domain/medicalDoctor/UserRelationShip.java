package org.theShire.domain.medicalDoctor;

import java.util.HashMap;
import java.util.UUID;

import static org.theShire.domain.medicalDoctor.Relation.RelationType.INCOMING;
import static org.theShire.domain.medicalDoctor.Relation.RelationType.OUTGOING;

public class UserRelationShip {
    /*      UUID(User)    RELATION
     *       1          TYPE:OUTGOING,RELATEDUSER:2
     *       2          TYPE:INCOMING,RELATEDUSER:1
     */
    public HashMap<UUID,Relation> relationShip;

    public UserRelationShip(UUID sender, UUID receiver) {
        relationShip = new HashMap<>();
        sendRequest(sender, receiver);
    }

    public HashMap<UUID, Relation> getRelationShip() {
        return relationShip;
    }

    public void setRelationShip(HashMap<UUID, Relation> relationShip) {
        this.relationShip = relationShip; //TODO HELP
    }

    public void sendRequest(UUID sender, UUID reciever) {
        //TODO ASSERTION
        relationShip.put(sender,new Relation(Relation.RelationType.OUTGOING,reciever));
        relationShip.put(reciever,new Relation(Relation.RelationType.INCOMING,sender));
    }

    public void acceptRequest(UUID sender, UUID reciever) {
        //TODO ASSERTION
        if (relationShip.containsKey(reciever)){
            if (relationShip.get(reciever).getType().equals(Relation.RelationType.INCOMING)){
                relationShip.replace(sender,new Relation(Relation.RelationType.ESTABLISHED,reciever));
                relationShip.replace(reciever,new Relation(Relation.RelationType.ESTABLISHED,sender));

                // TODO add chat & add to contacts
            }
        }
    }
}
