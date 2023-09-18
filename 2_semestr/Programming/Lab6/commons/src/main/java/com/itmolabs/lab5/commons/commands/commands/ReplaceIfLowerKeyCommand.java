package com.itmolabs.lab5.commons.commands.commands;

import com.itmolabs.lab5.commons.commands.BaseCommand;
import com.itmolabs.lab5.commons.commands.Command;
import com.itmolabs.lab5.commons.reader.InputReader;
import com.itmolabs.lab5.model.ticket.Ticket;

import java.io.IOException;
import java.util.Map;

@Command(
        value = "replace_if_lower",
        usage = "replace_if_lower <key> {element}",
        description = "Replace element with entered key if new element is lower"
)
public final class ReplaceIfLowerKeyCommand extends BaseCommand {

    public ReplaceIfLowerKeyCommand() {
        super(ReplaceIfLowerKeyCommand.class);
    }

    @Override
    public String execute(final Object... args) throws IOException {
        if (args.length == 0) return "Invalid arguments. Try again.";

        int key;

        if (args[0] instanceof String) key = Integer.parseInt((String) args[0]);
        else key = (int) args[0];

        Ticket ticket = ticket(argCheckOfNull(args));

        if (ticket == null) return "Invalid arguments. Try again.";

        if (isClient()) return sendPacket(key, ticket);
        else {
            try {
                Map<Integer, Ticket> collection = getCollection();

                boolean found = false;

                for (Map.Entry<Integer, Ticket> entry : collection.entrySet()) {
                    if (entry.getKey().equals(key)) {
                        found = true;

                        if (ticket.compareTo(entry.getValue()) < 0) {
                            collection.put(entry.getKey(), ticket);

                            return "Element has been replaced.";
                        }
                    }
                }

                if (found) return "Element hasn't been updated because it's not lower than given";
                return "Element has not been updated because given key is not exists.";
            } catch (final NumberFormatException e) {
                return "Incorrect argument.";
            }
        }
    }
}
