package com.itmolabs.lab5.commons.commands.commands;

import com.itmolabs.lab5.commons.commands.BaseCommand;
import com.itmolabs.lab5.commons.commands.Command;
import com.itmolabs.lab5.commons.generator.IdentifierGenerator;

import java.io.IOException;

@Command(value = "clear", description = "Remove all items from collection")
public final class ClearCommand extends BaseCommand {

    public ClearCommand() {
        super(ClearCommand.class);
    }

    @Override
    public String execute(final Object... args) throws IOException {
        if (isClient()) return sendPacket(args);
        else {
            getCollectionManager().clearCollection();
            IdentifierGenerator.reset();
        }

        return "Collection has been cleaned!\n";
    }
}