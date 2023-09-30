package com.itmolabs.lab5.commons.commands.commands;

import com.itmolabs.lab5.auth.AuthUser;
import com.itmolabs.lab5.commons.commands.BaseCommand;
import com.itmolabs.lab5.commons.commands.Command;
import com.itmolabs.lab5.commons.reader.InputReader;
import com.itmolabs.lab5.model.ticket.Ticket;

import java.io.IOException;

@Command(
        value = "insert",
        usage = "insert <key> {element}",
        description = "Inserts an element into the collection by the specified key."
)
public final class InsertCommand extends BaseCommand {

    public InsertCommand() {
        super(InsertCommand.class);
    }

    @Override
    public String execute(final AuthUser authUser, final Object... args) throws IOException {
        Ticket ticket = args != null && args.length > 0
                ? (Ticket) args[0]
                : new InputReader().receiveTicket(0, authUser);

        if (ticket == null) return "Invalid arguments. Try again.";

        if (isClient()) return sendPacket(authUser, ticket);
        else {
            int i = getDatabase().getNextId();

            ticket.setId(i);
            ticket.getVenue().setId(i);
            ticket.getCoordinates().setId(i);

            ticket.setOwner(authUser.getId());

            getDatabase().insertTicket(ticket);

            return "Element with key " + i + " has been added.";
        }
    }
}
