package com.itmolabs.commands.actions;

import com.itmolabs.application.CollectionManager;

public class InfoAction {

    private final CollectionManager collectionManager;

    public InfoAction(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public String doAction() {
        return String.format("%-32s", "Type of collection:")               +
                collectionManager.getCollection().getClass()        + "\n" +
                String.format("%-32s", "Collection initialization time:")   +
                collectionManager.getCollectionInitializationTime() + "\n" +
                String.format("%-32s", "Size of collection:")               +
                collectionManager.getCollection().size()            + "\n";
    }

}
