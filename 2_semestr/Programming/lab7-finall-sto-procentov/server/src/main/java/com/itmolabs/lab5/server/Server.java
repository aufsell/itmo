package com.itmolabs.lab5.server;

import com.itmolabs.lab5.auth.AuthManager;
import com.itmolabs.lab5.commons.commands.CommandInvoker;
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

    private final AuthManager authManager;

    public Server(
            final AuthManager authManager
    ) {
        this.authManager = authManager;

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
                authManager, InetAddress.getLocalHost(), 23586);

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
