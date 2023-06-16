package com.itmolabs.lab5.protocol.handler;

import com.itmolabs.lab5.protocol.packet.implementation.CommandPacket;

/**
 * Слушатель событий от клиента
 * <p>
 * @see com.itmolabs.lab5.protocol.handler.Handler
 */
public interface ClientHandler extends Handler {

    default void process(
            final CommandPacket.Response packet
    ) {
        // override me
    }

}
