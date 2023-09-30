package com.itmolabs.lab5.commons.commands.commands;

import com.itmolabs.lab5.auth.AuthUser;
import com.itmolabs.lab5.commons.commands.BaseCommand;
import com.itmolabs.lab5.commons.commands.Command;
import com.itmolabs.lab5.model.ticket.Ticket;
import com.itmolabs.lab5.model.ticket.TicketType;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Command(
        value = "filter_by_type",
        usage = "filter_by_type <type>",
        description = "Print elements, filtered by type of which is greater than a given."
)
public final class FilterByTypeCommand extends BaseCommand {

    public FilterByTypeCommand() {
        super(FilterByTypeCommand.class);
    }

    private static final String AVAILABLE_TYPES = Arrays.stream(TicketType.values())
            .map(TicketType::name)
            .collect(Collectors.joining(", "));

    public String execute(final AuthUser authUser, final Object... args) throws IOException {
        if (args.length == 0) return "Command failed. No ticket type provided.";

        String ticketTypeString = (String) args[0];

        if (ticketTypeString == null) return "Command failed. No ticket type provided.";

        if (isClient()) return sendPacket(authUser, ticketTypeString);
        else {
            TicketType ticketType = TicketType.of(ticketTypeString.toUpperCase());

            if (ticketType == null)
                return String.format("Command failed. Ticket type not found, available types: %s.", AVAILABLE_TYPES);

            try {
                StringBuilder stringBuilder = new StringBuilder();

                final var tickets = getDatabase().getCache().values()
                        .stream()
                        .filter(ticket -> ticket.getType() == ticketType)
                        .toList();

                if (tickets.isEmpty()) return "Collection is empty.";

                for (final var ticket : tickets) {
                    stringBuilder
                            .append(ticket.getId())
                            .append(" : ")
                            .append(ticket)
                            .append("\n");
                }

                return stringBuilder.toString();
            } catch (final IllegalArgumentException ignored) {
                return "Incorrect argument.";
            }
        }
    }

}
