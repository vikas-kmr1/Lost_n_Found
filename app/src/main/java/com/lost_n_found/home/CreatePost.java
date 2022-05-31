package com.lost_n_found.home;

public class CreatePost {
    public String status;
    public String title;
    public String description;
    public String location;
    public String date;
    public String contact;
    public String postImgUrl ;
    public String uid ;

    public String getPostImgUrl() {
        return postImgUrl;
    }

    public String getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public String username ;


    public String getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public String getContact() {
        return contact;
    }

    public CreatePost(String status, String title, String description, String location, String date, String contact ,String postImgUrl,String uid, String username) {
        this.status = status;
        this.title = title;
        this.description = description;
        this.location = location;
        this.date = date;
        this.contact = contact;
        this.postImgUrl = postImgUrl;
        this.uid = uid;
        this.username = username;
    }

    public CreatePost() {

    }

}
