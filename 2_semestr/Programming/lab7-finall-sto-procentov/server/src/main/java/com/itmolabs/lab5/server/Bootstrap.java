package com.itmolabs.lab5.server;

import com.itmolabs.lab5.auth.AuthManager;
import com.itmolabs.lab5.commons.commands.BaseCommand;
import com.itmolabs.lab5.database.Database;
import com.itmolabs.lab5.database.DatabaseCredentials;
import com.itmolabs.lab5.database.impl.CollectionSql;
import com.itmolabs.lab5.database.impl.UsersSql;
import com.itmolabs.lab5.protocol.exception.BindException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Запуск сервера приложения
 * <p>
 *
 * @see com.itmolabs.lab5.server.Server
 */
public final class Bootstrap {

    /**
     * Сервер приложения
     */
    private static Server server;

    /**
     * Запуск сервера приложения
     *
     * @param args аргументы командной строки
     * @throws IOException   - ошибка ввода/вывода
     * @throws BindException - ошибка привязки к порту
     */
    public static void main(String[] args) throws IOException, BindException {
        System.out.println("Starting application...");

        DatabaseCredentials credentials;

        try (BufferedReader reader = new BufferedReader(new FileReader("credentials.txt"))) {
            String line = reader.readLine();

            while (line == null) {
                line = reader.readLine();
            }

            String[] splits = line.split(";");

            credentials = new DatabaseCredentials(splits[0], splits[1], splits[2]);
        }

        final var database = new Database(credentials);

        final var usersSql = new UsersSql(database);
        final var collectionSql = new CollectionSql(database);

        {
            collectionSql.loadTickets();
//            usersSql.loadUsers();

            BaseCommand.setDatabase(collectionSql);
        }

        server = new Server(new AuthManager(usersSql));

        {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                collectionSql.saveTickets();
                database.close();
                System.out.println("Shutting down ...");
            }));
        }

        System.out.println("Application has been started!");

        server.start();
    }

    /**
     * Получение сервера приложения
     *
     * @return сервер приложения
     */
    public static Server getServer() {
        return server;
    }
}
