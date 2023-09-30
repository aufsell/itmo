package com.itmolabs.lab5.client;

import com.itmolabs.lab5.client.channel.ClientChannel;
import com.itmolabs.lab5.protocol.channel.AbstractClientChannel;

import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * Класс для запуска клиента и подключения к серверу.
 */
public final class Client {

    private static AbstractClientChannel channel;

    public void connect() {
        try {
            System.out.println("Lab work #6 implementation has been started!");

            channel = new ClientChannel(new InetSocketAddress(InetAddress.getLocalHost(), 23586));
            channel.connect(() -> System.out.println("Connected to server!"));
        } catch (final Exception ignored) {
            System.err.println("Failed to connect to server!");
        }
    }

    public AbstractClientChannel getChannel() {
        return channel;
    }

    public static void close() {
        channel.close();
    }
}
