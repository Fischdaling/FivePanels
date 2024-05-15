package org.theShire.domain.media;

import org.theShire.domain.BaseEntity;

import static org.theShire.foundation.DomainAssertion.*;

import org.theShire.domain.exception.MediaException;
import org.w3c.dom.Text;

import java.time.Instant;

public class Content extends BaseEntity { //TODO imageWriter
    //saves the class for exception handling
    private static final Class<MediaException> exType = MediaException.class;
    private Text text;
    //wields metadata of the Media class
    private Media media;

    public Content(Media media) {
        super(Instant.now());
        this.media = media;
    }

    public Content(Text text) {
        super(Instant.now());
        this.text = text;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = isNotNull(text, "text", exType);
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = isNotNull(media, "media", exType);
    }

//    @Override
//    public String toString() {
//        return "Content{" +
//                "text=" + text +
//                ", media=" + media +
//                '}';
//    }
}
