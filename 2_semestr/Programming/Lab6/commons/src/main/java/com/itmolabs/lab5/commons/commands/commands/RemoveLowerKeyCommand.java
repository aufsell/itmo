package com.itmolabs.lab5.commons.commands.commands;

import com.itmolabs.lab5.commons.commands.*;

import com.itmolabs.lab5.commons.generator.IdentifierGenerator;
import com.itmolabs.lab5.model.ticket.Ticket;

import java.io.IOException;

import java.util.Map.Entry;
import java.util.*;

@Command(
        value = "remove_lower_key",
        usage = "remove_lower_key <key>",
        description = "Remove all elements from the collection that are less than the specified key"
)
public final class RemoveLowerKeyCommand extends BaseCommand {

    public RemoveLowerKeyCommand() {
        super(RemoveLowerKeyCommand.class);
    }

    @Override
    public String execute(final Object... args) throws IOException {
        if (args.length == 0) return "Invalid arguments. Try again.";

        int key;

        if (args[0] instanceof String) key = Integer.parseInt((String) args[0]);
        else key = (int) args[0];

        if (key < 0) return "Command failed. Key cannot be negative.";

        if (isClient()) return sendPacket(key);
        else {
            int counter = 0;

            // без копии, будет ConcurrentModificationException
            for (Entry<Integer, Ticket> entry :
                    new HashMap<>(getCollection()).entrySet()) {
                final int tempKey = entry.getKey();

                if (tempKey < key) continue;

                counter++;
                getCollection().put(tempKey, entry.getValue());
                IdentifierGenerator.removeId(entry.getKey());
            }

            return "Command executed. Removed " + counter + " elements.";
        }
    }
}
