package com.itmolabs.commands.actions;

import com.itmolabs.application.CollectionManager;
import com.itmolabs.model.Ticket;

import java.util.Map;
import java.util.TreeMap;

public class ReplaceIfGreaterKeyAction {

    private CollectionManager collectionManager;

    public ReplaceIfGreaterKeyAction(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public String doAction(Integer key, Ticket newTicket) {
        newTicket.setId(key);
        Map<Integer, Ticket> collection = this.collectionManager.getCollection();
        boolean found = false;
        for (Map.Entry<Integer, Ticket> entry : collection.entrySet()) {
            if (entry.getKey().equals(key)) {
                found = true;
                if (newTicket.compareTo(entry.getValue()) > 0) {
                    collection.put(entry.getKey(), newTicket);
                    return "Element has been replaced.";
                }
            }
        }
        if (found) {
            return "Element hasn't been updated because it's not greater than given";
        }
        return "Element has not been updated because given key is not exists.";
    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }

}

