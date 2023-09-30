package com.itmolabs.lab5.commons.commands.commands;

import com.itmolabs.lab5.auth.AuthUser;
import com.itmolabs.lab5.commons.commands.BaseCommand;
import com.itmolabs.lab5.commons.commands.Command;
import com.itmolabs.lab5.model.ticket.Ticket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
    public String execute(final AuthUser authUser, final Object... args) throws IOException {
        if (args.length == 0) return "Invalid arguments. Try again.";

        int key;

        if (args[0] instanceof String) key = Integer.parseInt((String) args[0]);
        else key = (int) args[0];

        if (key < 0) return "Command failed. Key cannot be negative.";

        if (isClient()) return sendPacket(authUser, key);
        else {
            var tickets = getDatabase().getCache().values()
                    .stream()
                    .filter(ticket -> ticket.getOwner() == authUser.getId())
                    .toList();

            if (tickets.isEmpty()) return "You not owner of any ticket!";

            int counter = 0;

            // без копии, будет ConcurrentModificationException
            for (Map.Entry<Integer, Ticket> entry :
                    new HashMap<>(getDatabase().getCache()).entrySet()) {
                if (entry.getValue().getOwner() != authUser.getId()) continue;
                if (entry.getKey() >= key) continue;

                counter++;
                getDatabase().deleteTicket(entry.getKey());
            }

            return "Command executed. Removed " + counter + " elements.";
        }
    }
}
