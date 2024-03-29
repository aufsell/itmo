package com.itmolabs.lab5.model.ticket;

public enum TicketType {

    VIP,
    USUAL,
    BUDGETARY;

    public static final TicketType[] VALUES = values();

    public static TicketType of(final String name) {
        for (final TicketType type : VALUES) {
            if (name.toUpperCase().equals(type.name())) {
                return type;
            }
        }

        return null;
    }
}
