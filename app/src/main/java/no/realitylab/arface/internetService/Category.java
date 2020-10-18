package no.realitylab.arface.internetService;

import android.graphics.Bitmap;

import java.util.List;

public class Category {
    private int id;
    private String name;
    private String order;
    private String image;
    private Bitmap imageBitmap;
    private List<CategoryDetails> categoryDetails;


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOrder() {
        return order;
    }

    public String getImage() {
        return image;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public List<CategoryDetails> getCategoryDetails() {
        return categoryDetails;
    }
}
