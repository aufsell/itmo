package com.itmolabs.commands.actions;

import com.itmolabs.application.CollectionManager;
import com.itmolabs.application.FileManager;
import com.itmolabs.application.InputReader;
import com.itmolabs.commands.commands.*;
import com.itmolabs.model.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

public class ExecuteScriptAction {

    private final CollectionManager collectionManager;
    private static final Set<String> callStack = new LinkedHashSet<>();


    public ExecuteScriptAction(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public String doAction(String pathToScript) {
        if (FileManager.validateScript(pathToScript)) {
            if (!callStack.contains(pathToScript)) {
                callStack.add(pathToScript);
                // do script
                StringBuilder results = new StringBuilder();
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(pathToScript));
                    String[] splitCommand;
                    String command;
                    InputReader inputReader = new InputReader(this.collectionManager);
                    while ((command = reader.readLine()) != null) {
                        splitCommand = command.trim().split(" ", 3);
                        switch (splitCommand[0]) {
                            case "help":
                            results.append(new HelpCommand(new HelpAction(this.collectionManager)).execute())
                                    .append("\n");
                            break;
                            case "info":
                                results.append(new InfoCommand(new InfoAction(this.collectionManager)).execute())
                                        .append("\n");
                                break;
                            case "show":
                                results.append(new ShowCommand(new ShowAction(this.collectionManager)).execute())
                                        .append("\n");
                                break;
                            case "insert":
                                Ticket insertTicket = fromCsvString(splitCommand[2]);
                                if (insertTicket == null) {
                                    results.append("Incorrect ticket was read as argument of a command. Cannot insert.\n")
                                            .append("\n");
                                } else {
                                    InsertCommand command0 = new InsertCommand(new InsertAction(
                                            this.collectionManager));
                                    command0.setArg(insertTicket);
                                    results.append(command0.execute(splitCommand[1])).append("\n");
                                }
                                break;
                                case "update":
                                Ticket updateTicket = fromCsvString(splitCommand[2]);
                                if (updateTicket == null) {
                                    results.append("Incorrect ticket was read as argument of a command. Cannot update.\n")
                                            .append("\n");
                                } else {
                                    InsertCommand command4 = new InsertCommand(new InsertAction(
                                            this.collectionManager));
                                    command4.setArg(updateTicket);
                                    results.append(command4.execute(splitCommand[1])).append("\n");
                                }
                                break;
                            case "remove_key":
                                results.append(new RemoveKeyCommand(new RemoveKeyAction(this.collectionManager))
                                        .execute(splitCommand[1])).append("\n");
                                break;
                            case "clear":
                                results.append(new ClearCommand(new ClearAction(this.collectionManager)).execute())
                                        .append("\n");
                                break;
                            case "save":
                                results.append(new SaveCommand(new SaveAction(this.collectionManager)).execute()).append("\n");
                                break;
                            case "execute_script":
                                results.append(new ExecuteScriptCommand(new ExecuteScriptAction(this.collectionManager))
                                        .execute(splitCommand[1])).append("\n");
                                break;
                            case "exit":
                                results.append(new ExitCommand(new ExitAction()).execute()).append("\n");
                            case "replace_if_greater":
                                Ticket replaceIfGraterTicket = fromCsvString(splitCommand[2]);
                                if (replaceIfGraterTicket == null) {
                                    results.append("Incorrect ticket was read as argument of a command. Cannot replace.\n")
                                            .append("\n");
                                } else {
                                    ReplaceIfGreaterKeyCommand command1 = new ReplaceIfGreaterKeyCommand(new ReplaceIfGreaterKeyAction(
                                            this.collectionManager));
                                    command1.setArg(replaceIfGraterTicket);
                                    results.append(command1.execute(splitCommand[1])).append("\n");
                                }
                                break;
                            case "replace_if_lower":
                                Ticket replaceIfLowerTicket = fromCsvString(splitCommand[2]);
                                if (replaceIfLowerTicket == null) {
                                    results.append("Incorrect ticket was read as argument of a command. Cannot replace.\n")
                                            .append("\n");
                                } else {
                                    ReplaceIfLowerKeyCommand command2 = new ReplaceIfLowerKeyCommand(new ReplaceIfLowerKeyAction(
                                            this.collectionManager));
                                    command2.setArg(replaceIfLowerTicket);
                                    results.append(command2.execute(splitCommand[1])).append("\n");
                                }
                                break;
                            case "remove_lower_key":
                                results.append(new RemoveLowerKeyCommand(new RemoveLowerKeyAction(this.collectionManager))
                                                .execute(splitCommand[1]))
                                        .append("\n");
                                break;
                            case "remove_all_by_discount":
                                results.append(new RemoveAllByDiscountCommand(new RemoveAllByDiscountAction(this.collectionManager))
                                        .execute(splitCommand[1])).append("\n");
                                break;
                            case "count_less_than_discount":
                                results.append(new CountLessThanDiscountActionCommand(
                                                new CountLessThanDiscountAction(this.collectionManager)).execute(splitCommand[1]))
                                        .append("\n");
                                break;
                                case "filter_by_type":
                                results.append(new FilterByTypeCommand(
                                        new FilterByTypeAction(this.collectionManager))
                                        .execute(splitCommand[1])).append("\n");
                                break;
                            default:
                                reader.readLine();
                                break;
                        }
                    }
                } catch (FileNotFoundException fileNotFoundException) {
                    return "File with script not found. Check path to script and try again.\n";
                } catch (NumberFormatException numberFormatException) {
                    numberFormatException.printStackTrace();
                    return "Incorrect argument.\n";
                } catch (IOException ioException) {
                    return "File reading problems. Try to check file permissions or syntax and try again.\n";
                }
                callStack.remove(pathToScript);
                return results.toString();
            } else {
                return "Ring recursion detected. Script executing aborted.\n";
            }
        } else {
            return "Problems with script. It must be a file with correct path and you must have permissions for " +
                    "reading it. Check it and try again.\n";
        }
    }
    private String toCsvString(Ticket ticket) {
        return ticket.getId() + "," + ticket.getName() + "," + ticket.getCoordinates().getX() + "," + ticket.getCoordinates().getY() + "," + ticket.getCreationDate().toString() + "," + ticket.getPrice() + "," + ticket.getDiscount() + "," + ticket.getType() + "," + ticket.getVenue().getId() + "," + ticket.getVenue().getName() + "," + ticket.getVenue().getCapacity() + "," + ticket.getVenue().getType();
    }

    public static Ticket fromCsvString(String csvString) {
        try {
            String[] fields = csvString.split(",");
            int id = Integer.parseInt(fields[0]);
            String name = fields[1];
            float x = Float.parseFloat(fields[2]);
            float y = Float.parseFloat(fields[3]);
            LocalDate creationDate = LocalDate.parse(fields[4]);
            Long price = fields[5].equals("null") ? null : Long.parseLong(fields[5]);
            Float discount = fields[6].equals("null") ? null : Float.parseFloat(fields[6]);
            TicketType type = fields[7].equals("null") ? null : TicketType.valueOf(fields[7]);
            int venueId = Integer.parseInt(fields[8]);
            String venueName = fields[9];
            int venueCapacity = Integer.parseInt(fields[10]);
            VenueType venueType = fields[11].equals("null") ? null : VenueType.valueOf(fields[11]);
            return new Ticket(id, name, new Coordinates(x, y), creationDate, price, discount, type, new Venue(venueId, venueName, venueCapacity, venueType));
        } catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }
    }


}

