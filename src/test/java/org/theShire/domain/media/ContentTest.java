package org.theShire.domain.media;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.theShire.domain.media.Content;
import org.theShire.domain.media.ContentText;
import org.theShire.domain.media.Media;

public class ContentTest {

    Content contentMedia;
    Content contentText;
    Media media;
    ContentText text;
    @BeforeEach
    void setup(){
        media = new Media(500,500,"I am a Test pictuer","500x500");
        contentMedia = new Content(media);
        text = new ContentText("I am a test Text");
        contentText = new Content(text);
    }

    @Test
    void constructorWithMedia_ShouldInitializeMedia_WhenCalled() {
        assertEquals(media, contentMedia.getMedia());
    }

    @Test
    void constructorWithText_ShouldInitializeText_WhenCalled() {
        assertEquals(text, contentText.getContentText());
    }

    @Test
    void gettersAndSetters_ShouldWorkCorrectly_WhenFilledCorrectly() {
        contentMedia.setMedia(media);
        assertEquals(media, contentMedia.getMedia());

        contentText.setContentText(text);
        assertEquals(text, contentText.getContentText());
    }

    @Test
    void settersWithNullInputs_ShouldThrowException_WhenInputIsNull() {
        assertThrows(RuntimeException.class, () -> contentText.setContentText(null));

        assertThrows(RuntimeException.class, () -> contentMedia.setMedia(null));
    }
}

