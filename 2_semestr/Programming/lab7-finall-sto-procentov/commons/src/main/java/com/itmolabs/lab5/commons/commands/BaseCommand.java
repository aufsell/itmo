package com.itmolabs.lab5.commons.commands;

import com.itmolabs.lab5.auth.AuthUser;
import com.itmolabs.lab5.commons.Lazy;
import com.itmolabs.lab5.commons.reader.InputReader;
import com.itmolabs.lab5.database.impl.CollectionSql;
import com.itmolabs.lab5.model.ticket.Ticket;

import java.io.IOException;
import java.io.Serializable;

public abstract class BaseCommand implements ICommand, Serializable {

    private final String name;
    private final String description;

    private String usage;

    public BaseCommand(Class<?> cls) {
        if (!cls.isAnnotationPresent(Command.class)) {
            throw new IllegalStateException("Command must be annotated with @Command");
        }

        Command command = cls.getDeclaredAnnotation(Command.class);

        this.name = command.value();
        this.description = command.description();

        if (command.usage() != null && !command.usage().isEmpty()) {
            this.usage = command.usage();
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUsage() {
        return usage;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public boolean isClient() {
        return Lazy.isInitializedClient();
    }

    private static CollectionSql database;

    public static void setDatabase(CollectionSql database) {
        BaseCommand.database = database;
    }

    public static CollectionSql getDatabase() {
        return database;
    }

    public String sendPacket(final AuthUser authUser, final Object... args) throws IOException {
        return Lazy.getClient().sendCommandAndReceiveResponse(authUser, name, args);
    }

    public Object[] argCheckOfNull(
            final Object... args
    ) {
        if (args.length == 0) return null;

        return args.length < 2 ? new Object[]{args[0], new Object()} : args;
    }

    public Ticket ticket(
            final AuthUser user, final Object... args
    ) {
        if (args.length == 0) return null;

        return args[1] instanceof Ticket
                ? (Ticket) args[1]
                : new InputReader().receiveTicket(Integer.parseInt((String) args[0]), user);
    }
}
