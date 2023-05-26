package com.itmolabs.commands;

import com.itmolabs.application.CollectionManager;
import com.itmolabs.application.InputReader;
import com.itmolabs.commands.actions.*;
import com.itmolabs.commands.commands.*;

import java.util.*;

public class CommandInvoker {

    private String command = "";
    private final CollectionManager collectionManager;
    private Map<String, Command> commandMap;

    public CommandInvoker(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        this.commandMap = new HashMap<>();
        this.commandMap.put("help", new HelpCommand(new HelpAction(this.collectionManager)));
        this.commandMap.put("info", new InfoCommand(new InfoAction(this.collectionManager)));
        this.commandMap.put("show", new ShowCommand(new ShowAction(this.collectionManager)));
        this.commandMap.put("insert", new InsertCommand(new InsertAction(this.collectionManager)));
        this.commandMap.put("update", new UpdateCommand(new UpdateAction(this.collectionManager)));
        this.commandMap.put("remove_key", new RemoveKeyCommand(new RemoveKeyAction(this.collectionManager)));
        this.commandMap.put("clear", new ClearCommand(new ClearAction(this.collectionManager)));
        this.commandMap.put("save", new SaveCommand(new SaveAction(this.collectionManager)));
        this.commandMap.put("execute_script", new ExecuteScriptCommand(new ExecuteScriptAction(this.collectionManager)));
        this.commandMap.put("exit", new ExitCommand(new ExitAction()));
        this.commandMap.put("remove_lower_key", new RemoveLowerKeyCommand(new RemoveLowerKeyAction(this.collectionManager)));
        this.commandMap.put("replace_if_greater", new ReplaceIfGreaterKeyCommand(new ReplaceIfGreaterKeyAction(this.collectionManager)));
        this.commandMap.put("replace_if_lower", new ReplaceIfLowerKeyCommand(new ReplaceIfLowerKeyAction(this.collectionManager)));
        this.commandMap.put("count_less_than_discount", new CountLessThanDiscountActionCommand(new CountLessThanDiscountAction(this.collectionManager)));
        this.commandMap.put("remove_all_by_discount", new RemoveAllByDiscountCommand(new RemoveAllByDiscountAction(this.collectionManager)));
        this.commandMap.put("filter_by_type", new FilterByTypeCommand(new FilterByTypeAction(this.collectionManager)));

    }

    public void run() {
        try {
            try (Scanner commandReader = new Scanner(System.in)) {
                CommandInvoker commandInvoker = new CommandInvoker(this.collectionManager);
                while (!command.equals("exit")) {
                    System.out.print("Enter a command: ");
                    command = commandReader.nextLine();
                    String[] splitCommand = command.trim().toLowerCase().split(" ", 2);
                    InputReader inputReader = new InputReader(this.collectionManager);
                    try {
                        Command errorCommand = new Command() {
                            @Override
                            public String execute() {
                                return "Unknown command. Write help for receiving list of available commands.";
                            }
                        };
                        if (splitCommand.length == 1) {
                            System.out.println(commandMap.getOrDefault(splitCommand[0], errorCommand).execute());
                        } else {
                            System.out.println(commandMap.getOrDefault(splitCommand[0], errorCommand).execute(splitCommand[1]));
                        }

                    } catch (ArrayIndexOutOfBoundsException ex) {
                        System.out.println("Argument of command is absent. Write help for getting list of available " +
                                "commands and it's descriptions.");
                    } catch (NumberFormatException numberFormatException) {
                        System.out.println("Incorrect argument. Try again.");
                    }
                }
            }
        } catch (NoSuchElementException noSuchElementException) {
            System.out.println("Program will be finished now.");
            System.exit(0);
        }
    }
}