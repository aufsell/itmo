package com.itmolabs.lab5.protocol.packet.implementation;

import com.itmolabs.lab5.auth.AuthAction;
import com.itmolabs.lab5.auth.AuthUser;
import com.itmolabs.lab5.protocol.handler.ClientHandler;
import com.itmolabs.lab5.protocol.handler.Handler;
import com.itmolabs.lab5.protocol.handler.ServerHandler;
import com.itmolabs.lab5.protocol.packet.Packet;

import java.io.Serializable;

public final class LoginPacket {

    public static class Request extends Packet.Request<Response> implements Serializable {

        private final String user;
        private final String password;

        private final AuthAction action;

        public Request(final String user, final String password, final AuthAction action) {
            this.action = action;
            this.user = user;
            this.password = password;
        }

        public AuthAction getAction() {
            return action;
        }

        public String getUser() {
            return user;
        }

        public String getPassword() {
            return password;
        }

        @Override
        public boolean isHandler(final Handler handler) {
            return handler instanceof ServerHandler;
        }

        @Override
        public Class<LoginPacket.Response> getResponse() {
            return LoginPacket.Response.class;
        }
    }

    public static class Response extends Packet.Response implements Serializable {

        private final AuthUser authUser;
        private final boolean success;

        private final String message;

        public Response(
                final AuthUser user, final boolean success, final String message
        ) {
            this.authUser = user;
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public AuthUser getAuthUser() {
            return authUser;
        }

        public String getResponse() {
            return message;
        }

        @Override
        public boolean isHandler(final Handler handler) {
            return handler instanceof ClientHandler;
        }
    }

}
