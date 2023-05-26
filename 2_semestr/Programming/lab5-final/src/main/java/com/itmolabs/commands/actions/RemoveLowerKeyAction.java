package com.itmolabs.commands.actions;

import com.itmolabs.application.CollectionManager;
import com.itmolabs.application.IDGenerator;
import com.itmolabs.model.Ticket;

import java.util.Map;
import java.util.TreeMap;

public class RemoveLowerKeyAction {

    private CollectionManager collectionManager;

    public RemoveLowerKeyAction(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public String doAction(Integer key) {
        Map<Integer, Ticket> collection = this.collectionManager.getCollection();
        Map<Integer, Ticket> collectionCopy = new TreeMap<>();
        int counter = 0;
        for (Map.Entry<Integer, Ticket> entry : collection.entrySet()) {
            if (entry.getKey() >= key) {
                collectionCopy.put(entry.getKey(), entry.getValue());
                IDGenerator.removeId(entry.getValue().getId());
                //collectionCopy.remove(entry.getKey());
                //IDGenerator.removeId(entry.getValue().getId());
            } else {
                counter++;
            }
        }
        this.collectionManager.setCollection(collectionCopy);
        return "Command executed. Removed " + counter + " elements";
    }

}
