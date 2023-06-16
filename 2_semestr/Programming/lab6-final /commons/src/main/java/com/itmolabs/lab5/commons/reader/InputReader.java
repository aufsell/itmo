package com.itmolabs.lab5.commons.reader;

import com.itmolabs.lab5.commons.generator.IdentifierGenerator;
import com.itmolabs.lab5.model.Coordinates;
import com.itmolabs.lab5.model.ticket.Ticket;
import com.itmolabs.lab5.model.ticket.TicketType;
import com.itmolabs.lab5.model.venue.Venue;
import com.itmolabs.lab5.model.venue.VenueType;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputReader {

    public String receiveName() {
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);

                System.out.print("Enter a name: ");
                String name = scanner.nextLine();

                if (name.isEmpty()) {
                    System.out.println("This value cannot be empty. Try again");
                    continue;
                }

                return name;
            } catch (final InputMismatchException e) {
                System.out.println("This value must be string. Try again.");
            } catch (final NoSuchElementException e) {
                System.out.println("Program was stopped successfully.");
                System.exit(0);
            } catch (final NumberFormatException e) {
                System.out.println("Incorrect, try again");
            }
        }
    }


    public float receiveX() {
        for ( ; ; ) {
            try {
                System.out.print("Enter X coordinate: ");
                Scanner scanner = new Scanner(System.in);
                String s = scanner.nextLine();

                if (s.isEmpty()) System.out.println("Value cannot be null. Try again.");

                return Float.parseFloat(s);
            } catch (final InputMismatchException e) {
                System.out.println("This value must be a float-type number. Try again. ");
            } catch (final NoSuchElementException e) {
                System.out.println("Program was stopped successfully. ");
                System.exit(0);
            } catch (final NumberFormatException e) {
                System.out.println("Incorrect, try again");
            }
        }
    }

    public Float receiveY() {
        for ( ; ; ) {
            try {
                System.out.print("Enter Y coordinate. This value cannot be empty: ");

                Scanner scanner = new Scanner(System.in);
                String s = scanner.nextLine();

                if (s.isEmpty()) System.out.println("Value cannot be null. Try again.");

                float y = Float.parseFloat(s);

                if (y > 136) {
                    System.out.println("Max value is 136.");
                    continue;
                }

                return y;
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be a float-type number. Try again. ");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully.");
                System.exit(0);
            }catch (NumberFormatException e) {
                System.out.println("Incorrect, try again");
            }
        }
    }

    public Coordinates receiveCoordinates() {
        return new Coordinates(receiveX(), receiveY());
    }

    public Long receivePrice() {
        for ( ; ; ) {
            try {
                System.out.print("Enter a price: ");

                Scanner scanner = new Scanner(System.in);
                String priceString = scanner.nextLine();

                if (priceString.isEmpty()) return null;
                else {
                    long price = Long.parseLong(priceString);
                    if (price <= 0) {
                        System.out.println("Value must be positive. Try again.");
                        continue;
                    }
                    return price;
                }
            } catch (final InputMismatchException e) {
                System.out.println("This value must be a long-type number. Try again. ");
            } catch (final NoSuchElementException e) {
                System.out.println("Program was stopped successfully. ");
                System.exit(0);
            }catch (final NumberFormatException e) {
                System.out.println("Incorrect, try again");
            }
        }
    }

    public Float receiveDiscount() {
        for ( ; ; ) {
            try {
                System.out.print("Enter a discount: ");

                Scanner scanner = new Scanner(System.in);
                String discountString = scanner.nextLine();

                if (discountString.isEmpty()) return null;
                else {
                    float discount = Float.parseFloat(discountString);

                    if (discount <= 0 || discount > 100) {
                        System.out.println("Value must be from (0; 100]. Try again.");
                        continue;
                    }
                    return discount;
                }
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be a float-type number. Try again. ");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully. ");
                System.exit(0);
            } catch (NumberFormatException e) {
                System.out.println("Incorrect, try again");
            }
        }
    }

    public TicketType receiveTicketType() {
        for ( ; ; ) {
            try {
                System.out.println("Choose variant of ticket type. Enter color or the number corresponding " +
                        "to the desired option.");
                System.out.print("Variants: \n1. VIP; \n2. USUAL; \n3. BUDGETARY.\nEnter your variant here: ");
                Scanner scanner = new Scanner(System.in);
                String choose = scanner.nextLine().toUpperCase(Locale.ROOT);

                switch (choose) {
                    case "" -> {
                        return null;
                    }
                    case "1", "VIP" -> {
                        return TicketType.VIP;
                    }
                    case "2", "USUAL" -> {
                        return TicketType.USUAL;
                    }
                    case "3", "BUDGETARY" -> {
                        return TicketType.BUDGETARY;
                    }
                    default -> System.out.println("You should to choose the type from list or it's number. Try again.");
                }

            } catch (final InputMismatchException e) {
                System.out.println("This value must be a string with type or it's number. Try again.");
            } catch (final NoSuchElementException e) {
                System.out.println("Program was stopped successfully.");
                System.exit(0);
            } catch (final NumberFormatException e) {
                System.out.println("Incorrect, try again");
            }
        }
    }

    public VenueType receiveVenueType() {
        for ( ; ; ) {
            try {
                System.out.println("Choose variant of venue type. Enter color or the number corresponding " +
                        "to the desired option.");
                System.out.print("Variants: \n1. LOFT; \n2. THEATRE; \n3. STADIUM.\nEnter your variant here: ");

                Scanner scanner = new Scanner(System.in);
                String choose = scanner.nextLine().toUpperCase(Locale.ROOT);

                switch (choose) {
                    case "" -> {
                        return null;
                    }
                    case "1", "LOFT" -> {
                        return VenueType.LOFT;
                    }
                    case "2", "THEATRE" -> {
                        return VenueType.THEATRE;
                    }
                    case "3", "STADIUM" -> {
                        return VenueType.STADIUM;
                    }
                    default -> System.out.println("You should to choose the type from list or it's number. Try again.");
                }

            } catch (final InputMismatchException e) {
                System.out.println("This value must be a string with type or it's number. Try again.");
            } catch (final NoSuchElementException e) {
                System.out.println("Program was stopped successfully.");
                System.exit(0);
            } catch (final NumberFormatException e) {
                System.out.println("Incorrect, try again");
            }
        }
    }

    public int receiveCapacity() {
        for ( ; ; ) {
            try {
                System.out.print("Enter a capacity: ");

                Scanner scanner = new Scanner(System.in);
                String capacityString = scanner.nextLine();

                if (capacityString.isEmpty())
                    System.out.println("Value cannot be null. Try again.");
                else {
                    int  capacity = Integer.parseInt(capacityString);

                    if (capacity <= 0) {
                        System.out.println("Value must be positive. Try again.");
                        continue;
                    }
                    return capacity;
                }
            } catch (final InputMismatchException e) {
                System.out.println("This value must be a integer-type number. Try again. ");
            } catch (final NoSuchElementException e) {
                System.out.println("Program was stopped successfully. ");
                System.exit(0);
            } catch (final NumberFormatException e) {
                System.out.println("Incorrect, try again");
            }

        }
    }

    public Venue receiveVenue(final int id) {
        return new Venue(id, receiveName(), receiveCapacity(), receiveVenueType());
    }

    public Ticket receiveTicket() {
        final int id = IdentifierGenerator.generateID();

        Venue venue = receiveVenue(id);

        return new Ticket(id, receiveName(), receiveCoordinates(),
                LocalDate.now(), receivePrice(), receiveDiscount(), receiveTicketType(), venue);
    }
}
