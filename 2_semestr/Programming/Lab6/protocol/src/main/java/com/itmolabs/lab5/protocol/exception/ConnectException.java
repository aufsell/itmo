package com.itmolabs.lab5.protocol.exception;

import com.itmolabs.lab5.protocol.channel.AbstractChannel;

public class ConnectException extends Exception {

    public ConnectException(AbstractChannel channel, Throwable cause) {
        super("Unable to connect to server [" + channel.getSocketAddress() + "]", cause);
    }
}
