package com.itmolabs.application;

import com.itmolabs.model.*;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputReader {

    private CollectionManager collectionManager;

    public InputReader(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

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
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be string. Try again.");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully.");
                System.exit(0);
            }catch (NumberFormatException e) {
                System.out.println("Incorrect, try again");
            }
        }
    }


    public Float receiveX() {
        for ( ; ; ) {
            try {
                System.out.print("Enter X coordinate: ");
                Scanner scanner = new Scanner(System.in);
                String xString = scanner.nextLine();
                if (xString.isEmpty()) {
                    System.out.println("Value cannot be null. Try again.");
                }
                return Float.parseFloat(xString);
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be a float-type number. Try again. ");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully. ");
                System.exit(0);
            }catch (NumberFormatException e) {
                System.out.println("Incorrect, try again");
            }
        }
    }

    public Float receiveY() {
        for ( ; ; ) {
            try {
                System.out.print("Enter Y coordinate. This value cannot be empty: ");
                Scanner scanner = new Scanner(System.in);
                String yString = scanner.nextLine();
                if (yString.isEmpty()) {
                    System.out.println("Value cannot be null. Try again.");
                }
                Float y = Float.parseFloat(yString);
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
                if (priceString.isEmpty()) {
                    return null;
                } else {
                    Long price = Long.parseLong(priceString);
                    if (price <= 0) {
                        System.out.println("Value must be positive. Try again.");
                        continue;
                    }
                    return price;
                }
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be a long-type number. Try again. ");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully. ");
                System.exit(0);
            }catch (NumberFormatException e) {
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
                if (discountString.isEmpty()) {
                    return null;
                } else {
                    Float discount = Float.parseFloat(discountString);
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
            }catch (NumberFormatException e) {
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
                    case "":
                        return null;
                    case "1":
                    case "VIP":
                        return TicketType.VIP;
                    case "2":
                    case "USUAL":
                        return TicketType.USUAL;
                    case "3":
                    case "BUDGETARY":
                        return TicketType.BUDGETARY;
                    default:
                        System.out.println("You should to choose the type from list or it's number. Try again.");
                        break;
                }
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be a string with type or it's number. Try again.");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully.");
                System.exit(0);
            }catch (NumberFormatException e) {
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
                    case "":
                        return null;
                    case "1":
                    case "LOFT":
                        return VenueType.LOFT;
                    case "2":
                    case "THEATRE":
                        return VenueType.THEATRE;
                    case "3":
                    case "STADIUM":
                        return VenueType.STADIUM;
                    default:
                        System.out.println("You should to choose the type from list or it's number. Try again.");
                        break;
                }
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be a string with type or it's number. Try again.");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully.");
                System.exit(0);
            }catch (NumberFormatException e) {
                System.out.println("Incorrect, try again");
            }

        }
    }

    public Integer receiveCapacity() {
        for ( ; ; ) {
            try {
                System.out.print("Enter a capacity: ");
                Scanner scanner = new Scanner(System.in);
                String capacityString = scanner.nextLine();
                if (capacityString.isEmpty()) {
                    System.out.println("Value cannot be null. Try again.");
                } else {
                    Integer capacity = Integer.parseInt(capacityString);
                    if (capacity <= 0) {
                        System.out.println("Value must be positive. Try again.");
                        continue;
                    }
                    return capacity;
                }
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be a integer-type number. Try again. ");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully. ");
                System.exit(0);
            }catch (NumberFormatException e) {
                System.out.println("Incorrect, try again");
            }

        }
    }

    public Venue receiveVenue() {
        return new Venue(0, receiveName(), receiveCapacity(), receiveVenueType());
    }

    public Ticket receiveTicket() {
        Integer id = IDGenerator.generateID();
        Venue venue = receiveVenue();
        venue.setId(id);
        return new Ticket(id, receiveName(), receiveCoordinates(),
                LocalDate.now(), receivePrice(), receiveDiscount(), receiveTicketType(), venue);
    }


}
