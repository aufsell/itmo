package com.itmolabs.lab5.commons;

import com.itmolabs.lab5.protocol.channel.AbstractClientChannel;

/**
 * Инициализация клиента и получение его инстанса в любом месте приложения.
 * <p>
 * Использование метода Lazy
 */
public final class Lazy {

    private static AbstractClientChannel channel;

    public static AbstractClientChannel setClient(final AbstractClientChannel channel) {
        if (Lazy.channel != null) {
            return Lazy.channel;
        }

        Lazy.channel = channel;

        return channel;
    }

    public static AbstractClientChannel getClient() {
        return channel;
    }

    public static boolean isInitializedClient() {
        return channel != null;
    }
}
