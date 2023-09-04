package com.example.cncdcattleedcandroid.OfflineDb.Models;

import android.graphics.Bitmap;

import java.io.File;

public class ImageInfo {


    private Bitmap imagebmp;
    private String imageName;
    private String imagePath;



    public ImageInfo(String imageName, String imagePath, Bitmap imagebmp) {
        this.imageName = imageName;
        this.imagePath = imagePath;
        this.imagebmp = imagebmp;
    }



    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Bitmap getImagebmp() {
        return imagebmp;
    }

    public void setImagebmp(Bitmap imagebmp) {
        this.imagebmp = imagebmp;
    }



    public ImageInfo(String imageName, String imagePath) {
        this.imageName = imageName;
        this.imagePath = imagePath;
    }

    public String getImageName() {
        return imageName;
    }

    public String getImagePath() {
        return imagePath;
    }
}
