package com.itmolabs.lab5.auth;

import com.itmolabs.lab5.auth.hash.Hasher;
import com.itmolabs.lab5.database.impl.UsersSql;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public final class AuthManager {

    private final Hasher hasher;

    private final Map<Integer, AuthUser> users;

    private final UsersSql database;

    //инициализация менеджера авторизации
    public AuthManager(
            final UsersSql usersSql
    ) {
        this.hasher = usersSql.getHasher();
        this.users = new HashMap<>();
        this.database = usersSql;

        System.out.println("AuthManager has been initialized!");
    }

    public Optional<AuthUser> getUser(
            final int id
    ) {
        return Optional.ofNullable(users.get(id));
    }

    public Optional<AuthUser> getUserOrLoad(
            final int id
    ) {
        return Optional.ofNullable(users.get(id))
                .or(() -> {
                    AuthUser user = database.getUser(id)
                            .join();

                    if (user != null) users.put(id, user);
                    return Optional.ofNullable(user);
                });
    }

    public Optional<AuthUser> getUserOrLoad(String username) {
        return Optional.ofNullable(CompletableFuture.supplyAsync(() -> {
            for (AuthUser user : users.values())
                if (user.getUser().equals(username))
                    return user;

            return database.getUser(username)
                    .thenApply(user -> {
                        if (user != null)
                            users.put(user.getId(), user);
                        else return null;

                        return user;
                    }).join();
        }).join());
    }

    //регистрация юзера
    public AuthUser registerUser(
            final String user, final String password
    ) {
        System.out.println("Successfully registered user! (User: " + user + ")");

        return database.registerUser(user, password);
    }

    public Hasher getHasher() {
        return hasher;
    }
}
