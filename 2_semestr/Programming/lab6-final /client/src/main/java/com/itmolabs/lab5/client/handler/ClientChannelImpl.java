package com.itmolabs.lab5.client.handler;

import com.itmolabs.lab5.protocol.channel.AbstractClientChannel;
import com.itmolabs.lab5.protocol.handler.ClientHandler;
import com.itmolabs.lab5.protocol.packet.Packet;
import com.itmolabs.lab5.protocol.packet.implementation.CommandPacket;

/**
 * Клиентский слушатель для обработки ответов от сервера.
 *
 * @see ClientHandler
 *
 * Не нужен, ответы получаем пока через awaitPacket
 * В будущем можно будет использовать для получения ответов от сервера
 */
@Deprecated
public final class ClientChannelImpl implements ClientHandler {

    /**
     * Клиентский канал для отправки запросов
     */
    private final AbstractClientChannel client;

    public ClientChannelImpl(final AbstractClientChannel client) {
        this.client = client;

        client.addHandler(this);
    }

    /**
     * Обработка ответа от сервера
     *
     * @param packet - ответ от сервера
     */
    @Override
    public void process(final Packet.Response packet) {
        // Устройство для поддержания веса тела пациента
        // при стоянии и ходьбе (костыль)
        if (packet instanceof CommandPacket.Response) process((CommandPacket.Response) packet);
    }

    @Override
    public void process(final CommandPacket.Response packet) {
        System.out.println(packet.getResponse());
    }
}
