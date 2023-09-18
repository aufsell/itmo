package com.itmolabs.lab5.protocol.exception;

public class DecoderException extends Exception {

    public DecoderException(final String message) {
        super("Unable to decode data packet: " + message);
    }

    public DecoderException(final Throwable cause) {
        this(cause.getMessage());
    }
}
