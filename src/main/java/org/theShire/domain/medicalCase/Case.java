package org.theShire.domain.medicalCase;
import org.theShire.domain.BaseEntity;
import org.theShire.domain.media.Content;
import org.theShire.foundation.Knowledges;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Case extends BaseEntity {
    //the title provides information about the topic of the case, cannot be left blank and has a max length
    private String title;
    //A list of Content (contains text and metadata)
    private List<Content> content;
    //A list of Knowledges (portraits different medical knowledges in form of hashtags)
    private Set<Knowledges> Knowledges;
    //portraits how often a given case was viewed
    private int viewcount;
    //wields the UUID of the user that is the owner of a given case
    private UUID ownerid;
    //wields all UUIDs of every member that is part of the case
    private Set<UUID> members;
    //portraits how many users liked a given case
    private int likeCount;
    //remembers which user has already liked a case by their id (prevents a user to like the same case more often)
    private Set<UUID> userLiked;
    //A set of categories (contains different categories of medical cases)
    private Set<Category> category;
    //portraits the total votes of all members combined
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
