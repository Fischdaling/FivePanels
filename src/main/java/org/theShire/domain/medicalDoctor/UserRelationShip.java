package org.theShire.domain.medicalDoctor;

import java.util.HashMap;
import java.util.UUID;

public class UserRelationShip {
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
