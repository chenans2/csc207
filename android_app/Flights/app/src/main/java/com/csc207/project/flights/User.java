package com.csc207.project.flights;

import java.io.Serializable;

public abstract class User implements Serializable {

    private String email;

    /**
     * Constructs a new User object from the String userStr.
     *
     */
    public User (String email) {
        this.email = email;
    }

    /**
     * Gets this User's email.
     *
     * @return this User's email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets this User's email.
     *
     * @return this User's email
     */
    public void setEmail(String email) {
        this.email = email;
    }

}
