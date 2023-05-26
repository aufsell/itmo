package com.itmolabs.commands.actions;

import com.itmolabs.application.CollectionManager;
import com.itmolabs.application.IDGenerator;
import com.itmolabs.model.Ticket;

import java.util.Map;

public class RemoveKeyAction {

    private CollectionManager collectionManager;

    public RemoveKeyAction(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public String doAction(Integer key) {
        Map<Integer, Ticket> collection = this.collectionManager.getCollection();
        for (Map.Entry<Integer, Ticket> entry : collection.entrySet()) {
            if (entry.getKey().equals(key)) {
                collection.remove(entry.getKey());
                IDGenerator.removeId(entry.getKey());
                return "Element has been removed!";
            }
        }
        return "Element with entered key doesn't exists and that's why it is hasn't been deleted!";
    }

}
