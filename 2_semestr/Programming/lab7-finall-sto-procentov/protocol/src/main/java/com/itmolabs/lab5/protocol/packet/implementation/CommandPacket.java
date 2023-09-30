package com.itmolabs.lab5.protocol.packet.implementation;

import com.itmolabs.lab5.auth.AuthUser;
import com.itmolabs.lab5.protocol.handler.ClientHandler;
import com.itmolabs.lab5.protocol.handler.Handler;
import com.itmolabs.lab5.protocol.handler.ServerHandler;
import com.itmolabs.lab5.protocol.packet.Packet;

import java.io.Serializable;

public final class CommandPacket {

    public static class Request extends Packet.Request<Response> implements Serializable {

        protected AuthUser authUser;

        protected String command;

        protected Object[] args;

        public Request(final AuthUser authUser, final String command, final Object... args) {
            this.authUser = authUser;
            this.command = command;

            if (args != null && args.length != 0) {
                for (final Object o : args) {
                    if (!(o instanceof Serializable)) {
                        throw new IllegalArgumentException("Argument " + o + " is not serializable");
                    }
                }

                this.args = args;
            }
        }

        public AuthUser getAuthUser() {
            return authUser;
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
