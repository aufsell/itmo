package com.itmolabs.lab5.commons.commands.commands;

import com.itmolabs.lab5.auth.AuthUser;
import com.itmolabs.lab5.commons.commands.BaseCommand;
import com.itmolabs.lab5.commons.commands.Command;
import com.itmolabs.lab5.model.ticket.Ticket;

import java.io.IOException;
import java.util.Map;

@Command(value = "show")
public final class ShowCommand extends BaseCommand {

    public ShowCommand() {
        super(ShowCommand.class);
    }

    @Override
    public String execute(final AuthUser authUser, final Object... args) throws IOException {
        if (isClient()) return sendPacket(authUser, args);
        else {
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("Tickets content:")
                    .append("\n");

            final var tickets = getDatabase().getCache().entrySet();

            if (tickets.isEmpty()) return "Tickets is empty.";

            for (Map.Entry<Integer, Ticket> entry : tickets) {
                boolean own = entry.getValue().getOwner()
                        == authUser.getId();

                stringBuilder
                        .append(own ? "\u001B[32m" : "\u001B[0m")
                        .append(entry.getKey())
                        .append(" : ")
                        .append(own ? "You" : entry.getValue().getOwner())
                        .append(" : ")
                        .append(entry.getValue().toString())
                        .append("\u001B[0m\n");
            }

            return stringBuilder.toString();
        }
    }
}
