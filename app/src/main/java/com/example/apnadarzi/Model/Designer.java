package com.example.apnadarzi.Model;

public class Designer {

    private String d_name;
    private float d_rating;
    private String d_image;


    public Designer(){

    }
    public Designer(String d_image,String d_name,float d_rating){
        this.d_image=d_image;
        this.d_name=d_name;
        this.d_rating=d_rating;

    }

    public String getD_name() {
        return d_name;
    }

    public void setD_name(String d_name) {
        this.d_name = d_name;
    }

    public float getD_rating() {
        return d_rating;
    }

    public void setD_rating(float d_rating) {
        this.d_rating = d_rating;
    }

    public String getD_image() {
        return d_image;
    }

    public void setD_image(String d_image) {
        this.d_image = d_image;
    }
}
