package org.theShire.domain.medicalCase;

import org.theShire.domain.BaseEntity;
import org.theShire.domain.media.Content;
import org.theShire.domain.medicalDoctor.User;
import org.theShire.domain.messenger.Chat;
import org.theShire.foundation.Knowledges;

import java.util.*;
import java.util.stream.Collectors;

import static org.theShire.domain.exception.MedicalCaseException.exTypeCase;
import static org.theShire.foundation.DomainAssertion.*;

public class Case extends BaseEntity {

    //the title provides information about the topic of the case, cannot be left blank and has a max length
    private String title;
    //A list of Content (contains text and metadata)
    private List<Content> content;
    //A list of Knowledges (portraits different medical knowledges in form of hashtags)
    private Set<Knowledges> knowledges;
    //portraits how often a given case was viewed
    private int viewcount;
    //wields the UUID of the user that is the owner of a given case
    private User owner;
    //wields all UUIDs of every member that is part of the case
    private Set<User> members;
    //portraits how many users liked a given case
    private int likeCount;
    //remembers which user has already liked a case by their id (prevents a user to like the same case more often)
    private Set<UUID> userLiked;
    //A set of categories (contains different categories of medical cases)
    private Set<Category> category;
    //portraits the total votes of all members combined
    private CaseVote caseVote;


    public Case(User owner, String title, List<Content> content, User... members) {
        super();
        setOwner(owner);
        setTitle(title);
        this.content = new ArrayList<>();
        addContentList(content);
        this.members = new HashSet<>();
        addMembers(members);
        Chat caseChat = new Chat(members);
        caseChat.addPerson(owner);
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = hasMaxLength(title, 30, "title", exTypeCase);
    }

    public List<Content> getContent() {
        return content;
    }


    public Set<Knowledges> getKnowledges() {
        return knowledges;
    }

//    public void setKnowledges(Set<Knowledges> knowledges) {
//        this.knowledges = knowledges;
//    }

    public int getViewcount() {
        return viewcount;
    }

//    public void setViewcount(int Viewcount) {
//        this.viewcount = Viewcount;
//    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = isNotNull(owner, "owner", exTypeCase);
    }

    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }

    public int getLikeCount() {
        return likeCount;
    }

//    public void setLikeCount(int likeCount) {
//        this.likeCount = likeCount;
//    }

    public Set<UUID> getUserLiked() {
        return userLiked;
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
        this.caseVote = isNotNull(caseVote, "caseVote", exTypeCase);
    }

    //------------------
    public void addMember(User member) {
        this.members.add(
                isNotInCollection(member, this.members, "members", exTypeCase)
        );
    }

    public void addMembers(User... members) {
        for (User member : members) {
            addMember(member);
        }
    }

    public void addContent(Content content) {
        this.content.add(isNotNull(content, "content", exTypeCase));
    }

    public void addContentList(List<Content> contentList) {
        this.content.addAll(isNotNull(contentList, "contentList", exTypeCase));
    }

    public void addKnowledge(Knowledges knowledges) {
        this.knowledges.add(isNotNull(knowledges, "knowledges", exTypeCase));
    }

    public void addCategory(Category category) {
        this.category.add(isNotNull(category, "category", exTypeCase));
    }

    public void like(UUID userLiked) {
        this.userLiked.add(isInCollection(userLiked, this.userLiked, "userLiked", exTypeCase));
        likeCount++;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Case: ").append(title).repeat('-',60).append(System.lineSeparator());
        sb.append(content).append(System.lineSeparator());
        sb.append("knowledges: ").append(knowledges).append(System.lineSeparator());
        sb.append("viewcount: ").append(viewcount).append(System.lineSeparator());
        sb.append("owner: ").append(owner.getProfile().getFirstName()).append(System.lineSeparator());
        sb.append("members: ").append(members.stream().
                map(user -> user.getProfile().getFirstName()).collect(Collectors.toList())).append(System.lineSeparator());
        sb.append("likeCount: ").append(likeCount).append(System.lineSeparator());
        sb.append("userLiked: ").append(userLiked).append(System.lineSeparator());
        sb.append("category: ").append(category).append(System.lineSeparator());
        sb.append("caseVote: ").append(caseVote).append(System.lineSeparator());
        return sb.toString();
    }
}
