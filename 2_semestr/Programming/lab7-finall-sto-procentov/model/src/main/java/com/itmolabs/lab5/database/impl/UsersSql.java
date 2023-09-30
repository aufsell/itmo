package com.itmolabs.lab5.database.impl;

import com.itmolabs.lab5.auth.AuthUser;
import com.itmolabs.lab5.auth.hash.Hasher;
import com.itmolabs.lab5.auth.hash.MD2PasswordHasher;
import com.itmolabs.lab5.database.Database;

import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public final class UsersSql {

    private final Hasher hasher;
    private final Database database;

    public UsersSql(
            final Database database
    ) {
        this.hasher = new MD2PasswordHasher();
        this.hasher.init();

        this.database = database;

        createTable();
    }

    public Hasher getHasher() {
        return hasher;
    }

    private static final String CREATE_USERS_SEQUENCE
            = "CREATE SEQUENCE IF NOT EXISTS users_id_seq START 1 INCREMENT 1;";

    private static final String SELECT_USERS_SEQUENCE
            = "SELECT nextval('users_id_seq');";

    private static final String CREATE_USERS_TABLE
            = "CREATE TABLE IF NOT EXISTS Users(" +
            "id BIGSERIAL PRIMARY KEY, user_name VARCHAR(128) NOT NULL UNIQUE, password VARCHAR(128) NOT NULL);";

    private static final String INSERT_USER
            = "INSERT INTO Users (id, user_name, password) VALUES (?, ?, ?);";

    public void createTable() {
        database.execute(CREATE_USERS_TABLE)
                .thenApply(v -> {
                    System.out.println("Successfully created table Users!");

                    return v;
                }).exceptionally(e -> {
                    e.printStackTrace();
                    return null;
                });

        database.execute(CREATE_USERS_SEQUENCE)
                .thenApply(v -> {
                    System.out.println("Successfully created sequence users_id_seq!");

                    return v;
                }).exceptionally(e -> {
                    e.printStackTrace();
                    return null;
                });
    }

    public AuthUser registerUser(
            final String user, final String password
    ) {
        int i = getNextId();

        return database.execute(INSERT_USER, i, user, hasher.hash(password))
                .thenApply(v -> new AuthUser(i, user, password))
                .join();
    }

    public int getNextId() {
        return CompletableFuture.supplyAsync(() -> {
            try (final var statement = database.getConnection().prepareStatement(
                    SELECT_USERS_SEQUENCE
            )) {
                final var resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    return resultSet.getInt(1) - 1; // Из-за ограничений PostGRE ( min value sequence = 1)
                }
            } catch (final Exception e) {
                throw new RuntimeException("Failed to get user!", e);
            }

            return null;
        }).join();
    }

    public CompletableFuture<AuthUser> getUser(
            final int id
    ) {
        return CompletableFuture.supplyAsync(() -> {
            try (final var statement = database.getConnection().prepareStatement(
                    "SELECT * FROM Users WHERE uuid = ?"
            )) {
                statement.setInt(1, id);

                final var resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    return new AuthUser(
                            resultSet.getInt("id"),
                            resultSet.getString("user_name"),
                            resultSet.getString("password")
                    );
                }
            } catch (final Exception e) {
                throw new RuntimeException("Failed to get user!", e);
            }

            return null;
        });
    }

    public CompletableFuture<AuthUser> getUser(
            final String user
    ) {

        return CompletableFuture.supplyAsync(() -> {
            try (final var statement = database.getConnection().prepareStatement(
                    "SELECT * FROM Users WHERE user_name = ?"
            )) {
                statement.setString(1, user);

                final var resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    return new AuthUser(
                            resultSet.getInt("id"),
                            resultSet.getString("user_name"),
                            resultSet.getString("password")
                    );
                }
            } catch (final Exception e) {
                throw new RuntimeException("Failed to get user!", e);
            }

            return null;
        });
    }

}
