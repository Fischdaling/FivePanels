package org.theShire.domain.medicalDoctor;

import java.util.HashMap;
import java.util.UUID;

public class UserRelationShip {
    /*      UUID(User)    RELATION
     *       1          TYPE:OUTGOING,RELATEDUSER:2
     *       2          TYPE:INCOMING,RELATEDUSER:1
     */
    private HashMap<UUID,Relation> relationShip;

    public UserRelationShip(HashMap<UUID, Relation> relationShip) {
        this.relationShip = relationShip;
    }

    public HashMap<UUID, Relation> getRelationShip() {
        return relationShip;
    }

    public void setRelationShip(HashMap<UUID, Relation> relationShip) {
        this.relationShip = relationShip;
    }
}
