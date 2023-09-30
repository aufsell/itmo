package com.itmolabs.lab5.database.impl;

import com.itmolabs.lab5.database.Database;
import com.itmolabs.lab5.model.Coordinates;
import com.itmolabs.lab5.model.ticket.Ticket;
import com.itmolabs.lab5.model.ticket.TicketType;
import com.itmolabs.lab5.model.venue.Venue;
import com.itmolabs.lab5.model.venue.VenueType;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public final class CollectionSql {

    private final Database database;

    public CollectionSql(
            final Database database
    ) {
        this.database = database;

        createTable();
    }

    private static final String CREATE_SEQUENCE
            = "CREATE SEQUENCE IF NOT EXISTS ticket_id_seq START 1 INCREMENT 1;";
    private static final String SELECT_NEXT_VAL_SEQUENCE
            = "SELECT nextval('ticket_id_seq');";

    private static final String CREATE_COORDINATES_TABLE
            = "CREATE TABLE IF NOT EXISTS Coordinates ("
            + "id BIGSERIAL PRIMARY KEY,"
            + "x INT NOT NULL,"
            + "y INT NOT NULL);";
    private static final String CREATE_TICKET_TABLE
            = "CREATE TABLE IF NOT EXISTS Tickets ("
            + "id BIGSERIAL PRIMARY KEY,"
            + "owner INT NOT NULL REFERENCES Users(id) ON DELETE CASCADE,"
            + "coordinates INT NOT NULL REFERENCES Coordinates(id) ON DELETE CASCADE,"
            + "venue INT NOT NULL REFERENCES Venues(id) ON DELETE CASCADE,"
            + "creation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
            + "price BIGINT NULL,"
            + "discount FLOAT NULL,"
            + "type VARCHAR(128) NULL"
            + ");";
    private static final String CREATE_VENUE_TABLE
            = "CREATE TABLE IF NOT EXISTS Venues ("
            + "id BIGSERIAL PRIMARY KEY,"
            + "name VARCHAR(128) NOT NULL,"
            + "capacity INT NOT NULL,"
            + "type VARCHAR(128) NULL);";

    private static final String INSERT_INTO_COORDINATES
            = "INSERT INTO Coordinates (id, x, y) VALUES (?, ?, ?) RETURNING id;";
    private static final String INSERT_INTO_VENUE
            = "INSERT INTO Venues (id, name, capacity, type)"
            + "VALUES (?, ?, ?, ?) RETURNING id;";
    private static final String INSERT_INTO_TICKET
            = "INSERT INTO Tickets " +
            "(id, owner, coordinates, venue, creation_date, price, discount, type)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING id;";

    private static final String DELETE_TICKET_BY_ID
            = "DELETE FROM Tickets WHERE id = ?;";
    private static final String DELETE_COORDINATES_BY_ID
            = "DELETE FROM Coordinates WHERE id = ?;";
    private static final String DELETE_VENUE_BY_ID
            = "DELETE FROM Venues WHERE id = ?;";

    private static final String SELECT_COORDINATES_BY_ID
            = "SELECT * FROM Coordinates WHERE id = ?;";
    private static final String SELECT_VENUE_BY_ID
            = "SELECT * FROM Venues WHERE id = ?;";
    private static final String SELECT_ALL_TICKETS
            = "SELECT * FROM Tickets LEFT JOIN Coordinates ON Tickets.coordinates = Coordinates.id " +
            "LEFT JOIN Venues ON Tickets.venue = Venues.id;";
    private static final String SELECT_TICKET_BY_ID
            = "SELECT * FROM Tickets WHERE id = ?;";

    private static final String UPDATE_TICKET_BY_ID
            = "UPDATE Tickets SET owner = ?, coordinates = ?, venue = ?, creation_date = ?, price = ?, discount = ?, type = ? WHERE id = ?;";
    private static final String UPDATE_VENUE_BY_ID
            = "UPDATE Venues SET name = ?, capacity =?, type = ? WHERE id = ?";
    private static final String UPDATE_COORDINATES_BY_ID
            = "UPDATE Coordinates SET x = ?, y = ? WHERE id = ?";

    private final Map<Integer, Ticket> tickets
            = Collections.synchronizedMap(new HashMap<>());

    public void createTable() {
        database.execute(CREATE_SEQUENCE)
                .thenApply(v -> {
                    System.out.println("Successfully created sequence!");

                    return v;
                }).exceptionally(e -> {
                    e.printStackTrace();

                    return null;
                });

        database.execute(CREATE_COORDINATES_TABLE)
                .thenApply(v -> {
                    System.out.println("Successfully created table Coordinates!");

                    return v;
                }).exceptionally(e -> {
                    e.printStackTrace();

                    return null;
                });

        database.execute(CREATE_VENUE_TABLE)
                .thenApply(v -> {
                    System.out.println("Successfully created table Venues!");

                    return v;
                }).exceptionally(e -> {
                    System.out.println("Failed to create table Venues!");

                    return null;
                });

        database.execute(CREATE_TICKET_TABLE)
                .thenApply(v -> {
                    System.out.println("Successfully created table Tickets!");

                    loadTickets();

                    return v;
                }).exceptionally(e -> {
                    e.printStackTrace();
                    System.out.println("Failed to create table Tickets!");

                    return null;
                });
    }

    public void loadTickets() {
        synchronized (tickets) {
            tickets.clear();

            for (final var ticket : getTickets()
                    .join()
            )
                tickets.put(ticket.getId(), ticket);

            System.out.println("Successfully loaded tickets!");
        }
    }

    public void saveTickets() {
        tickets.values().forEach(ticket -> {
            if (isTicketExists(ticket.getId())) updateTicket(ticket).join();
            else insertTicket(ticket).join();
        });
    }

    public boolean isTicketExists(
            final int id
    ) {
        try (final var statement = database.getConnection().prepareStatement(
                SELECT_TICKET_BY_ID
        )) {
            statement.setInt(1, id);

            final var resultSet = statement.executeQuery();

            return resultSet.next();
        } catch (final Exception e) {
            throw new RuntimeException("Failed to check ticket!", e);
        }
//        return CompletableFuture.supplyAsync(() -> {
//        }).join();
    }

    public Coordinates getCoordinates(
            final int id
    ) {
        return CompletableFuture.supplyAsync(() -> {
            try (final var statement = database.getConnection().prepareStatement(
                    SELECT_COORDINATES_BY_ID
            )) {
                statement.setInt(1, id);

                final var resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    return new Coordinates(
                            resultSet.getInt("id"),
                            resultSet.getInt("x"), resultSet.getInt("y")
                    );
                }
            } catch (final Exception e) {
                throw new RuntimeException("Failed to get coordinates!", e);
            }

            return null;
        }).join();
    }

    public Venue getVenue(
            final int id
    ) {
        return CompletableFuture.supplyAsync(() -> {
            try (final var statement = database.getConnection().prepareStatement(
                    SELECT_VENUE_BY_ID
            )) {
                statement.setInt(1, id);

                final var resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    return new Venue(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getInt("capacity"),
                            VenueType.valueOf(resultSet.getString("type"))
                    );
                }
            } catch (final Exception e) {
                throw new RuntimeException("Failed to get venue!", e);
            }

            return null;
        }).join();
    }

    public Ticket getTicket(
            final int id
    ) {
        synchronized (tickets) {
            if (tickets.containsKey(id))
                return tickets.get(id);
        }

        return CompletableFuture.supplyAsync(() -> {
            try (final var statement = database.getConnection().prepareStatement(
                    SELECT_ALL_TICKETS
            )) {
                statement.setInt(1, id);

                final var resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int ticketId = resultSet.getInt("id");

                    return new Ticket(
                            ticketId, resultSet.getInt("owner"), resultSet.getString("name"),
                            getCoordinates(ticketId), resultSet.getTimestamp("creation_date")
                            .toLocalDateTime(),
                            resultSet.getLong("price"), resultSet.getFloat("discount"),
                            TicketType.of(resultSet.getString("type")), getVenue(ticketId)
                    );
                }
            } catch (final Exception e) {
                throw new RuntimeException("Failed to get ticket!", e);
            }

            return null;
        }).join();
    }

    public void insertCoordinates(
            final Coordinates coordinates
    ) {
        try (final var statement = database.getConnection().prepareStatement(
                INSERT_INTO_COORDINATES
        )) {
            statement.setInt(1, coordinates.getId());
            statement.setFloat(2, coordinates.getX());
            statement.setFloat(3, coordinates.getY());

            statement.execute();
        } catch (final Exception e) {
            throw new RuntimeException("Failed to insert coordinates!", e);
        }
    }

    public void insertVenue(
            final Venue venue
    ) {
        try (final var statement = database.getConnection().prepareStatement(
                INSERT_INTO_VENUE
        )) {
            statement.setInt(1, venue.getId());
            statement.setString(2, venue.getName());
            statement.setInt(3, venue.getCapacity());
            statement.setString(4, VenueType.STADIUM.name());

            statement.execute();
        } catch (final Exception e) {
            throw new RuntimeException("Failed to insert venue!", e);
        }

    }

    public CompletableFuture<Void> insertTicket(
            final Ticket ticket
    ) {
        insertCoordinates(ticket.getCoordinates());
        insertVenue(ticket.getVenue());

        synchronized (tickets) {
            tickets.put(ticket.getId(), ticket);
        }

        return CompletableFuture.runAsync(() -> {
            try (final var statement = database.getConnection().prepareStatement(
                    INSERT_INTO_TICKET
            )) {
                statement.setInt(1, ticket.getId());
                statement.setInt(2, ticket.getOwner());
                statement.setInt(3, ticket.getId());
                statement.setInt(4, ticket.getId());
                statement.setTimestamp(5, Timestamp.valueOf(ticket.getCreationDate()));
                statement.setLong(6, ticket.getPrice());
                statement.setFloat(7, ticket.getDiscount());
                statement.setString(8, TicketType.VIP.name());

                statement.execute();
            } catch (final Exception e) {
                e.printStackTrace();
            }
        });
    }

    public CompletableFuture<Void> deleteTicket(
            final int id
    ) {
        synchronized (tickets) {
            tickets.remove(id);
        }

        deleteCoordinates(id);
        deleteVenue(id);

        return database.execute(DELETE_TICKET_BY_ID, id);
    }

    public CompletableFuture<Void> deleteCoordinates(
            final int id
    ) {
        return database.execute(DELETE_COORDINATES_BY_ID, id);
    }

    public CompletableFuture<Void> deleteVenue(
            final int id
    ) {
        return database.execute(DELETE_VENUE_BY_ID, id);
    }

    public CompletableFuture<Void> deleteTicket(
            final Ticket ticket
    ) {
        return deleteTicket(ticket.getId());
    }

    public CompletableFuture<Void> deleteCoordinates(
            final Coordinates coordinates
    ) {
        return database.execute(DELETE_COORDINATES_BY_ID, coordinates.getId());
    }

    public CompletableFuture<Void> deleteVenue(
            final Venue venue
    ) {
        return database.execute(DELETE_VENUE_BY_ID, venue.getId());
    }

    public CompletableFuture<Void> updateTicket(
            final Ticket ticket
    ) {
        synchronized (tickets) {
            tickets.replace(ticket.getId(), ticket);
        }

        return database.execute(UPDATE_TICKET_BY_ID,
                ticket.getOwner(), ticket.getCoordinates().getId(), ticket.getVenue().getId(),
                java.sql.Timestamp.valueOf(ticket.getCreationDate()), ticket.getPrice(),
                ticket.getDiscount(), ticket.getType().name(), ticket.getId());
    }

    public CompletableFuture<Void> updateVenue(
            final Venue venue
    ) {
        return database.execute(UPDATE_VENUE_BY_ID, venue.getName(), venue.getCapacity(), venue.getType().name(), venue.getId());
    }

    public CompletableFuture<Void> updateCoordinates(
            final Coordinates coordinates
    ) {
        return database.execute(UPDATE_COORDINATES_BY_ID, coordinates.getX(), coordinates.getY(), coordinates.getId());
    }

    public CompletableFuture<List<Ticket>> getTickets() {
        return CompletableFuture.supplyAsync(() -> {
            try (final var statement = database.getConnection().prepareStatement(
                    SELECT_ALL_TICKETS
            )) {
                final var resultSet = statement.executeQuery();

                final var ticketList = new ArrayList<Ticket>();

                while (resultSet.next()) {
                    int ticketId = resultSet.getInt("id");

                    ticketList.add(new Ticket(
                            ticketId, resultSet.getInt("owner"), resultSet.getString("name"),
                            getCoordinates(ticketId), resultSet.getTimestamp("creation_date")
                            .toLocalDateTime(),
                            resultSet.getLong("price"), resultSet.getFloat("discount"),
                            TicketType.of(resultSet.getString("type")), getVenue(ticketId)
                    ));
                }

                return ticketList;
            } catch (final Exception e) {
                e.printStackTrace();
            }

            return null;
        });
    }

    public int getNextId() {
        return CompletableFuture.supplyAsync(() -> {
            try (final var statement = database.getConnection().prepareStatement(
                    SELECT_NEXT_VAL_SEQUENCE
            )) {
                final var resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    return resultSet.getInt(1) - 1; // Из-за ограничений PostGRE, приходится так..
                }
            } catch (final Exception e) {
                throw new RuntimeException("Failed to get next id!", e);
            }

            return null;
        }).join();
    }

    public Map<Integer, Ticket> getCache() {
        return tickets;
    }
}
