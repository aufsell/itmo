package com.itmolabs.lab5.commons.commands.commands;

import com.itmolabs.lab5.commons.commands.BaseCommand;
import com.itmolabs.lab5.commons.commands.Command;
import com.itmolabs.lab5.commons.generator.IdentifierGenerator;
import com.itmolabs.lab5.commons.reader.InputReader;
import com.itmolabs.lab5.model.ticket.Ticket;

import java.io.IOException;
import java.util.Map;

@Command(
        value = "replace_if_greater_key",
        description = "Replaces the value by key if the new value is greater than the old one."
)
public final class ReplaceIfGreaterKeyCommand extends BaseCommand {

    public ReplaceIfGreaterKeyCommand() {
        super(ReplaceIfGreaterKeyCommand.class);
    }

    @Override
    public String execute(final Object... args) throws IOException {
        if (args.length == 0) return "Invalid arguments. Try again.";

        int key;

        if (args[0] instanceof String) key = Integer.parseInt((String) args[0]);
        else key = (int) args[0];

        if (key < 0) return "Command failed. Key cannot be negative.";

        Ticket ticket = (Ticket) args[1];

        if (ticket == null) ticket = new InputReader().receiveTicket();

        if (ticket == null) return "Invalid arguments. Try again.";

        if (isClient()) return sendPacket(args, ticket);
        else {
            boolean isFound = false;

            if (IdentifierGenerator.ifUnique(key)) {
                ticket.setId(key);

                for (Map.Entry<Integer, Ticket> entry : getCollection().entrySet()) {
                    if (entry.getKey().equals(key)) continue;

                    isFound = true;

                    if (ticket.compareTo(entry.getValue()) > 0) {
                        getCollection().put(key, ticket);

                        return "Element with key " + key + " has been replaced.";
                    }
                }
            }

            if (!isFound) return "Element hasn't been updated because there is no element with key " + key + ".";
            else return "Element hasn't been updated because given key is not exists.";
        }
    }
}
