package no.realitylab.arface;

import android.graphics.Bitmap;

public class Model {

    private Bitmap bitmap;
    private String title;

    public Model(Bitmap bitmap) {
        this.bitmap = bitmap;
        this.title = title;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
