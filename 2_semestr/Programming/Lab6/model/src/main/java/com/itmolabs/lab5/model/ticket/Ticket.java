package com.itmolabs.lab5.model.ticket;

import com.itmolabs.lab5.model.Coordinates;
import com.itmolabs.lab5.model.venue.Venue;
import com.itmolabs.lab5.adapter.LocalDateAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@XmlRootElement(name = "ticket")
public class Ticket implements Comparable<Ticket>, Serializable {
    private int id; //Поле не может быть null, Значение поля должно быть больше 0,
    // Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    private long price; //Поле может быть null, Значение поля должно быть больше 0
    private float discount; //Поле может быть null, Значение поля должно быть больше 0, Максимальное значение поля: 100

    private TicketType type; //Поле может быть null
    private Venue venue; //Поле не может быть null

    public Ticket() {}

    public Ticket(int id,
                  String name,
                  Coordinates coordinates,
                  LocalDate creationDate,
                  long price,
                  float discount,
                  TicketType type,
                  Venue venue) {
        this.id = id;
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

    @XmlElement
    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    @XmlElement
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public long getPrice() {
        return price;
    }

    @XmlElement
    public void setPrice(long price) {
        this.price = price;
    }

    public float getDiscount() {
        return discount;
    }

    @XmlElement
    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public TicketType getType() {
        return type;
    }

    @XmlElement
    public void setType(TicketType type) {
        this.type = type;
    }

    public Venue getVenue() {
        return venue;
    }

    @XmlElement
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
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", price=" + price +
                ", discount=" + discount +
                ", type=" + type +
                ", venue="  + venue.toString() +
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