package com.happycity.project.happyquiz.model;

/**
 * Created by Ha Truong on 9/29/2017.
 * This is a HappyQuiz
 * into the com.happycity.project.happyquiz.model
 */

public class Category {
    private String name;
    private String image;

    public Category() {
    }

    public Category(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public Category setName(String name) {
        this.name = name;
        return this;
    }

    public String getImage() {
        return image;
    }

    public Category setImage(String image) {
        this.image = image;
        return this;
    }
}
