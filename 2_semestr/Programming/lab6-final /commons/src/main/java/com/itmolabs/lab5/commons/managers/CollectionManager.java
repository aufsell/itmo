package com.itmolabs.lab5.commons.managers;

import com.itmolabs.lab5.model.Coordinates;
import com.itmolabs.lab5.model.ticket.Ticket;
import com.itmolabs.lab5.model.venue.Venue;

import java.time.LocalDate;
import java.util.*;

public final class CollectionManager {

    private final LocalDate initializationDate;

    private Map<Integer, Ticket> collection;

    private String path;

    public CollectionManager(String pathToCollection) {
        this.initializationDate = LocalDate.now();

        if (FileManager.validateFile(pathToCollection)) {
            Map<Integer, Ticket> validatedCollection = validateCollection(
                    new FileManager().downloadCollection(pathToCollection)
            );

            System.out.println("Collection has been downloaded!");
            System.out.println(validatedCollection.size() + " elements has been downloaded.");

            this.collection = validatedCollection;
            this.path = pathToCollection;
        }

        else {
            System.out.println("Problems with file. It must be a file with correct path and You must have permission " +
                    "for read, write it.\n " +
                    "Check and restart program.");
            System.exit(0);
        }
    }

    public void clearCollection() {
        collection.clear();
    }

    private static final int MAX_Y_HEIGHT = 136;

    private boolean validateElement(final Ticket ticket) {
        if (ticket == null) return false;

        final String name = ticket.getName();

        if (name == null || name.isEmpty()) return false;

        final Coordinates coordinates = ticket.getCoordinates();

        if (coordinates == null) return false;

        final float discount = ticket.getDiscount();

        if (discount <= 0 || discount > 100) return false;

        final Venue venue = ticket.getVenue();

        if (venue == null) return false;

        final int venueIdentifier = venue.getId();

        if (venueIdentifier <= 0) return false;

        return ticket.getId() > 0
                && coordinates.getY() <= MAX_Y_HEIGHT
                && ticket.getCreationDate() != null
                && ticket.getPrice() > 0
                && venue.getName() != null
                && !venue.getName().isEmpty()
                && venue.getCapacity() > 0;
    }

     private Map<Integer, Ticket> validateCollection(Map<Integer, Ticket> original) {
        Map<Integer, Ticket> validated = new TreeMap<>();

        original.entrySet().stream()
                .filter((entry) -> {
                    if (!Objects.equals(entry.getValue().getId(), entry.getKey())) return false;

                    return validateElement(entry.getValue());
                }).forEach(entry -> validated.put(entry.getKey(), entry.getValue()));

        return validated;
     }

    public Map<Integer, Ticket> getCollection() {
        return collection;
    }

    public void setCollection(final Map<Integer, Ticket> collection) {
        this.collection = collection;
    }

    public LocalDate getInitializationDate() {
        return initializationDate;
    }

    public String getPathToCollection() {
        return path;
    }

}
