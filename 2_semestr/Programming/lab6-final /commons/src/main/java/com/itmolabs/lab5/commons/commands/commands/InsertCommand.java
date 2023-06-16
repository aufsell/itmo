package com.itmolabs.lab5.commons.commands.commands;

import com.itmolabs.lab5.commons.commands.BaseCommand;
import com.itmolabs.lab5.commons.commands.Command;
import com.itmolabs.lab5.commons.generator.IdentifierGenerator;
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
    public String execute(final Object... args) throws IOException {
        if (args.length == 0) return "Invalid arguments. Try again.";

        int key;

        if (args[0] instanceof String) key = Integer.parseInt((String) args[0]);
        else key = (int) args[0];

        if (key < 0) return "Command failed. Key cannot be negative.";

        Ticket ticket = args.length > 1 ? (Ticket) args[1] : new InputReader().receiveTicket();

        if (ticket == null) {
            return "Invalid arguments. Try again.";
        }

        if (isClient()) return sendPacket(key, ticket);
        else {
            if (IdentifierGenerator.ifUnique(key)) {
                ticket.setId(key);
                getCollection().put(key, ticket);

                if (IdentifierGenerator.addId(ticket.getId())) return "Element with key " + key + " has been added.";
                else return "Element with key " + key + " has been added, but ID generator is not working.";
            } else return "Key must be unique. Check collection items and try again.";
        }
    }
}
