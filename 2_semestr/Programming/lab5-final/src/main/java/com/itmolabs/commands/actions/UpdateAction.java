package com.itmolabs.commands.actions;

import com.itmolabs.application.CollectionManager;
import com.itmolabs.model.Ticket;

import java.util.Map;

public class UpdateAction {

    private CollectionManager collectionManager;

    public UpdateAction(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public String doAction(Integer id, Ticket updatedTicket) {
        updatedTicket.setId(id);
        Map<Integer, Ticket> collection = this.collectionManager.getCollection();
        for (Map.Entry<Integer, Ticket> entry : collection.entrySet()) {
            if (entry.getValue().getId().equals(id)) {
                collection.remove(entry.getKey());
                updatedTicket.setId(id);
                collection.put(updatedTicket.getId(), updatedTicket);
                return "Element has been updated!\n";
            }
        }
        return "Element has not been updated because there are no element with entered ID.\n";
    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }
}

