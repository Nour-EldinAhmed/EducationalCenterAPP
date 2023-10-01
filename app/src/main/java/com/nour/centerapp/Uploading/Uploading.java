package com.nour.centerapp.Uploading;

import com.google.firebase.database.Exclude;

public class Uploading {

    private String nameimage;
    private String imageUrl;
    private String mkey;
    @Exclude
    public String getMkey() {
        return mkey;
    }
    @Exclude
    public void setMkey(String mkey) {
        this.mkey = mkey;
    }

    public Uploading() {
    }

    public Uploading(String nameimage, String imageUrl) {

        if(nameimage.trim().equals(""))
        {
            this.nameimage="No Name";
        }


        this.nameimage = nameimage;
        this.imageUrl = imageUrl;
    }


    public String getNameimage() {
        return nameimage;
    }

    public void setNameimage(String nameimage) {
        this.nameimage = nameimage;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
