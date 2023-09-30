package com.itmolabs.lab5.auth;

import java.io.Serializable;

public class AuthUser implements Serializable {

    private final int id;
    private final String user;
    private final String password;

    public AuthUser(
            final int id, final String user, final String password
    ) {
        this.id = id;
        this.user = user;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
