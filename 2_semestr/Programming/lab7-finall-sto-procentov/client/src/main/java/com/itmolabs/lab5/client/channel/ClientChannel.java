package com.itmolabs.lab5.client.channel;

import com.itmolabs.lab5.protocol.channel.AbstractClientChannel;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.spi.AbstractSelectableChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Клиентский канал для общения с сервером.
 *
 * @see AbstractClientChannel
 */
public class ClientChannel extends AbstractClientChannel {

    public ClientChannel(SocketAddress socketAddress) {
        super(socketAddress);
    }

    @Override
    protected AbstractSelectableChannel bindChannel() throws IOException {
        DatagramChannel channel = DatagramChannel.open();

        channel.bind(null);
        channel.connect(getSocketAddress());

        channel.configureBlocking(false);

        return channel;
    }
}
