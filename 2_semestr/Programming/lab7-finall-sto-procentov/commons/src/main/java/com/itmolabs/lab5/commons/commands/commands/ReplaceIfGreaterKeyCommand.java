package com.itmolabs.lab5.commons.commands.commands;

import com.itmolabs.lab5.auth.AuthUser;
import com.itmolabs.lab5.commons.commands.BaseCommand;
import com.itmolabs.lab5.commons.commands.Command;
import com.itmolabs.lab5.model.ticket.Ticket;

import java.io.IOException;
import java.util.Map;

@Command(
        value = "replace_if_greater_key",
        usage = "replace_if_greater_key <key> {element}",
        description = "Replaces the value by key if the new value is greater than the old one."
)
public final class ReplaceIfGreaterKeyCommand extends BaseCommand {

    public ReplaceIfGreaterKeyCommand() {
        super(ReplaceIfGreaterKeyCommand.class);
    }

    @Override
    public String execute(final AuthUser authUser, final Object... args) throws IOException {
        if (args.length == 0) return "Invalid arguments. Try again.";

        int key;

        if (args[0] instanceof String) key = Integer.parseInt((String) args[0]);
        else key = (int) args[0];

        if (key < 0) return "Command failed. Key cannot be negative.";

        Ticket ticket = ticket(authUser, argCheckOfNull(args));

        if (ticket == null) return "Invalid arguments. Try again.";

        if (isClient()) return sendPacket(authUser, key, ticket);
        else {
            var tickets = getDatabase().getCache().values()
                    .stream()
                    .filter(t -> t.getOwner() == authUser.getId())
                    .toList();

            if (tickets.isEmpty()) return "You not owner of any ticket!";

            boolean isFound = false;

            for (Map.Entry<Integer, Ticket> entry : getDatabase()
                    .getCache()
                    .entrySet()
            ) {
                if (entry.getKey() != key) continue;
                if (entry.getValue().getOwner() != authUser.getId()) {
                    return "You don't have permission to replace this ticket!";
                }

                isFound = true;

                if (ticket.compareTo(entry.getValue()) > 0) {
                    getDatabase()
                            .getCache()
                            .replace(key, ticket);

                    return "Element with key " + key + " has been replaced.";
                }
            }

            if (!isFound) return "Element hasn't been updated because there is no element with key " + key + ".";
            else return "Element hasn't been updated because given key is not exists.";
        }
    }
}
