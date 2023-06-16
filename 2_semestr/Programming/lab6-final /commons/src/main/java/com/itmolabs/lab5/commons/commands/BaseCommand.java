package com.itmolabs.lab5.commons.commands;

import com.itmolabs.lab5.commons.Lazy;
import com.itmolabs.lab5.commons.managers.CollectionManager;
import com.itmolabs.lab5.model.ticket.Ticket;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

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

    /**
     * Менеджер коллекции
     */
    private static CollectionManager collectionManager;

    /**
     * Установить менеджер коллекции
     *
     * @param collectionManager - менеджер коллекции
     */
    public static void setCollectionManager(
            final CollectionManager collectionManager
    ) {
        BaseCommand.collectionManager = collectionManager;
    }

    /**
     * Получить менеджер коллекции
     *
     * @return менеджер коллекции
     */
    public static CollectionManager getCollectionManager() {
        return collectionManager;
    }

    /**
     * Получить коллекцию
     *
     * @return коллекция
     */
    public static Map<Integer, Ticket> getCollection() {
        return collectionManager.getCollection();
    }

    public boolean isClient() {
        return Lazy.isInitializedClient();
    }

    public String sendPacket(final Object... args) throws IOException {
        return Lazy.getClient().sendCommandAndReceiveResponse(name, args);
    }
}
