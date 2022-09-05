package com.example.contactloadingrecyclerview;

import android.net.Uri;

public class ContactModel {

    String name,number,image;
    Uri imageuri;

    public ContactModel(String name, String number, String image) {
        this.name = name;
        this.number = number;
        this.image = image;
        this.imageuri = imageuri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Uri getImageuri() {
        return imageuri;
    }

    public void setImageuri(Uri imageuri) {
        this.imageuri = imageuri;
    }
}
