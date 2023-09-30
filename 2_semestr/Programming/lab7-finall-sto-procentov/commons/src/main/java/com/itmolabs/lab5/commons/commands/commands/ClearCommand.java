package com.itmolabs.lab5.commons.commands.commands;

import com.itmolabs.lab5.auth.AuthUser;
import com.itmolabs.lab5.commons.commands.BaseCommand;
import com.itmolabs.lab5.commons.commands.Command;

import java.io.IOException;

@Command(value = "clear", description = "Remove all items from collection")
public final class ClearCommand extends BaseCommand {

    public ClearCommand() {
        super(ClearCommand.class);
    }

    @Override
    public String execute(final AuthUser authUser, final Object... args) throws IOException {
        if (isClient()) return sendPacket(authUser, args);
        else {
            if (getDatabase().getCache().isEmpty()) return "Collection is already empty!\n";

            var tickets = getDatabase().getCache().values()
                    .stream()
                    .filter(ticket -> ticket.getOwner() == authUser.getId())
                    .toList();

            if (tickets.isEmpty()) return "You not owner of any ticket!\n";

            for (final var ticket : tickets)
                getDatabase().deleteTicket(ticket.getId());

            return "Collection has been cleaned!\n";
        }
    }
}