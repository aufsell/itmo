package com.itmolabs.lab5.commons.commands.commands;

import com.itmolabs.lab5.auth.AuthUser;
import com.itmolabs.lab5.commons.commands.BaseCommand;
import com.itmolabs.lab5.commons.commands.Command;

import java.io.IOException;

@Command(
        value = "info",
        description = "Print all collection items into the string representation"
)
public final class InfoCommand extends BaseCommand {

    public InfoCommand() {
        super(InfoCommand.class);
    }

    @Override
    public String execute(final AuthUser authUser, final Object... args) throws IOException {
        if (isClient()) return sendPacket(authUser, args);
        else {
            final var tickets = getDatabase().getCache().values()
                    .stream()
                    .filter(ticket -> ticket.getOwner() == authUser.getId())
                    .toList();

            if (tickets.isEmpty()) return "Tickets is empty.";

            final var localDate = tickets.get(0).getCreationDate();

            return String.format("%n%-32s%s%n%-32s%s%n",
                    "First Ticket initialization time:", localDate,
                    "Size of own tickets:", tickets.size());
        }
    }
}
