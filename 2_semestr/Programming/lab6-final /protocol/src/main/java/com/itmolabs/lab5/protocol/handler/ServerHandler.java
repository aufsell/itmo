package com.itmolabs.lab5.protocol.handler;

import com.itmolabs.lab5.protocol.packet.implementation.CommandPacket;

import java.net.SocketAddress;

/**
 * Слушатель событий от сервера
 * <p>
 * @see com.itmolabs.lab5.protocol.handler.Handler
 */
public interface ServerHandler extends Handler {

    default void process(
            final SocketAddress socketAddress, final CommandPacket.Request packet
    ) {
        // override me
    }
}
