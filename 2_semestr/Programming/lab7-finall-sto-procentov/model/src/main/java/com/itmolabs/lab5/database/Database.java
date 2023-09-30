package com.itmolabs.lab5.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class Database {

    private Connection connection;

    public Database(
            DatabaseCredentials credentials
    ) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            this.connection = DriverManager.getConnection(
                    credentials.getUrl(), credentials.getUsername(), credentials.getPassword());
        } catch (final SQLException e) {
            e.printStackTrace();

            return;
        }

        System.out.println("Successfully connected to database!");
    }

    public Connection getConnection() {
        return connection;
    }

    public CompletableFuture<Void> execute(
            String query, Object... params
    ) {
        return CompletableFuture.runAsync(() -> {
            try (final var statement = connection.prepareStatement(query)) {
                if (params.length > 0) {
                    for (int i = 0; i < params.length; i++) {
                        statement.setObject(i + 1, params[i]);
                    }
                }

                statement.execute();
            } catch (final Exception e) {
                throw new RuntimeException("Failed to execute query!", e);
            }
        });
    }

    public CompletableFuture<Void> executeQuery(
            String query, Consumer<ResultSet> resultSetConsumer, Object... params
    ) {
        return CompletableFuture.runAsync(() -> {
            try (final var statement = connection.prepareStatement(query)) {
                if (params.length > 0) {
                    for (int i = 0; i < params.length; i++) {
                        statement.setObject(i + 1, params[i]);
                    }
                }

                final var resultSet = statement.executeQuery();

                resultSetConsumer.accept(resultSet);
            } catch (final Exception e) {
                throw new RuntimeException("Failed to execute query!", e);
            }
        });
    }

    public void close() {
        try {
            connection.close();
        } catch (final Exception e) {
            throw new RuntimeException("Failed to close connection!", e);
        }
    }
}
