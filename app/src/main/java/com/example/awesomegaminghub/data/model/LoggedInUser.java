package com.example.awesomegaminghub.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser{
    private String userId;
    private String displayName;
    private boolean admin;

    public LoggedInUser(String userId, String displayName, Boolean admin) {
        this.userId = userId;
        this.displayName = displayName;
        this.admin = admin;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean getAdmin(){ return admin;}
}