package com.feriantes4dawin.feriavirtualmovil.data.models;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser{
    public String userId;
    public String displayName;

    public LoggedInUser(String userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
    }

    public LoggedInUser() {
    }
}