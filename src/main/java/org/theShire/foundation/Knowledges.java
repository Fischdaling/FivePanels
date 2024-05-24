package org.theShire.foundation;

import org.theShire.domain.exception.MedicalDoctorException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static org.theShire.domain.exception.MedicalDoctorException.exTypeUser;
import static org.theShire.foundation.DomainAssertion.isInCollection;
import static org.theShire.foundation.DomainAssertion.isNotInCollection;

public class Knowledges {
    // list of legal knowledges that are good and confirmed
    private static Set<String> legalKnowledges;
    // the knowledge the user wants
    private String knowledge;

    public Knowledges(String knowledge) {
        legalKnowledges = new HashSet<String>();
        readKnowledges();
        setKnowledge(knowledge);
    }

    public String getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(String knowledge) {
        this.knowledge = isNotInCollection(knowledge, legalKnowledges,"knowledge",exTypeUser);
    }

    public void readKnowledges() {
        try {
            //TODO Not found file
            BufferedReader br = new BufferedReader(new FileReader("Knowledges.txt"));
            br.lines().forEach(line -> legalKnowledges.add(line));
        } catch (FileNotFoundException e) {
            throw new MedicalDoctorException("Knowledge file not found");
        }
    }

}
