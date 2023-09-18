package com.itmolabs.lab5.server.channel;

import com.itmolabs.lab5.protocol.channel.AbstractServerChannel;
import com.itmolabs.lab5.server.handler.ServerHandlerImpl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.spi.AbstractSelectableChannel;

/**
 * Канал для сервера
 * <p>
 * @see com.itmolabs.lab5.protocol.channel.AbstractServerChannel
 */
public class ServerChannel extends AbstractServerChannel {

    public ServerChannel(final InetAddress host, final int port) {
        super(host, port);

        // создаем обработчик запросов от клиентов
        // само создание канала происходит в родительском классе (AbstractChannel)
        new ServerHandlerImpl(this);
    }

    /**
     * Создание канала для сервера
     *
     * @return канал для сервера
     * @throws IOException - ошибка создания канала
     */
    @Override
    protected AbstractSelectableChannel bindChannel() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        // устанавливаем неблокирующий режим
        serverSocketChannel.configureBlocking(false);

        // устанавливаем опции

        // сокет будет переиспользоваться
        serverSocketChannel.socket().setReuseAddress(true);
        // биндим сокет
        serverSocketChannel.socket().bind(getSocketAddress());

        // устанавливаем опции
        serverSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);

        return serverSocketChannel;
    }
}