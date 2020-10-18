package no.realitylab.arface.internetService;

import android.graphics.Bitmap;

public class CategoryDetails {

    private  int id;
    private  int order;
    private String image;
    private Bitmap imageBitmap;

    //private String token;


    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public int getId() {
        return id;
    }

    public int getOrder() {
        return order;
    }

    public String getImage() {
        return image;
    }


}