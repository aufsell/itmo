package com.itmolabs.lab5.commons.commands.commands;

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
    public String execute(final Object... args) throws IOException {
        if (isClient()) return sendPacket(args);
        else {
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("Collection content:")
                    .append("\n");

            for (Map.Entry<Integer, Ticket> entry : getCollection().entrySet()) {
                stringBuilder.append(entry.getKey())
                        .append(" : ")
                        .append(entry.getValue().toString())
                        .append("\n");
            }

            return stringBuilder.toString();
        }
    }
}
