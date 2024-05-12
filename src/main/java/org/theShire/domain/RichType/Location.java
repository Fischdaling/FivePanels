package org.theShire.domain.RichType;

public class Location {
    private String location;

    public Location(String location) {
        setLocation(location);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        //TODO ASSERTION
        this.location = location;
    }
}
