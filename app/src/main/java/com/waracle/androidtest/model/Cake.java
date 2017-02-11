package com.waracle.androidtest.model;

import android.widget.ImageView;

/**
 * Created by Ruairi on 10/02/2017.
 */

public class Cake {

    private String title, desc;
    private String image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Cake{" +
                "title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", image=" + image +
                '}';
    }
}
