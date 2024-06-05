package org.theShire.service;

import org.theShire.domain.media.Content;
import org.theShire.domain.medicalCase.Answer;
import org.theShire.domain.medicalCase.Case;
import org.theShire.domain.medicalCase.CaseVote;
import org.theShire.domain.medicalDoctor.User;
import org.theShire.foundation.Knowledges;
import org.theShire.repository.CaseRepository;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static org.theShire.domain.exception.MedicalCaseException.exTypeCase;
import static org.theShire.foundation.DomainAssertion.isInCollection;
import static org.theShire.foundation.DomainAssertion.isTrue;
import static org.theShire.service.ChatService.messengerRepo;
import static org.theShire.service.UserService.userLoggedIn;


public class CaseService {
    public static final CaseRepository caseRepo = new CaseRepository();

    public static void findAllCase(){
        caseRepo.findAll().forEach(aCase -> aCase.setViewcount(aCase.getViewcount()+1));
        caseRepo.findAll().forEach(System.out::println);
    }

    public static void deleteCaseById(UUID caseId) {
        Case tmpCase = caseRepo.findByID(caseId);
        isTrue(caseRepo.getEntryMap().containsValue(tmpCase),()->"Case not found",exTypeCase);
            tmpCase.getOwner().removeCase(tmpCase);
            tmpCase.getMembers().forEach(aUser -> aUser.removeCase(tmpCase));
            caseRepo.deleteById(tmpCase.getEntityId());
    }

    public static void likeCase(UUID caseId) {
        Case medCase = caseRepo.findByID(caseId);
        isInCollection(medCase.getEntityId(),caseRepo.getEntryMap().keySet(),()->"Case not Found",exTypeCase);
        isInCollection(userLoggedIn,caseRepo.findByID(medCase.getEntityId()).getMembers(),()->"You are not able to like",exTypeCase);
            medCase.setViewcount(medCase.getViewcount()+1);
            medCase.like(userLoggedIn.getEntityId());
            medCase.setUpdatedAt(Instant.now());
    }

    public static void findCaseById(UUID caseID) {
        Case medicCase =  caseRepo.findByID(caseID);
        isTrue(caseRepo.getEntryMap().containsValue(medicCase),()->"Case not found",exTypeCase);
            medicCase.setViewcount(medicCase.getViewcount() + 1);
            System.out.println(medicCase);
    }

    public static void vote(UUID caseId, List<Answer> answers, List<Double> percentage) {
        Case medCase = caseRepo.findByID(caseId);
        isInCollection(caseId,caseRepo.getEntryMap().keySet(),()->"Case not Found",exTypeCase);
        isInCollection(userLoggedIn,caseRepo.findByID(caseId).getMembers(),()->"You are not able to vote",exTypeCase);
        isTrue(percentage.stream().reduce(0.0, Double::sum) == 100,()->"you have to vote 100%",exTypeCase);
        for (int i = 0; i < answers.size(); i++) {
            Answer answer = answers.get(i);
            Double percent = percentage.get(i);

            medCase.getCaseVote().voting(userLoggedIn.getEntityId(), answer, percent);
        }

    }


    public static Case createCase(User owner,String title,Set<String> knowledges, List<Content> content,CaseVote caseVote, User... members){
        Set<Knowledges> knowledgesSet  = knowledges.stream().map(Knowledges::new).collect(Collectors.toSet());
        Case medCase = new Case(owner, title, knowledgesSet, content, caseVote , members);
        owner.addOwnedCase(medCase);
        owner.setScore(owner.getScore()+5);
        Arrays.stream(members).forEach(user -> user.addMemberOfCase(medCase));
        caseRepo.save(medCase);
        Set<User> chatters = Arrays.stream(members).filter(Objects::nonNull).collect(Collectors.toSet());;
        chatters.add(owner);
        if (messengerRepo.findByMembers(chatters) == null)
            ChatService.createChat(chatters.toArray(User[]::new));
        medCase.setGroupchat(messengerRepo.findByMembers(chatters));
        return medCase;
    }

    public static void correctAnswer(UUID caseId, String answer){
        isTrue(caseRepo.findByID(caseId).getOwner().equals(userLoggedIn),()->"You must be the owner of the case",exTypeCase);
        System.out.println(caseRepo.findByID(caseId).getCaseVote().getAnswers() +"\n");
        caseRepo.findByID(caseId).setCorrectAnswer(new Answer(answer));
        System.out.println(answer + " Was declared as the right Answer. Doctors that made this assumption will earn points.");
        caseRepo.findByID(caseId).setUpdatedAt(Instant.now());
    }

    public static void removeMember(UUID caseId, User member){
        Case medCase = caseRepo.findByID(caseId);
        isTrue(medCase.getOwner().equals(userLoggedIn),()->"You must be the owner of the case",exTypeCase);

        member.removeCase(medCase);
        medCase.removeMember(member);
        medCase.getGroupchat().removeChatter(member.getEntityId());
        medCase.setUpdatedAt(Instant.now());
    }
    public static void addMember(UUID caseId, User member){
        Case medCase = caseRepo.findByID(caseId);
        isTrue(medCase.getOwner().equals(userLoggedIn),()->"You must be the owner of the case",exTypeCase);

        member.addMemberOfCase(medCase);
        medCase.addMember(member);
        medCase.getGroupchat().addPerson(member);
        caseRepo.findByID(caseId).setUpdatedAt(Instant.now());

    }

}
