package org.theShire.domain.medicalCase;
import org.theShire.domain.BaseEntity;
import org.theShire.domain.exception.MedicalCaseException;
import org.theShire.domain.media.Content;
import org.theShire.domain.medicalDoctor.User;
import org.theShire.foundation.DomainAssertion;
import org.theShire.foundation.Knowledges;

import java.time.Instant;
import java.util.*;

import static org.theShire.foundation.DomainAssertion.*;

public class Case extends BaseEntity {
    private static final Class<MedicalCaseException> exType = MedicalCaseException.class;

    //the title provides information about the topic of the case, cannot be left blank and has a max length
    private String title;
    //A list of Content (contains text and metadata)
    private List<Content> content;
    //A list of Knowledges (portraits different medical knowledges in form of hashtags)
    private Set<Knowledges> knowledges;
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


    public Case() {
        super(Instant.now());
    }
    public Case(UUID ownerid,String title, List<Content> content, UUID...members) {
        super(Instant.now());
        this.ownerid = ownerid;
        setTitle(title);
        addContentList(content);
        this.members = new HashSet<>();
        addMembers(members);
    }



    //getter and setter----- //TODO ASSERT
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        hasMaxLength(title, 30, "title",exType);
        this.title = title;
    }

    public List<Content> getContent() {
        return content;
    }



    public Set<Knowledges> getKnowledges() {
        return knowledges;
    }

    public void setKnowledges(Set<Knowledges> knowledges) {
        this.knowledges = knowledges;
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

    public void setOwner(UUID ownerid) {
        this.ownerid = isNotNull(ownerid, "ownerid",exType);
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
        isNotNull(caseVote, "caseVote",exType);
        this.caseVote = caseVote;
    }
    //------------------
    public void addMember(UUID member){
        this.members.add(
            isNotInCollection(member,this.members,"members",exType)
        );
    }

    public void addMembers(UUID...members){
        for (UUID member: members){
            addMember(member);
        }
    }

    public void addContent(Content content) {
        this.content.add(isNotNull(content, "content",exType));
    }

    public void addContentList(List<Content> contentList) {
        this.content.addAll(isNotNull(contentList, "contentList",exType));
    }

    public void addKnowledge(Knowledges knowledges) {
        this.knowledges.add(isNotNull(knowledges, "knowledges",exType));
    }

    public void addCategory(Category category) {
        this.category.add(isNotNull(category, "category",exType));
    }

}
