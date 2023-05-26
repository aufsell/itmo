package com.itmolabs.application;

import com.itmolabs.model.Ticket;

import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CollectionManager {

    private static CollectionManager INSTANCE;
    private Map<Integer, Ticket> collection;
    private LocalDate collectionInitializationTime;
    private final Map<String, String> tutorial;
    private String pathToCollection;

    public static CollectionManager getInstance(String pathToCollection) {
        if (INSTANCE == null) {
            INSTANCE = new CollectionManager(pathToCollection);
        }
        return INSTANCE;
    }

    private CollectionManager(String pathToCollection) {
        this.collectionInitializationTime = LocalDate.now();
        tutorial = new HashMap<>();
        tutorial.put("help", "get help on alternative commands");
        tutorial.put("info", "print all collection items into the string representation");
        tutorial.put("insert key {element}", "add new item to collection with entered key");
        tutorial.put("update id {element}", "update the value of the collection element " +
                "whose ID matches the given one");
        tutorial.put("remove_key key", "remove an element from the collection by its key");
        tutorial.put("clear", "remove all items from collection");
        tutorial.put("save", "save collection to file");
        tutorial.put("execute_script filename", "read and execute script from given file. " +
                "The same views are found in the script as in the interactive mode");
        tutorial.put("exit", "exit the program (without closing in the file)");
        tutorial.put("replace_if_greater key {element}", "replace the element by key " +
                "if entered item is greater than old");
        tutorial.put("replace_if_lower key {element}", "replace the element by key " +
                "if entered item is lower than old");
        tutorial.put("remove_lower_key key", "remove from the collection all elements " +
                "keys of which are strictly lower than the given key");

        tutorial.put("remove_all_by_discount discount", "remove from the collection all elements, field" +
                "discount of which is equal");
        tutorial.put("count_less_than_discount discount", "count items, field discount of which is lower than a given");
        tutorial.put("filter_by_type type", "print elements, filed type of which is greater than a given");
        if (FileManager.validateFile(pathToCollection)) {
            Map<Integer, Ticket> rawCollection = new FileManager().downloadCollection(pathToCollection);
            Map<Integer, Ticket> validatedCollection = validateCollection(rawCollection);
            this.collection = validatedCollection;
            System.out.println("Collection has been downloaded!");
            System.out.println(validatedCollection.size() + " elements has been downloaded.");
            this.pathToCollection = pathToCollection;
        } else {
            System.out.println("Problems with file. It must be a file with correct path and You must have permission " +
                    "for read, write it.\n " +
                    "Check and restart program.");
            System.exit(1);
        }

    }

    private boolean validateElement(Ticket ticket) {
        if (ticket == null) return false;
        if (ticket.getId() == null) return false;
        if (!IDGenerator.checkIfIDUnique(ticket.getId())) return false;
        if (ticket.getId() <= 0) return false;
        if (ticket.getName() == null) return false;
        if (ticket.getName().isEmpty()) return false;
        if (ticket.getCoordinates() == null) return false;
        if (ticket.getCoordinates().getX() == null) return false;
        if (ticket.getCoordinates().getY() == null) return false;
        if (ticket.getCoordinates().getY() > 136) return false;
        if (ticket.getCreationDate() == null) return false;
        if (ticket.getPrice() == null) return false;
        if (ticket.getPrice() <= 0) return false;
        if (ticket.getDiscount() == null) return false;
        if (ticket.getDiscount() > 100 || ticket.getDiscount() <= 0) return false;
        if (ticket.getVenue() == null) return false;
        if (!IDGenerator.checkIfIDUnique(ticket.getVenue().getId())) return false;
        if (ticket.getVenue().getId() <= 0) return false;
        if (ticket.getVenue().getName() == null) return false;
        if (ticket.getVenue().getName().isEmpty()) return false;
        if (ticket.getVenue().getCapacity() == null) return false;
        if (ticket.getVenue().getCapacity() <= 0) return false;
        return true;
     }

     private Map<Integer, Ticket> validateCollection(Map<Integer, Ticket> original) {
        Map<Integer, Ticket> validated = new TreeMap<>();
        for (Map.Entry<Integer, Ticket> entry : original.entrySet()) {
            if (validateElement(entry.getValue())) {
                entry.getValue().setId(entry.getKey());
                validated.put(entry.getKey(), entry.getValue());
                IDGenerator.saveId(entry.getKey());

            }
        }
        return validated;
     }

    public Map<Integer, Ticket> getCollection() {
        return collection;
    }

    public void setCollection(Map<Integer, Ticket> collection) {
        this.collection = collection;
    }

    public Map<String, String> getTutorial() {
        return tutorial;
    }

    public LocalDate getCollectionInitializationTime() {
        return collectionInitializationTime;
    }

    public String getPathToCollection() {
        return pathToCollection;
    }

}
