import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

// Generic class Booking
class Booking<T> {
    private T bookingDetails;

    public Booking(T bookingDetails) {
        this.bookingDetails = bookingDetails;
    }

    public T getBookingDetails() {
        return bookingDetails;
    }

    public void setBookingDetails(T bookingDetails) {
        this.bookingDetails = bookingDetails;
    }
}

// Generic interface for BookingService
interface BookingService<T> {
    void makeBooking(T bookingDetails);

    T getBookingDetails(int bookingId);
}

// TurfBookingDetails class representing details of a turf booking
class TurfBookingDetails {
    private String turfName;
    private int numOfMalePlayers;
    private int numOfFemalePlayers;
    private double totalAmount;

    public TurfBookingDetails(String turfName, int numOfMalePlayers, int numOfFemalePlayers, double totalAmount) {
        this.turfName = turfName;
        this.numOfMalePlayers = numOfMalePlayers;
        this.numOfFemalePlayers = numOfFemalePlayers;
        this.totalAmount = totalAmount;
    }

    public String getTurfName() {
        return turfName;
    }

    public int getNumOfMalePlayers() {
        return numOfMalePlayers;
    }

    public int getNumOfFemalePlayers() {
        return numOfFemalePlayers;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}

// TurfBookingService implementing BookingService for Turf Bookings
class TurfBookingService implements BookingService<TurfBookingDetails> {
    private Map<Integer, TurfBookingDetails> turfBookings; // Simulating bookings with IDs

    public TurfBookingService() {
        this.turfBookings = new HashMap<>();
    }

    @Override
    public void makeBooking(TurfBookingDetails bookingDetails) {
        // Simulating a booking and assigning a unique ID
        int bookingId = generateUniqueId();
        turfBookings.put(bookingId, bookingDetails);
        System.out.println("Turf Booking made with ID: " + bookingId);
    }

    @Override
    public TurfBookingDetails getBookingDetails(int bookingId) {
        return turfBookings.get(bookingId);
    }

    // Other methods specific to Turf bookings can be added here

    // Helper method to generate unique IDs (for simulation purposes)
    private int generateUniqueId() {
        // Logic to generate unique IDs
        return new Random().nextInt(1000);
    }
}

public class Mainlab6 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter name of the turf: ");
        String turfName = scanner.nextLine();

        System.out.print("Enter number of male players: ");
        int numOfMalePlayers = scanner.nextInt();

        System.out.print("Enter number of female players: ");
        int numOfFemalePlayers = scanner.nextInt();

        System.out.print("Enter total amount for the booking: ");
        double totalAmount = scanner.nextDouble();

        // Creating TurfBookingDetails object with user input
        TurfBookingDetails userBookingDetails = new TurfBookingDetails(turfName, numOfMalePlayers, numOfFemalePlayers, totalAmount);

        // Creating an instance of TurfBookingService
        TurfBookingService turfBookingService = new TurfBookingService();

        // Making a turf booking with user input details
        turfBookingService.makeBooking(userBookingDetails);

        // Getting booking details
        System.out.print("Enter booking ID to retrieve details: ");
        int bookingId = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        TurfBookingDetails bookingDetails = turfBookingService.getBookingDetails(bookingId);
        if (bookingDetails != null) {
            System.out.println("Booking Details for ID " + bookingId + ":");
            System.out.println("Turf Name: " + bookingDetails.getTurfName());
            System.out.println("Number of Male Players: " + bookingDetails.getNumOfMalePlayers());
            System.out.println("Number of Female Players: " + bookingDetails.getNumOfFemalePlayers());
            System.out.println("Total Amount: " + bookingDetails.getTotalAmount());
        } else {
            System.out.println("No booking found for ID: " + bookingId);
        }

        scanner.close();
    }
}
