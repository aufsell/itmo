package com.itmolabs.lab5.model.ticket;

import com.itmolabs.lab5.model.Coordinates;
import com.itmolabs.lab5.model.venue.Venue;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Ticket implements Comparable<Ticket>, Serializable {
    private int id; //Поле не может быть null, Значение поля должно быть больше 0,
    // Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    private int owner;

    private String name;

    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    private long price; //Поле может быть null, Значение поля должно быть больше 0
    private float discount; //Поле может быть null, Значение поля должно быть больше 0, Максимальное значение поля: 100

    private TicketType type; //Поле может быть null
    private Venue venue; //Поле не может быть null

    public Ticket() {
    }

    public Ticket(
            int id,
            int owner,
            String name,
            Coordinates coordinates,
            LocalDateTime creationDate,
            long price,
            float discount,
            TicketType type,
            Venue venue
    ) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.price = price;
        this.discount = discount;
        this.type = type;
        this.venue = venue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id) && Objects.equals(name, ticket.name) &&
                Objects.equals(coordinates, ticket.coordinates)
                && Objects.equals(creationDate, ticket.creationDate)
                && Objects.equals(price, ticket.price)
                && Objects.equals(discount, ticket.discount)
                && type == ticket.type && Objects.equals(venue, ticket.venue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, price, discount, type, venue);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", owner='" + owner + "'" +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                ", price=" + price +
                ", discount=" + discount +
                ", type=" + type +
                ", venue=" + venue.toString() +
                '}';
    }

    @Override
    public int compareTo(
            final Ticket another
    ) {
        int m = 0;

        try {
            m = (int) (getPrice() - another.getPrice());
        } catch (final NullPointerException e) {
            System.out.println("price not be null if you use this command");
        }

        return m;
    }
}