package com.itmolabs.lab5.commons.commands.commands;

import com.itmolabs.lab5.auth.AuthUser;
import com.itmolabs.lab5.commons.commands.BaseCommand;
import com.itmolabs.lab5.commons.commands.Command;
import com.itmolabs.lab5.commons.commands.CommandManager;
import com.itmolabs.lab5.commons.commands.ICommand;
import com.itmolabs.lab5.commons.manager.FileManager;
import com.itmolabs.lab5.model.Coordinates;
import com.itmolabs.lab5.model.ticket.Ticket;
import com.itmolabs.lab5.model.ticket.TicketType;
import com.itmolabs.lab5.model.venue.Venue;
import com.itmolabs.lab5.model.venue.VenueType;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

@Command(
        value = "execute_script",
        usage = "execute_script <file>",
        description = "Execute script from file"
)
public final class ExecuteScriptCommand extends BaseCommand {

    public ExecuteScriptCommand() {
        super(ExecuteScriptCommand.class);
    }

    private static final Set<String> callStack
            = new LinkedHashSet<>();

    @Override
    public String execute(final AuthUser authUser, final Object... args) throws IOException {
        if (args.length == 0) return "Command failed. No path provided.";

        String pathToScript = (String) args[0];

        if (pathToScript == null) return "Command failed. No path provided.";

        if (isClient()) return sendPacket(authUser, pathToScript);
        else {
            if (FileManager.validateScript(pathToScript)) {
                if (!callStack.contains(pathToScript)) {
                    callStack.add(pathToScript);

                    StringBuilder results = new StringBuilder();

                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(pathToScript));
                        String[] splitCommand;
                        String command;

                        while ((command = reader.readLine()) != null) {
                            splitCommand = command.trim().split(" ", 2);

                            if (splitCommand.length == 0) continue;

                            if (splitCommand[0].equals("execute_script")) {
                                results.append("Script recursion detected. Script will not be executed.\n")
                                        .append("\n");
                                continue;
                            }

                            if (!CommandManager.hasCommand(splitCommand[0])) {
                                results.append("Command \"")
                                        .append(splitCommand[0])
                                        .append("\" not found.\n")
                                        .append("\n");
                                continue;
                            }

                            ICommand cmd = CommandManager.getCommand(splitCommand[0]);

                            switch (cmd.getName()) {
                                case "insert" -> {
                                    Ticket ticket = fromCsvStringInsert(authUser, splitCommand[1]);

                                    results.append(cmd.execute(authUser, new Object[]{ticket})).append("\n");
                                }

                                case "update", "replace_if_greater", "replace_if_lower" -> {
                                    Ticket ticket = fromCsvString(authUser, splitCommand[2]);

                                    String cant = cmd.getName().startsWith("replace")
                                            ? "replace" : cmd.getName().toLowerCase();

                                    if (ticket == null)
                                        results.append(String.format(
                                                "Incorrect ticket was read as argument of a command. Cannot %s.\n", cant
                                        )).append("\n");
                                    else
                                        results.append(cmd.execute(authUser, new Object[]{splitCommand[1], ticket})).append("\n");

                                }

                                default -> results.append(cmd.execute(authUser, new String[]{
                                        splitCommand[1]
                                })).append("\n");
                            }
                        }
                    } catch (final FileNotFoundException e) {
                        return "File with script not found. Check path to script and try again.\n";
                    } catch (final NumberFormatException e) {
                        e.printStackTrace();

                        return "Incorrect argument.\n";
                    } catch (final IOException e) {
                        return "File reading problems. Try to check file permissions or syntax and try again.\n";
                    }

                    callStack.remove(pathToScript);
                    return results.toString();
                } else return "Ring recursion detected. Script executing aborted.\n";

            } else
                return "Problems with script. It must be a file with correct path and you must have permissions for " +
                        "reading it. Check it and try again.\n";

        }
    }

    /**
     * До сих пор не понимаю, зачем этот метод нужен.
     *
     * @param ticket - билет
     * @return строка в формате CSV
     */
    private String toCsvString(final Ticket ticket) {
        return String.format(
                "%d,%s,%f,%f,%s,%d,%f,%s,%d,%s,%d,%s",
                ticket.getId(), ticket.getName(),
                ticket.getCoordinates().getX(), ticket.getCoordinates().getY(),
                ticket.getCreationDate().toString(), ticket.getPrice(), ticket.getDiscount(),
                ticket.getType(),
                ticket.getVenue().getId(), ticket.getVenue().getName(), ticket.getVenue().getCapacity(), ticket.getVenue().getType()
        );
    }

    private Ticket fromCsvStringInsert(
            final AuthUser authUser, final String csvString
    ) {
        try {
            String[] fields = csvString.split(",");

            String name = fields[0];
            float x = Float.parseFloat(fields[1]);
            float y = Float.parseFloat(fields[2]);

            LocalDateTime creationDate = LocalDateTime.parse(fields[3], DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm"));

            long price = fields[4].equals("null") ? 0 : Long.parseLong(fields[4]);
            float discount = fields[5].equals("null") ? 0 : Float.parseFloat(fields[5]);

            // TicketType
            TicketType type = fields[6].equals("null") ? null : TicketType.valueOf(fields[6]);

            // Venue
            String venueName = fields[7];
            int venueCapacity = Integer.parseInt(fields[8]);

            VenueType venueType = fields[9].equals("null") ? null : VenueType.valueOf(fields[9]);

            return new Ticket(0, authUser.getId(), name, new Coordinates(0, x, y), creationDate, price, discount, type, new Venue(0, venueName, venueCapacity, venueType));
        } catch (final IllegalArgumentException ignored) {
            return null;
        }
    }

    /**
     * Ковертироет строку в формате CSV в билет.
     *
     * @param csvString - строка в формате CSV
     * @return {@link Ticket}
     */
    private Ticket fromCsvString(
            final AuthUser user, final String csvString) {
        try {
            String[] fields = csvString.split(",");

            int id = Integer.parseInt(fields[0]);
            String name = fields[1];
            float x = Float.parseFloat(fields[2]);
            float y = Float.parseFloat(fields[3]);

            LocalDateTime creationDate = LocalDateTime.parse(fields[4], DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm"));

            long price = fields[5].equals("null") ? 0 : Long.parseLong(fields[5]);
            float discount = fields[6].equals("null") ? 0 : Float.parseFloat(fields[6]);

            // TicketType
            TicketType type = fields[7].equals("null") ? null : TicketType.valueOf(fields[7]);

            // Venue
            int venueId = Integer.parseInt(fields[8]);
            String venueName = fields[9];
            int venueCapacity = Integer.parseInt(fields[10]);

            VenueType venueType = fields[11].equals("null") ? null : VenueType.valueOf(fields[11]);

            return new Ticket(id, user.getId(), name, new Coordinates(0, x, y), creationDate, price, discount, type, new Venue(venueId, venueName, venueCapacity, venueType));
        } catch (final IllegalArgumentException ignored) {
            return null;
        }
    }
}