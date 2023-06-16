package com.itmolabs.lab5.server;

import com.itmolabs.lab5.commons.commands.BaseCommand;
import com.itmolabs.lab5.commons.commands.CommandInvoker;
import com.itmolabs.lab5.commons.managers.CollectionManager;
import com.itmolabs.lab5.protocol.exception.BindException;
import com.itmolabs.lab5.server.channel.ServerChannel;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Запуск сервера приложения
 */
public final class Server {

    /**
     * Серверный канал для отправки ответов клиентам
     */
    private static ServerChannel channel;

    public Server() {
        CollectionManager collectionManager;

        try {
            collectionManager = new CollectionManager("/Users/aroslavlevcenko/Desktop/lab6-final /collection.xml");
        } catch (final Exception e) {
            System.out.println("Can't load collection from file! (File not found)");
            System.exit(0);

            return;
        }

        // Установка менеджера коллекции для команд
        BaseCommand.setCollectionManager(collectionManager);

        new CommandInvoker();
    }

    /**
     * Запуск сервера приложения
     *
     * @throws IOException   - ошибка ввода/вывода
     * @throws BindException - ошибка привязки к порту
     */
    public void start() throws IOException, BindException {
        channel = new ServerChannel(
                InetAddress.getLocalHost(), 2358
        );

        channel.start();
    }

    /**
     * Получение серверного канала для отправки ответов клиентам
     *
     * @return серверный канал для отправки ответов клиентам
     */
    public ServerChannel getChannel() {
        return channel;
    }

    public static void stop() throws IOException {
        channel.close();
    }
}
