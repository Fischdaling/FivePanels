package org.theShire.domain.MedicalCase;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

public class CaseVote {
    private LinkedHashSet<Answers> answers;
    private HashMap<UUID, Set<Vote>> votes;
    private int maxAnswers;
    private double prozentCount;
    private final double maxProzentCount = 100.0;
}
