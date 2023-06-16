package com.itmolabs.lab5.commons.commands.commands;

import com.itmolabs.lab5.commons.commands.BaseCommand;
import com.itmolabs.lab5.commons.commands.Command;
import com.itmolabs.lab5.commons.generator.IdentifierGenerator;
import com.itmolabs.lab5.model.ticket.Ticket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

        if (isClient()) return sendPacket(args);
        else {
            int count = 0;

            Map<Integer, Ticket> temp = new HashMap<>(getCollection().size()); // initial capacity

            for (Map.Entry<Integer, Ticket> entry : getCollection().entrySet()) {
                if (entry.getKey() < key) {
                    count++;
                    continue;
                }

                temp.put(entry.getKey(), entry.getValue());
                IdentifierGenerator.removeId(entry.getKey());
            }

            getCollectionManager().setCollection(temp);

            return "Command executed. Removed " + count + " elements.";
        }
    }
}
