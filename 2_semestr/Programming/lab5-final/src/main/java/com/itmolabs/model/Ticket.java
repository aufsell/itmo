package com.itmolabs.model;

import com.itmolabs.serializers.LocalDateAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.Objects;
@XmlType()
@XmlRootElement(name = "person")
public class Ticket implements Comparable<Ticket> {

    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0,
    // Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long price; //Поле может быть null, Значение поля должно быть больше 0
    private Float discount; //Поле может быть null, Значение поля должно быть больше 0, Максимальное значение поля: 100
    private TicketType type; //Поле может быть null
    private Venue venue; //Поле не может быть null

    public Ticket() {}

    public Ticket(Integer id, String name, Coordinates coordinates, LocalDate creationDate, Long price,
                  Float discount, TicketType type, Venue venue) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.price = price;
        this.discount = discount;
        this.type = type;
        this.venue = venue;
    }

    @XmlElement
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @XmlElement
    public LocalDate getCreationDate() {
        return creationDate;
    }

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    @XmlElement
    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @XmlElement
    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    @XmlElement
    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    @XmlElement
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
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", price=" + price +
                ", discount=" + discount +
                ", type=" + type +
                ", venue=" + venue +
                '}';
    }

    @Override
    public int compareTo(Ticket another) {
        Integer m = 0;
        try {
            m = (int) (this.getPrice() - another.getPrice());
        } catch (NullPointerException e) {
            System.out.println("price not be null if you use this command");
        }
        return m;
    }
}