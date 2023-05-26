package com.itmolabs.commands.actions;

import com.itmolabs.application.CollectionManager;
import com.itmolabs.model.Ticket;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class ShowAction {

    private final CollectionManager collectionManager;

    public ShowAction(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public String doAction() {
        StringBuilder stringBuilder = new StringBuilder();
        Map<Integer, Ticket> collectionCopy = collectionManager.getCollection();
        stringBuilder.append("Collection content:").append("\n");
        for (Map.Entry<Integer, Ticket>  entry: collectionCopy.entrySet()) {
            stringBuilder.append(entry.getKey()).append(" : ").append(entry.getValue().toString()).append("\n");
        }
        return stringBuilder.toString();
    }

}