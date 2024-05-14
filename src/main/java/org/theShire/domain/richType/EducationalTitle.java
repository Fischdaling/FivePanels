package org.theShire.domain.richType;

import java.util.List;

public class EducationalTitle {
    private List<String> educationalTitle;

    public EducationalTitle(String educationalTitle) {
        setEducationalTitle(educationalTitle);
    }

    public String getEducationalTitle() {
        return educationalTitle;
    }

    public void addEducationalTitle(String educationalTitle) {
        this.educationalTitle.add(educationalTitle);
    }


}
