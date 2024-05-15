package org.theShire.domain.media;

import org.theShire.domain.exception.MediaException;
import static org.theShire.foundation.DomainAssertion.*;

import java.util.Set;
import java.util.UUID;

public class Title {
    private static final Class<MediaException> exType = MediaException.class;
    //a set of titles saved by their UUID
    Set<UUID> setOfTitles;

    public Title(Set<UUID> setOfTitles) {
        this.setOfTitles = setOfTitles;
    }
//adder & getter---------------------------

    public Set<UUID> getSetOfTitles() {
        return setOfTitles;
    }

    public void addTitle(UUID title) {
        setOfTitles.add(title);
    }
}
