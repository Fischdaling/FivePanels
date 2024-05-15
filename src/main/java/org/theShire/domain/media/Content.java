package org.theShire.domain.media;

import org.theShire.domain.BaseEntity;

import static org.theShire.domain.exception.MediaException.exTypeMedia;
import static org.theShire.foundation.DomainAssertion.*;

import org.w3c.dom.Text;

import java.time.Instant;

public class Content extends BaseEntity { //TODO imageWriter
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
        this.text = isNotNull(text, "text", exTypeMedia);
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = isNotNull(media, "media", exTypeMedia);
    }

//    @Override
//    public String toString() {
//        return "Content{" +
//                "text=" + text +
//                ", media=" + media +
//                '}';
//    }
}
