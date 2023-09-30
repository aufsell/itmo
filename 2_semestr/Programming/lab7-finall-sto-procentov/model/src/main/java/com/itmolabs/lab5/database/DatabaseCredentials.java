package com.itmolabs.lab5.database;


// класс с данными для подключения к бд
public class DatabaseCredentials {

    private final String url;
    private final String username;
    private final String password;

    public DatabaseCredentials(
            String url,
            String username,
            String password
    ) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
