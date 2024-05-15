package org.theShire.domain.media;

import org.theShire.domain.exception.MediaException;

import static org.theShire.foundation.DomainAssertion.*;

public class Text {
    ////saves the class for exception handling
    private static final Class<MediaException> exType = MediaException.class;
    //a String that holds text
    private String text;


    public Text(String text) {
        setText(text);
    }

    //setter and getter---------------------
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = isNotBlank(text, "text", exType);
    }
}
