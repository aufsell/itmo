package com.itmolabs.commands.actions;

import com.itmolabs.application.CollectionManager;
import com.itmolabs.application.IDGenerator;
import com.itmolabs.model.Ticket;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class RemoveAllByDiscountAction {

    private CollectionManager collectionManager;

    public RemoveAllByDiscountAction(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public String execute(Float discount) {
        Map<Integer, Ticket> collection = this.collectionManager.getCollection();
        Iterator<Map.Entry<Integer, Ticket>> iterator = collection.entrySet().iterator();
        int counter = 0;
        while (iterator.hasNext()) {
            Map.Entry<Integer, Ticket> entry = iterator.next();
            if (Objects.equals(entry.getValue().getDiscount(), discount)) {
                iterator.remove();
                counter++;
            }
        }
        return "Command executed. Removed " + counter + " elements.";
    }

}
