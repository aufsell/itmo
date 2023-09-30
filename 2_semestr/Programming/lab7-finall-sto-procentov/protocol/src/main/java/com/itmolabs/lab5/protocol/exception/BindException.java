package com.itmolabs.lab5.protocol.exception;

import com.itmolabs.lab5.protocol.channel.AbstractChannel;

public class BindException extends Exception {

    public BindException(AbstractChannel channel, Throwable cause) {
        super("Unable to bind server [" + channel.getSocketAddress() + "]", cause);
    }

}
