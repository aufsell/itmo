package com.itmolabs.lab5.commons.commands;

import com.itmolabs.lab5.auth.AuthAction;
import com.itmolabs.lab5.auth.AuthUser;
import com.itmolabs.lab5.commons.Lazy;
import com.itmolabs.lab5.commons.commands.commands.*;
import com.itmolabs.lab5.protocol.channel.AbstractClientChannel;
import com.itmolabs.lab5.protocol.packet.implementation.LoginPacket;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Класс, который отвечает за вызов команд
 * <p>
 *
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

    private AuthUser authUser;

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
                //   new SaveCommand(),
                new InsertCommand(),
                new UpdateCommand()
        );
    }

    /**
     * Запустить интерактивный режим работы с программой
     */
    public void start() throws IOException {
        AbstractClientChannel client = Lazy.getClient();

        if (client == null) {
            System.err.println("Client is not initialized, good bye!");
            System.exit(1);
        }

        // Инициализация сканнера для чтения команд
        Scanner commandReader = new Scanner(System.in);

        while (true) {
            AuthAction action = null;

            while (action == null) {
                System.out.println("Do you want to register or login? (r/l)");

                final var answer = commandReader.nextLine().trim().toLowerCase();

                if (answer.equals("r")) action = AuthAction.REGISTER;
                else if (answer.equals("l")) action = AuthAction.LOGIN;
                else System.out.println("Incorrect answer, try again!");
            }

            String login = null;

            while (login == null) {
                System.out.println("Enter login: ");

                login = commandReader.nextLine().trim();

                if (login.length() < 4) {
                    System.out.println("Login is too short, try again!");
                    login = null;
                } else if (login.length() > 16) {
                    System.out.println("Login is too long, try again!");
                    login = null;
                }
            }

            String password = null;

            while (password == null) {
                System.out.println("Enter password: ");

                password = commandReader.nextLine().trim();

                if (password.length() < 4) {
                    System.out.println("Password is too short, try again!");

                    password = null;
                } else if (password.length() > 32) {
                    System.out.println("Password is too long, try again!");

                    password = null;
                }
            }

            LoginPacket.Response response;

            if (!(response = client.tryLoginAndReceiveResponse(login, password, action)).isSuccess()) {
                System.out.println(response.getResponse());
                continue;
            }

            this.authUser = response.getAuthUser();

            // But sorry, I'm too lazy to write a normal code
            // It's very bad, but I don't have time to write a normal code
            if (response.isSuccess())
                if (action == AuthAction.REGISTER)
                    System.out.println("You have successfully registered!");
                else
                    System.out.println("You have successfully logged in! Hello to system! :)");

            System.out.println("Program is running. Write help for getting list of available commands and it's descriptions.");
            System.out.println("Run a first command: ");

            while (true) {
                // На всякий случай :)
                if (authUser == null) {
                    System.out.println("You are not logged in, good bye!");
                    System.exit(1);
                }

                // Чтение команды и её аргументов из консоли и разделение на массив строк
                String[] split = commandReader.nextLine().trim().toLowerCase().split(" ", 2);

                try {
                    String command = split[0];

                    // Если команда пустая, то пропускаем её
                    if (command.equals(" ") || command.isEmpty()) continue;

                    // Получение команды по имени
                    ICommand cmd = CommandManager.getCommand(command);

                    if (cmd == null
                            || !CommandManager.hasCommand(command)) {
                        System.out.println(ERROR_COMMAND.execute(null)); // разницы нет :D
                        continue;
                    }

                    // Выполнение команды
                    System.out.println(
                            cmd.execute(authUser, split.length > 1 ? new String[]{split[1]} : new Object[0])
                    );
                } catch (final ArrayIndexOutOfBoundsException e) { // Если аргументов нет
                    System.err.println(
                            "Argument of command is absent. Write help for getting list of available commands and it's descriptions."
                    );
                } catch (final NumberFormatException e) { // Если аргумент не число
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