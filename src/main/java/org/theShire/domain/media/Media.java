package org.theShire.domain.media;

import org.theShire.domain.exception.MediaException;

import static org.theShire.foundation.DomainAssertion.*;

public class Media {
    //saves the class for exception handling
    private static final Class<MediaException> exType = MediaException.class;
    // saves MetaData as a String
    private String metaData;
    //declares the width of a picture in pixels
    private int width;
    //declares the height of a picture in pixels
    private int height;
    //an alternative description of a picture in case the picture cannot be displayed
    private String altText;
    //defines teh resolution of a picture
    private String resolution;


    public Media(String metaData, int width, int height, String altText, String resolution) {
        setMetaData(metaData);
        setWidth(width);
        setHeight(height);
        setaltText(altText);
        setResolution(resolution);
    }


    //getter and setter-----------------------
    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = isNotBlank(metaData, "metadata", exType);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = greaterZero(width,"width",exType);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = greaterZero(height,"height",exType);
    }

    public String getaltText() {
        return altText;
    }

    public void setaltText(String altText) {
        this.altText = isNotBlank(altText,"altText", exType);
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) { //TODO calc from height and width
        this.resolution = isNotBlank(resolution,"resolution", exType);
    }
}
