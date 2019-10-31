package com.example.photosahreapp;

import android.content.Intent;

public class Categories {

    String imagetitle;
    int image;

    public Categories(String imagetitle, int image) {
        this.imagetitle = imagetitle;
        this.image = image;
    }

    public String getImagetitle() {
        return imagetitle;
    }

    public void setImagetitle(String imagetitle) {
        this.imagetitle = imagetitle;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }


}
