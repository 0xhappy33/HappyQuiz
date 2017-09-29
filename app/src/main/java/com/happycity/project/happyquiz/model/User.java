package com.happycity.project.happyquiz.model;

/**
 * Created by Ha Truong on 9/29/2017.
 * This is a HappyQuiz
 * into the com.happycity.project.happyquiz.model
 */

public class User {
    private String userName;
    private String passWord;
    private String email;

    public User() {
    }

    public User(String userName, String passWord, String email) {
        this.userName = userName;
        this.passWord = passWord;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassWord() {
        return passWord;
    }

    public User setPassWord(String passWord) {
        this.passWord = passWord;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }
}
