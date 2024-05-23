package org.theShire.domain.media;

import org.theShire.domain.BaseEntity;

import static org.theShire.domain.exception.MediaException.exTypeMedia;
import static org.theShire.foundation.DomainAssertion.*;


import java.time.Instant;

public class Content extends BaseEntity { //TODO imageWriter
    private ContentText text;
    //wields metadata of the Media class
    private Media media;

    public Content(Media media) {
        this.media = media;
    }

    public Content(ContentText text) {
        super(Instant.now());
        this.text = text;
    }

    public ContentText getContentText() {
        return text;
    }

    public void setContentText(ContentText text) {
        this.text = isNotNull(text, "text", exTypeMedia);
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = isNotNull(media, "media", exTypeMedia);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Content: ");
        if (text != null){
            sb.append(text).append(System.lineSeparator());
        }
        if (media != null){
            sb.append(media).append(System.lineSeparator());
        }
        return sb.toString();
    }
}
