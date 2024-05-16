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
    private HashMap<UUID,Relation> relationShip;

    public UserRelationShip(UUID sender, UUID receiver) {
        relationShip = new HashMap<>();
        relationShip.put(sender, new Relation(OUTGOING,receiver));
        relationShip.put(receiver, new Relation(INCOMING,sender));
    }

    public HashMap<UUID, Relation> getRelationShip() {
        return relationShip;
    }

    public void setRelationShip(HashMap<UUID, Relation> relationShip) {
        this.relationShip = relationShip; //TODO HELP
    }
}
