package com.itmolabs.commands.actions;

import com.itmolabs.application.CollectionManager;
import com.itmolabs.application.FileManager;
import com.itmolabs.model.Ticket;
import com.itmolabs.serializers.CollectionSerializer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class SaveAction {

    private CollectionManager collectionManager;

    public SaveAction(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public String doAction() {
        Map<Integer, Ticket> collection = this.collectionManager.getCollection();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.collectionManager.getPathToCollection()))) {
            String xml = CollectionSerializer.serialize(collection);
            writer.write(xml);
            writer.close();
            return "Collection saved.";
        } catch (IOException ioException) {
            return "Cannot save collection to file. Try again.";
        }
    }

}
