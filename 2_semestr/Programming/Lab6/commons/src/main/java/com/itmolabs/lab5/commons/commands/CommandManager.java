package com.itmolabs.lab5.commons.commands;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Класс, который отвечает за хранение и управление командами
 */
public final class CommandManager {

    /**
     * Map, в котором хранятся все команды
     */
    private static final Map<String, BaseCommand> commands
            = new ConcurrentHashMap<>();

    /**
     * Получить команду по имени
     *
     * @param cls - класс команды
     */
    public static void addCommand(final Class<?> cls) {
        if (!cls.isAnnotationPresent(Command.class)) {
            throw new IllegalStateException("Command must be annotated with @Command");
        }

        Command command = cls.getDeclaredAnnotation(Command.class);
        String commandName = command.value();

        if (hasCommand(commandName)) {
            throw new IllegalStateException("Command with name " + commandName + " already exists");
        }

        if (!BaseCommand.class.isAssignableFrom(cls)) {
            throw new IllegalStateException("Command must extend BaseCommand");
        }

        try {
            commands.put(commandName, (BaseCommand) cls.newInstance());
        } catch (final InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("Cannot instantiate command " + commandName, e);
        }
    }

    /**
     * Добавить команду
     *
     * @param instance - экземпляр команды
     */
    public static void addCommand(final Object instance) {
        addCommand(instance.getClass());
    }

    /**
     * Удалить команду
     *
     * @param commandName - имя команды
     */
    public static void removeCommand(final String commandName) {
        commands.remove(commandName);
    }

    /**
     * Удалить команду
     *
     * @param cls - класс команды
     */
    public static void removeCommand(final Class<?> cls) {
        removeCommand(cls.getDeclaredAnnotation(Command.class).value());
    }

    /**
     * Получить команду по имени
     *
     * @param commandName - имя команды
     * @return команда
     */
    public static ICommand getCommand(final String commandName) {
        return commands.get(commandName);
    }

    /**
     * Проверить, есть ли команда с таким именем
     *
     * @param commandName - имя команды
     * @return true, если есть, иначе false
     */
    public static boolean hasCommand(final String commandName) {
        return commands.containsKey(commandName);
    }

    /**
     * Добавить команды
     *
     * @param instances
     */
    public static void addCommands(final Object... instances) {
        for (final Object instance : instances) {
            addCommand(instance);
        }
    }

    /**
     * Добавить команды
     *
     * @param classes - классы команд (массив)
     */
    public static void addCommands(final Class<?>... classes) {
        for (final Class<?> cls : classes) {
            addCommand(cls);
        }
    }

    /**
     * Удалить команды
     *
     * @param commandNames - имена команд (массив)
     */
    public static void removeCommands(final String... commandNames) {
        for (final String commandName : commandNames) {
            removeCommand(commandName);
        }
    }

    /**
     * Удалить команды
     *
     * @param classes - классы команд (массив)
     */
    public static void removeCommands(final Class<?>... classes) {
        for (final Class<?> cls : classes) {
            removeCommand(cls.getDeclaredAnnotation(Command.class).value());
        }
    }

    /**
     * Удалить команды
     *
     * @param instances - экземпляры команд (массив)
     */
    public static void removeCommands(final Object... instances) {
        for (final Object instance : instances) {
            removeCommand(instance.getClass().getDeclaredAnnotation(Command.class).value());
        }
    }

    /**
     * Удалить команды
     *
     * @param commands - команды (массив)
     */
    public static void removeCommands(final ICommand... commands) {
        for (final ICommand command : commands) {
            removeCommand(command.getClass().getDeclaredAnnotation(Command.class).value());
        }
    }

    /**
     * Получить все команды
     *
     * @return все команды
     */
    public static Map<String, BaseCommand> getCommands() {
        return commands;
    }
}
