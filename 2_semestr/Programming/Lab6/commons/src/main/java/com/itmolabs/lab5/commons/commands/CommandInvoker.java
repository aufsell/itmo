package com.itmolabs.lab5.commons.commands;

import com.itmolabs.lab5.commons.commands.commands.*;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Класс, который отвечает за вызов команд
 * <p>
 * @see BaseCommand
 * @see CommandManager
 */
public class CommandInvoker {

    /**
     * Команда, которая будет вызываться при ошибке
     *
     * @see ErrorCommand
     */
    private static BaseCommand ERROR_COMMAND;

    /**
     * Конструктор, который инициализирует все команды и добавляет их в Map
     */
    public CommandInvoker() {
        ERROR_COMMAND = new ErrorCommand();

        CommandManager.addCommands(
                ERROR_COMMAND,

                new ClearCommand(),
                new ExitCommand(),
                new ShowCommand(),
                new HelpCommand(),
                new InfoCommand(),

                // Script Commands
                new ExecuteScriptCommand(),

                // Remove Commands
                new RemoveKeyCommand(),
                new RemoveAllByDiscountCommand(),
                new RemoveLowerKeyCommand(),

                // Replace Commands
                new ReplaceIfLowerKeyCommand(),
                new ReplaceIfGreaterKeyCommand(),

                // дальше мне лень писать ((
                new FilterByTypeCommand(),
                new CountLessThanDiscountCommand(),
                new SaveCommand(),
                new InsertCommand(),
                new UpdateCommand()
        );
    }

    /**
     * Запустить интерактивный режим работы с программой
     */
    public void start() {
        // Инициализация сканнера для чтения команд
        try (Scanner commandReader = new Scanner(System.in)) {
            // Логи, молокоссосы
            System.out.println("Program is running. Write help for getting list of available commands and it's descriptions.");
            System.out.println("Run a first command: ");

            while (true) {
                // Чтение команды и её аргументов из консоли и разделение на массив строк
                String[] split = commandReader.nextLine().trim().toLowerCase().split(" ", 2);

                try {
                    String command = split[0];

                    // Если команда пустая, то пропускаем её
                    if (command.equals(" ") || command.isEmpty()) continue;

                    // Получение команды по имени
                    ICommand cmd = CommandManager.getCommand(command);

                    if (cmd == null || !CommandManager.hasCommand(command)) {
                        ERROR_COMMAND.execute();
                        continue;
                    }

                    // Выполнение команды
                    System.out.println(
                            cmd.execute(
                                    split.length > 1 ? new String[]{split[1]} : new Object[0])
                    );

                } catch (final ArrayIndexOutOfBoundsException e) { // Если аргументов нет
                    e.printStackTrace();
                    System.err.println(
                            "Argument of command is absent. Write help for getting list of available commands and it's descriptions."
                    );
                } catch (final NumberFormatException e) { // Если аргумент не число
                    e.printStackTrace();
                    System.err.println("Incorrect argument. Try again.");
                } catch (final NoSuchElementException e) { // Если команда не найдена
                    System.err.println("Program will be finished now.");
                    break;
                } catch (final IOException e) { // Если ошибка ввода/вывода
                    System.err.println("Something went wrong. Try again, error details:");
                    e.printStackTrace();

                    break;
                }
            }
        }
    }
}