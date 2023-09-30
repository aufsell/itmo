package com.itmolabs.lab5.commons.commands.commands;

import com.itmolabs.lab5.auth.AuthUser;
import com.itmolabs.lab5.commons.commands.BaseCommand;
import com.itmolabs.lab5.commons.commands.Command;
import com.itmolabs.lab5.model.ticket.Ticket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Command(
        value = "remove_key",
        usage = "remove_key <key>",
        description = "Remove element from collection by key"
)
public final class RemoveKeyCommand extends BaseCommand {

    public RemoveKeyCommand() {
        super(RemoveKeyCommand.class);
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
            try {
                for (Map.Entry<Integer, Ticket> entry :
                        new HashMap<>(getDatabase().getCache()).entrySet().stream()
                                .toList()
                ) {
                    if (!entry.getKey().equals(key)) continue;

                    if (entry.getValue().getOwner() != authUser.getId()) {
                        return "You don't have permission to delete this ticket!";
                    }

                    getDatabase().deleteTicket(entry.getKey());

                    return "Element has been removed!";
                }

                return "Element with entered key doesn't exists and that's why it is hasn't been deleted!";
            } catch (final NumberFormatException exception) {
                return "Incorrect argument.";
            }
        }
    }

}
