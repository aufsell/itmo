package com.itmolabs.lab5.server.handler;

import com.itmolabs.lab5.auth.AuthAction;
import com.itmolabs.lab5.auth.AuthManager;
import com.itmolabs.lab5.auth.AuthUser;
import com.itmolabs.lab5.commons.commands.CommandManager;
import com.itmolabs.lab5.commons.commands.ICommand;
import com.itmolabs.lab5.protocol.channel.AbstractServerChannel;
import com.itmolabs.lab5.protocol.handler.ServerHandler;
import com.itmolabs.lab5.protocol.packet.Packet;
import com.itmolabs.lab5.protocol.packet.implementation.CommandPacket;
import com.itmolabs.lab5.protocol.packet.implementation.LoginPacket;
import com.itmolabs.lab5.protocol.utils.SerializeUtils;

import java.io.IOException;
import java.net.SocketAddress;

/**
 * Обработчик запросов от клиентов на сервере
 * <p>
 *
 * @see com.itmolabs.lab5.protocol.handler.ServerHandler
 */
public final class ServerHandlerImpl implements ServerHandler {

    /**
     * Серверный канал для отправки ответов клиентам
     */
    private final AbstractServerChannel channel;

    /**
     * Менеджер авторизации
     */
    private final AuthManager authManager;

    public ServerHandlerImpl(
            final AbstractServerChannel channel,
            final AuthManager authManager
    ) {
        this.authManager = authManager;
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
        // Устройство для поддержания веса тела пациента при стоянии и ходьбе
        if (packet instanceof CommandPacket.Request)
            process(socketAddress, (CommandPacket.Request) packet);

        if (packet instanceof LoginPacket.Request)
            process(socketAddress, (LoginPacket.Request) packet);
    }

    @Override
    public void process(SocketAddress socketAddress, LoginPacket.Request packet) {
        final var user = packet.getUser();

        if (user.length() < 3 || user.length() > 16) {
            sendLoginResponse(socketAddress, new LoginPacket.Response(null, false,"Username must be between 3 and 16 characters long"));
            return;
        }

        final var password = packet.getPassword();

        if (password.length() < 4 || password.length() > 32) {
            sendLoginResponse(socketAddress, new LoginPacket.Response(null, false, "Password must be between 3 and 16 characters long"));
            return;
        }

        final var authUser = authManager.getUserOrLoad(user)
                .orElse(null);

        if (authUser == null && packet.getAction() == AuthAction.LOGIN) {
            sendLoginResponse(socketAddress, new LoginPacket.Response(null, false, "User not found"));
            return;
        }

        if (authUser != null && packet.getAction() == AuthAction.REGISTER) {
            sendLoginResponse(socketAddress, new LoginPacket.Response(null, false,"User already exists"));
            return;
        }

        if (authUser != null && packet.getAction() == AuthAction.LOGIN) {
            if (!authManager.getHasher().check(password, authUser.getPassword())) {
                sendLoginResponse(socketAddress, new LoginPacket.Response(null, false,"Wrong password"));
                return;
            }

            sendLoginResponse(socketAddress, new LoginPacket.Response(authUser, true,"Successfully logged in"));
            return;
        }

        if (authUser == null && packet.getAction() == AuthAction.REGISTER) {
            sendLoginResponse(socketAddress, new LoginPacket.Response(
                    authManager.registerUser(user, password), true, "Successfully registered"));
        }
    }

    @Override
    public void process(
            final SocketAddress socketAddress,
            final CommandPacket.Request packet
    ) {
        ICommand command;

        if ((command = CommandManager.getCommand(packet.getCommand().toLowerCase())) == null) {
            System.out.println(packet.getCommand());

            sendCommandResponse(socketAddress, new CommandPacket.Response("Unknown command"));
            return;
        }

        try {
            sendCommandResponse(socketAddress, new CommandPacket.Response(
                    command.execute(packet.getAuthUser(), packet.getArgs())
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
    public void sendCommandResponse(
            final SocketAddress socketAddress,
            final CommandPacket.Response response
    ) {
        byte[] bytes;

        try {
            // Сериализация ответа
            bytes = SerializeUtils.serialize(response);
        } catch (final IOException e) {
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

    public void sendLoginResponse(
            final SocketAddress socketAddress,
            final LoginPacket.Response response
    ) {
        byte[] bytes;

        try {
            // Сериализация ответа
            bytes = SerializeUtils.serialize(response);
        } catch (final IOException e) {
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
