package org.theShire.domain.MedicalCase;

import org.theShire.domain.BaseEntity;
import org.theShire.domain.Media.Content;
import org.theShire.foundation.Knowledges;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.theShire.foundation.DomainAssertion.*;

public class Case extends BaseEntity {
    private String title;
    private List<Content> content;
    private Set<Knowledges> knowledges;
    private int viewcount;
    private UUID ownerid;
    private Set<UUID> members;
    private int likeCount;
    private Set<UUID> userLiked;
    private Set<Category> category;
    private CaseVote caseVote;


    public Case(Instant createdAt) {
        super(Instant.now());
    }

    //getter and setter----- //TODO ASSERT
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        hasMaxLength(title, 30, "title");
        this.title = title;
    }

    public List<Content> getContent() {
        return content;
    }

    public void addContent(Content content) {
        isNotNull(content, "content");
        this.content.add(content);
    }

    public Set<Knowledges> getKnowledges() {
        return knowledges;
    }

    public void addKnowledges(Knowledges knowledges) {
        isNotNull(knowledges, "knowledges");
        this.knowledges.add(knowledges);
    }

    public int getViewcount() {
        return viewcount;
    }

    public UUID getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(UUID ownerid) {
        isNotNull(ownerid, "ownerid");
        this.ownerid = ownerid;
    }

    public Set<UUID> getMembers() {
        return members;
    }

    public void setMembers(UUID members) {
        isNotNull(members, "members");
        this.members.add(members);
    }

    public int getLikeCount() {
        return likeCount;
    }

    public Set<UUID> getUserLiked() {
        return userLiked;
    }

    public Set<Category> getCategory() {
        return category;
    }

    public void addCategory(Category category) {
        isNotNull(category, "category");
        this.category.add(category);
    }

    public CaseVote getCaseVote() {
        return caseVote;
    }

    public void setCaseVote(CaseVote caseVote) {
        isNotNull(caseVote, "caseVote");
        this.caseVote = caseVote;
    }
    //------------------
}
