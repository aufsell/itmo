package com.itmolabs.lab5.protocol.handler;

import com.itmolabs.lab5.protocol.packet.Packet;

import java.net.SocketAddress;

/**
 * Слушатель событий от клиента или сервера (в зависимости от реализации)
 * <p>
 * @see com.itmolabs.lab5.protocol.handler.ClientHandler
 * @see com.itmolabs.lab5.protocol.handler.ServerHandler
 */
public interface Handler {

    default void process(final SocketAddress socketAddress, final Packet.Request<?> packet) {
        // override me
    }

    default void process(final Packet.Response packet) {
        // override me
    }

}
