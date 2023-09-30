package com.itmolabs.lab5.model.venue;

import java.io.Serializable;

public class Venue implements Serializable {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным,
    // Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private int capacity; //Поле не может быть null, Значение поля должно быть больше 0
    private VenueType type; //Поле может быть null

    public Venue() {}

    public Venue(final int id,
                 final String name,
                 final int capacity,
                 final VenueType type
    ) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(final int capacity) {
        this.capacity = capacity;
    }

    public VenueType getType() {
        return type;
    }

    public void setType(final VenueType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Venue{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                ", type=" + type +
                '}';
    }
}