package com.itmolabs.lab5.commons.commands;

import com.itmolabs.lab5.auth.AuthUser;

import java.io.IOException;

/**
 * <br>Интерфейс для команды
 * <br>Все команды должны реализовывать этот интерфейс
 * <br>Все команды должны быть помечены аннотацией {@link Command}
 *
 * @see Command
 */
public interface ICommand {

    /**
     * Имя команды
     *
     * @return имя команды
     */
    String getName();

    /**
     * Использование команды
     *
     * @return использование команды
     */
    String getUsage();

    /**
     * Описание команды
     *
     * @return описание команды
     */
    String getDescription();

    /**
     * Выполнить команду
     *
     * @param args - аргументы команды
     *
     * @return результат выполнения команды
     * @throws IOException - ошибка ввода/вывода
     */
    String execute(final AuthUser authUser, final Object[] args) throws IOException;

    default String execute(final AuthUser authUser) throws IOException {
        return execute(authUser, new Object[0]);
    }
}
