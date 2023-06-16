package com.itmolabs.lab5.protocol.packet.implementation;

import com.itmolabs.lab5.protocol.handler.ClientHandler;
import com.itmolabs.lab5.protocol.handler.Handler;
import com.itmolabs.lab5.protocol.handler.ServerHandler;
import com.itmolabs.lab5.protocol.packet.Packet;

import java.io.Serializable;

public final class CommandPacket {

    public static class Request extends Packet.Request<Response> implements Serializable {

        protected String command;

        protected Object[] args;

        public Request(final String command, final Object... args) {
            this.command = command;

            if (args != null) {
                for (final Object o : args) {
                    if (!(o instanceof Serializable)) {
                        throw new IllegalArgumentException("Argument " + o + " is not serializable");
                    }

                    this.args = args;
                }
            }
        }

        public String getCommand() {
            return command;
        }

        public Object[] getArgs() {
            return args;
        }

        @Override
        public boolean isHandler(final Handler handler) {
            return handler instanceof ServerHandler;
        }

        @Override
        public Class<CommandPacket.Response> getResponse() {
            return CommandPacket.Response.class;
        }
    }

    public static class Response extends Packet.Response implements Serializable {

        private final String response;

        public Response(final String response) {
            this.response = response;
        }

        public String getResponse() {
            return response;
        }

        @Override
        public boolean isHandler(final Handler handler) {
            return handler instanceof ClientHandler;
        }
    }
}
