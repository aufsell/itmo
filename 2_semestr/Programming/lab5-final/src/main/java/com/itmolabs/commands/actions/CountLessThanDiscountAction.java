package com.itmolabs.commands.actions;

import com.itmolabs.application.CollectionManager;
import com.itmolabs.model.Ticket;

import java.util.Map;

public class CountLessThanDiscountAction {

    private CollectionManager collectionManager;

    public CountLessThanDiscountAction(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public String execute(Float discount) {
        Map<Integer, Ticket> collection = this.collectionManager.getCollection();
        int counter = 0;
        for (Map.Entry<Integer, Ticket> entry : collection.entrySet()) {
            if (entry.getValue().getDiscount() < discount) {
                counter++;
            }
        }
        return "Command executed. Counted " + counter +  " items";
    }

}
