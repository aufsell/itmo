package com.itmolabs.lab5.commons.commands.commands;

import com.itmolabs.lab5.commons.commands.BaseCommand;
import com.itmolabs.lab5.commons.commands.Command;
import com.itmolabs.lab5.commons.commands.CommandManager;
import com.itmolabs.lab5.commons.serializers.CollectionSerializer;
import com.itmolabs.lab5.model.ticket.Ticket;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

@Command(
        value = "save",
        description = "Save collection to file"
)
public final class SaveCommand extends BaseCommand {

    public SaveCommand() {
        super(SaveCommand.class);

        if (!isClient()) CommandManager.removeCommand(SaveCommand.class);
    }

    @Override
    public String execute(final Object[] ignored) throws IOException {
        if (isClient()) return sendPacket(ignored);
        else {
            Map<Integer, Ticket> collection = getCollection();

            try (final BufferedWriter writer = new BufferedWriter(new FileWriter(getCollectionManager().getPathToCollection()))) {
                String xml = CollectionSerializer.serialize(collection);

                writer.write(xml);
                writer.close();

                return "Collection saved.";
            } catch (final Exception exception) {
                return "Cannot save collection to file. Try again.";
            }
        }
    }
}
