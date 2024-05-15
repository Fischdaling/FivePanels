package org.theShire.domain.media;

import org.theShire.domain.exception.MediaException;

import static org.theShire.domain.exception.MediaException.exTypeMedia;
import static org.theShire.foundation.DomainAssertion.*;

public class Text {
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
        this.text = isNotBlank(text, "text", exTypeMedia);
    }
}
