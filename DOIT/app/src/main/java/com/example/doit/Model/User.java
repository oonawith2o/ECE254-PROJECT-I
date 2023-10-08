package com.example.doit.Model;

/**
 * LIBRARY IMPORT
 */
import java.util.UUID;

/**
 * USER CLASS
 */
public class User {

    /**
     * ATTRIBUTES
     */
    private UUID userID;
    private String username;
    private String email;
    private String password;

    /**
     * CONSTRUCTORS
     */
    public User() {
        this.userID = UUID.randomUUID();
    }

    public User(int userID, String username, String email, String password) {
        this.userID = UUID.randomUUID();
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * GET METHODS
     */
    public UUID getUserID() { return userID; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    /**
     * SET METHODS
     */
    private void setUserID(UUID userID) { this.userID = userID; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }

}
