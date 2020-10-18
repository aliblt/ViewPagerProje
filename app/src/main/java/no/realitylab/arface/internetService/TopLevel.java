package no.realitylab.arface.internetService;

import java.util.List;

public class TopLevel {
    private String versionNumber;
    private Boolean isUpdated;
    private List<Category> categories;
    private List<CategoryDetails> categoryDetails;


    public String getVersionNumber() {
        return versionNumber;
    }

    public Boolean getUpdated() {
        return isUpdated;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<CategoryDetails> getCategoryDetails() {
        return categoryDetails;
    }
}
