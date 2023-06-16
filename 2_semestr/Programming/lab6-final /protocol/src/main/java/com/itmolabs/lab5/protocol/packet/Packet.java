package com.itmolabs.lab5.protocol.packet;

import com.itmolabs.lab5.protocol.handler.Handler;

import java.io.Serializable;
import java.nio.ByteBuffer;

public abstract class Packet {

    public abstract boolean isHandler(Handler handler);

    public static abstract class Response extends Packet implements Serializable {}

    public static abstract class Request<
            Response extends Packet.Response> extends Packet implements Serializable {

        public abstract Class<?> getResponse();

    }
}
