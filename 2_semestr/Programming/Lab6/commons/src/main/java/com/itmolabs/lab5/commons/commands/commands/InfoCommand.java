package com.itmolabs.lab5.commons.commands.commands;

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
    public String execute(final Object... args) throws IOException {
        if (isClient()) return sendPacket(args);
        else return String.format("%-32s%s%n%-32s%s%n%-32s%s%n",
                "Type of collection:", getCollection().getClass(),
                "Collection initialization time:", getCollectionManager().getInitializationDate(),
                "Size of collection:", getCollection().size());
    }
}
