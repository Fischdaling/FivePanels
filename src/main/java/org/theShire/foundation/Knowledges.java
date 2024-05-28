package org.theShire.foundation;

import org.theShire.domain.exception.MedicalDoctorException;

import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;
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
        this.knowledge = isInCollection(knowledge, legalKnowledges,"knowledge",exTypeUser);
    }

    public void readKnowledges() {
        // Use Paths.get to ensure the file path is correctly
//        String filePath = Paths.get("resources", "Knowledges.txt").toString();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/Knowledges"))) {
            br.lines().forEach(legalKnowledges::add);
        } catch (IOException e) {
            throw new MedicalDoctorException("Error reading knowledge file: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(knowledge);
        return sb.toString();
    }

}
