package com.itmolabs.lab5.commons.managers;

import com.itmolabs.lab5.model.Coordinates;
import com.itmolabs.lab5.model.ticket.Ticket;
import com.itmolabs.lab5.model.venue.Venue;

import java.time.LocalDate;
import java.util.*;

public final class CollectionManager {

//======================= INITIALIZATION DATE START ====================================================
    private final LocalDate initializationDate;

    public LocalDate getInitializationDate() {
        return initializationDate;
    }

//======================= INITIALIZATION DATE END ====================================================

//============================= PATH START ===================================================
    private String path;

    public String getPathToCollection() {
        return path;
    }

//============================ PATH END ===================================================

    public CollectionManager(
            final String pathToCollection
    ) {
        this.initializationDate = LocalDate.now();

        if (FileManager.validateFile(pathToCollection)) {
            Map<Integer, Ticket> tickets = validate(
                    new FileManager().downloadCollection(pathToCollection)
            );

            System.out.println("Collection has been downloaded!");
            System.out.println(tickets.size() + " elements has been downloaded.");

            this.collection = tickets;
            this.path = pathToCollection;
        }

        else {
            System.out.println("Problems with file. It must be a file with correct path and You must have permission " +
                    "for read, write it.\n " +
                    "Check and restart program.");
            System.exit(0);
        }
    }

//======================= COLLECTIONS START ==================================================
    //======================== FIELDS =====================================================
    private Map<Integer, Ticket> collection;
    //======================== FIELDS =====================================================

    private static final int MAX_Y_HEIGHT = 136;

    /**
     * Валидация элемента коллекции
     *
     * @param ticket - элемент коллекции
     *
     * @return валидный элемент коллекции
     */
    private boolean validateElement(
            final Ticket ticket
    ) {
        if (ticket == null)
            return false;

        final String name = ticket.getName();

        if (name == null || name.isEmpty())
            return false;

        final Coordinates coordinates = ticket.getCoordinates();

        if (coordinates == null)
            return false;

        final float discount = ticket.getDiscount();

        if (discount <= 0 || discount > 100)
            return false;

        final Venue venue = ticket.getVenue();

        if (venue == null)
            return false;

        final int venueIdentifier = venue.getId();

        if (venueIdentifier <= 0)
            return false;

        return ticket.getId() > 0
                && coordinates.getY() <= MAX_Y_HEIGHT
                && ticket.getCreationDate() != null
                && ticket.getPrice() > 0
                && venue.getName() != null
                && !venue.getName().isEmpty()
                && venue.getCapacity() > 0;
    }

    /**
     * Валидация коллекции
     *
     * @param value - коллекция
     *
     * @return валидная коллекция
     */
    private Map<Integer, Ticket> validate(
            final Map<Integer, Ticket> value
    ) {
        return value.values()
                .stream()
                .filter(this::validateElement)
                .collect(HashMap::new, (m, v) -> m.put(v.getId(), v), HashMap::putAll);
    }

    /**
     * Получить коллекцию элементов типа Ticket и с ключем Integer
     */
    public Map<Integer, Ticket> getCollection() {
        return collection;
    }

    /**
     * Очистить коллекцию
     */
    public void clearCollection() {
        collection.clear();
    }

    /**
     * Установить коллекцию филду
     *
     * @param collection - коллекция
     */
    public void setCollection(final Map<Integer, Ticket> collection) {
        this.collection = collection;
    }

//======================= COLLECTIONS END ==================================================
}
