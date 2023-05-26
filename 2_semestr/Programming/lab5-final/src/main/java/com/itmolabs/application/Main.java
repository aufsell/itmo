package com.itmolabs.application;

import com.itmolabs.commands.CommandInvoker;
import com.itmolabs.model.Ticket;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            System.out.println("Lab work #5 implementation has been started!");
            CollectionManager collectionManager = CollectionManager.getInstance(System.getenv("FILEPATH"));
            new CommandInvoker(collectionManager).run();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("System variable FILEPATH not found. Try to define it and try again.");
        }
    }
    


    private static String toCsvString(Ticket ticket) {
        return ticket.getId() + "," + ticket.getName() + "," + ticket.getCoordinates().getX() + "," +
                ticket.getCoordinates().getY() + "," + ticket.getCreationDate().toString() + "," + ticket.getPrice() +
                "," + ticket.getDiscount() + "," + ticket.getType() + "," + ticket.getVenue().getId() + "," +
                ticket.getVenue().getName() + "," + ticket.getVenue().getCapacity() + "," + ticket.getVenue().getType();
    }

}
