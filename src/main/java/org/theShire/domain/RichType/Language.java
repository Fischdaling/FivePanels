package org.theShire.domain.RichType;

public class Language {
    private String language;

    public Language(String language) {
       setLanguage(language);
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        //TODO ASSERTION
        this.language = language;
    }

}
