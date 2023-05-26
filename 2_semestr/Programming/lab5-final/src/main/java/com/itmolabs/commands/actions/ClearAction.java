package com.itmolabs.commands.actions;

import com.itmolabs.application.CollectionManager;
import com.itmolabs.application.IDGenerator;

public class ClearAction {

    private final CollectionManager collectionManager;

    public ClearAction(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public String doAction() {
        this.collectionManager.getCollection().clear();
        IDGenerator.removeAllIds();
        return "Collection has been cleaned!\n";
    }


}
