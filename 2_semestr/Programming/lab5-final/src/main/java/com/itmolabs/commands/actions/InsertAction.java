package com.itmolabs.commands.actions;

import com.itmolabs.application.CollectionManager;
import com.itmolabs.application.IDGenerator;
import com.itmolabs.model.Ticket;

public class InsertAction {

    private final CollectionManager collectionManager;

    public InsertAction(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public String doAction(Integer key, Ticket ticket) {
        if (IDGenerator.checkIfIDUnique(key)) {
            ticket.setId(key);
            this.collectionManager.getCollection().put(key, ticket);
            IDGenerator.saveId(ticket.getId());
            return "Element has been added!\n";
        } else {
            return "Key must be unique. Check collection items and try again.";
        }
    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }

}
