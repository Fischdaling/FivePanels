package org.theShire.domain.media;

import org.theShire.domain.BaseEntity;
import org.w3c.dom.Text;

import java.time.Instant;

public class Content extends BaseEntity {
    private Text text;
    private Media media;

    public Content(Text text, Media media) {
        super(Instant.now());
        this.text = text;
        this.media = media;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        //TODO Assertions
        this.text = text;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        //TODO Assertions
        this.media = media;
    }

    public String contentToString(){
        //TODO toString
        return null;
    }
}
