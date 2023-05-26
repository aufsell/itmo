package com.itmolabs.commands.actions;

import com.itmolabs.application.CollectionManager;

import java.util.Map;

public class HelpAction {

    private CollectionManager collectionManager;

    public HelpAction(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public String doAction() {
        Map<String, String> tutorial = collectionManager.getTutorial();
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String> entry : tutorial.entrySet()) {
            String key = String.format("%-43s", entry.getKey());
            String value = String.format(" : %1$s", entry.getValue());
            result.append(key).append(value).append("\n");
        }
        result.append("\n").append("ATTENTION. {element} syntax means, that user input required after " +
                "command running;").append("\n");
        result.append("ATTENTION. [update id]-like syntax means, that you should type [update] and type ID of" +
                " item for updating after it.").append("\n");
        return result.toString();
    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }
}
