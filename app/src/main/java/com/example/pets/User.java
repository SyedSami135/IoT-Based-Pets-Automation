package com.example.pets;

import android.net.Uri;

public class User {

    public String getName() {
        return name;
    }



    public String getEmail() {
        return email;
    }


    public String getContact(){
        return contact;
    }
    private String name;
    private String email;
    private String contact;
    private Uri imageUri;

public User(){

}
public User(String name, String email , String contact , Uri imageUri){
    this.email=email;
    this.name=name;
    this.contact=contact;
    this.imageUri=imageUri;

}

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }
}
