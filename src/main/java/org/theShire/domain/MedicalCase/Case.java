package org.theShire.domain.MedicalCase;

import org.theShire.domain.BaseEntity;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public class Case extends BaseEntity {
    private String title;
    private List<Content> content;
    private Set<Specialization> specialization;
    private int viewcunt;
    private UUID ownerid;
    private Set<UUID> members;
    private int likeCount;
    private Set<UUID> userLiked;
    private Set<Category> category;
    private CaseVote caseVote;

    public Case(Instant createdAt) {
        super(Instant.now());
    }
}
