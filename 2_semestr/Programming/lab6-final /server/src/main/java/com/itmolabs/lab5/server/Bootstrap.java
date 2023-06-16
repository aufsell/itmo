package com.itmolabs.lab5.server;

import com.itmolabs.lab5.protocol.exception.BindException;

import java.io.IOException;

/**
 * Запуск сервера приложения
 * <p>
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
     *
     * @throws IOException            - ошибка ввода/вывода
     * @throws BindException          - ошибка привязки к порту
     */
    public static void main(String[] args) throws IOException, BindException {
        System.out.println("Starting application...");

        server = new Server();
        server.start();

        System.out.println("Application has been started!");
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
