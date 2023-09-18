package com.itmolabs.lab5.server.handler;

import com.itmolabs.lab5.commons.commands.CommandManager;
import com.itmolabs.lab5.commons.commands.ICommand;
import com.itmolabs.lab5.protocol.channel.AbstractServerChannel;
import com.itmolabs.lab5.protocol.handler.ServerHandler;
import com.itmolabs.lab5.protocol.packet.Packet;
import com.itmolabs.lab5.protocol.packet.implementation.CommandPacket;
import com.itmolabs.lab5.protocol.utils.SerializeUtils;

import java.io.IOException;
import java.net.SocketAddress;

/**
 * Обработчик запросов от клиентов на сервере
 * <p>
 * @see com.itmolabs.lab5.protocol.handler.ServerHandler
 */
public final class ServerHandlerImpl implements ServerHandler {

    /**
     * Серверный канал для отправки ответов клиентам
     */
    private final AbstractServerChannel channel;

    public ServerHandlerImpl(
            final AbstractServerChannel channel
    ) {
        this.channel = channel;

        channel.addHandler(this);
    }

    /**
     * Обработка запроса от клиента
     *
     * @param socketAddress - адрес клиента
     * @param packet        - запрос клиента
     */
    @Override
    public void process(
            final SocketAddress socketAddress,
            final Packet.Request<?> packet
    ) {
        // Устройство для поддержания веса тела пациента
        // при стоянии и ходьбе (костыль)
        if (packet instanceof CommandPacket.Request) process(socketAddress, (CommandPacket.Request) packet);
    }

    @Override
    public void process(
            final SocketAddress socketAddress,
            final CommandPacket.Request packet
    ) {
        ICommand command;

        if ((command = CommandManager.getCommand(packet.getCommand().toLowerCase())) == null) {
            System.out.println(packet.getCommand());

            sendResponse(socketAddress, new CommandPacket.Response("Unknown command"));
            return;
        }

        try {
            sendResponse(socketAddress, new CommandPacket.Response(
                    command.execute(packet.getArgs())
            ));
        } catch (final IOException e) {
            channel.disconnectFromClient();

            e.printStackTrace();
        }
    }

    /**
     * Отправка ответа клиенту
     *
     * @param socketAddress - адрес клиента
     * @param response      - ответ клиенту
     */
    public void sendResponse(
            final SocketAddress socketAddress,
            final CommandPacket.Response response
    ) {
        byte[] bytes;

        try {
            // Сериализация ответа
            bytes = SerializeUtils.serialize(response);
        } catch (final IOException e) {
            // Не сработало, пошел клиент нахер
            channel.disconnectFromClient();
            return;
        }

        try {
            // Отправка ответа клиенту
            channel.sendData(socketAddress, bytes);
        } catch (final IOException e) {
            System.out.println("Failed to send response to " + socketAddress);
            channel.disconnectFromClient();
        }
    }
}
