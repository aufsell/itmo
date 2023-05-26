package com.itmolabs.commands.actions;

import com.itmolabs.application.CollectionManager;
import com.itmolabs.model.Ticket;
import com.itmolabs.model.TicketType;

import java.util.Map;

public class FilterByTypeAction {

    private CollectionManager collectionManager;

    public FilterByTypeAction(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public String doAction(TicketType ticketType) {
        Map<Integer, Ticket> collection = this.collectionManager.getCollection();
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Integer, Ticket> entry : collection.entrySet()) {
            if (entry.getValue().getType().equals(ticketType)) {
                stringBuilder.append(entry.getKey()).append(" : ").append(entry.getKey()).append("\n");
            }
        }
        return stringBuilder.toString();
    }

}
