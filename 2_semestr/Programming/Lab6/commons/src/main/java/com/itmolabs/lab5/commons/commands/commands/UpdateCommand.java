package com.itmolabs.lab5.commons.commands.commands;

import com.itmolabs.lab5.commons.commands.BaseCommand;
import com.itmolabs.lab5.commons.commands.Command;
import com.itmolabs.lab5.model.ticket.Ticket;

import java.io.IOException;

@Command(
        value = "update",
        usage = "update <id> {element}",
        description = "Update the value of the collection element whose id is equal to the given."
)
public final class UpdateCommand extends BaseCommand {

    public UpdateCommand() {
        super(UpdateCommand.class);
    }

    @Override
    public String execute(final Object... args) throws IOException {
        if (args.length == 0) return "Invalid arguments. Try again.";

        int key;

        if (args[0] instanceof String) key = Integer.parseInt((String) args[0]);
        else key = (int) args[0];

        if (key < 0) return "Command failed. Key cannot be negative.";

        Ticket ticket = ticket(argCheckOfNull(args));

        if (ticket == null) return "Invalid arguments. Try again.";

        if (isClient()) return sendPacket(key, ticket);
        else {
            for (Ticket t : getCollection().values()) {
                if (t.getId() == key) {
                    getCollection().remove(key);

                    ticket.setId(key);
                    getCollection().put(key, ticket);

                    return "Element with key " + key + " has been updated.";
                }
            }

            return "Element hasn't been updated because there is no element with key " + key + ".";
        }
    }
}
