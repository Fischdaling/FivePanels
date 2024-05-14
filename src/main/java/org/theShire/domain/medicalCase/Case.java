package org.theShire.domain.medicalCase;
import org.theShire.domain.BaseEntity;
import org.theShire.domain.media.Content;
import org.theShire.foundation.Knowledges;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Case extends BaseEntity {
    private String title;
    private List<Content> content;
    private Set<Knowledges> Knowledges;
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
        this.title = title;
    }

    public List<Content> getContent() {
        return content;
    }

    public void setContent(List<Content> content) {
        this.content = content;
    }

    public Set<Knowledges> getKnowledges() {
        return Knowledges;
    }

    public void setKnowledges(Set<Knowledges> Knowledges) {
        this.Knowledges = Knowledges;
    }

    public int getViewcount() {
        return viewcount;
    }

    public void setViewcount(int Viewcount) {
        this.viewcount = Viewcount;
    }

    public UUID getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(UUID ownerid) {
        this.ownerid = ownerid;
    }

    public Set<UUID> getMembers() {
        return members;
    }

    public void setMembers(Set<UUID> members) {
        this.members = members;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public Set<UUID> getUserLiked() {
        return userLiked;
    }

    public void setUserLiked(Set<UUID> userLiked) {
        this.userLiked = userLiked;
    }

    public Set<Category> getCategory() {
        return category;
    }

    public void setCategory(Set<Category> category) {
        this.category = category;
    }

    public CaseVote getCaseVote() {
        return caseVote;
    }

    public void setCaseVote(CaseVote caseVote) {
        this.caseVote = caseVote;
    }
    //------------------
}
