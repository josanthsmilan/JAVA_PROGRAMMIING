import java.util.Scanner;

// Abstract class for Turf
abstract class Turf {
    private String turfName;
    private double pricePerHour;

    public Turf(String turfName, double pricePerHour) {
        this.turfName = turfName;
        this.pricePerHour = pricePerHour;
    }

    public String getTurfName() {
        return turfName;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    // Abstract method to calculate booking cost
    public abstract double calculateBookingCost();
}

// Concrete class representing Indoor Turf which inherits from Turf
class IndoorTurf extends Turf {
    private boolean isAirConditioned;
    private boolean[] slotsAvailability; // Slots availability for 5 hours
    private int numMales;
    private int numFemales;

    public IndoorTurf(String turfName, double pricePerHour, boolean isAirConditioned) {
        super(turfName, pricePerHour);
        this.isAirConditioned = isAirConditioned;
        this.slotsAvailability = new boolean[5]; // Only 5 slots available
        initializeSlots();
    }

    // Initialize all slots as available initially
    private void initializeSlots() {
        for (int i = 0; i < slotsAvailability.length; i++) {
            slotsAvailability[i] = true;
        }
    }

    public boolean isAirConditioned() {
        return isAirConditioned;
    }

    // Book a specific slot
    public void bookSlot(int slot) {
        slotsAvailability[slot] = false;
    }

    // Check availability of a slot
    public boolean isSlotAvailable(int slot) {
        return slotsAvailability[slot];
    }

    // Set number of males and females for the booking
    public void setPlayersCount(int males, int females) {
        this.numMales = males;
        this.numFemales = females;
    }

    @Override
    public double calculateBookingCost() {
        return getPricePerHour(); // Cost for one hour booking
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Getting details for Indoor Turf from the user
        System.out.println("Enter details for Indoor Turf:");
        System.out.print("Turf Name: ");
        String indoorName = scanner.nextLine();
        System.out.print("Price per Hour: ");
        double indoorPrice = scanner.nextDouble();
        scanner.nextLine(); // Clear buffer
        System.out.print("Is Air Conditioned? (true/false): ");
        boolean isIndoorAirConditioned = scanner.nextBoolean();

        IndoorTurf indoorTurf = new IndoorTurf(indoorName, indoorPrice, isIndoorAirConditioned);

        // Display available slots
        System.out.println("\nAvailable Slots:");
        for (int i = 0; i < 5; i++) {
            if (indoorTurf.isSlotAvailable(i)) {
                System.out.println("Slot " + (i + 1) + ": Available");
            } else {
                System.out.println("Slot " + (i + 1) + ": Not Available");
            }
        }

        // Booking a slot
        System.out.print("\nEnter the slot number you want to book (1-5): ");
        int selectedSlot = scanner.nextInt();

        if (selectedSlot >= 1 && selectedSlot <= 5 && indoorTurf.isSlotAvailable(selectedSlot - 1)) {
            System.out.print("Enter the number of males: ");
            int males = scanner.nextInt();
            System.out.print("Enter the number of females: ");
            int females = scanner.nextInt();

            indoorTurf.setPlayersCount(males, females);
            indoorTurf.bookSlot(selectedSlot - 1);
            System.out.println("Slot " + selectedSlot + " booked successfully!");
            System.out.println("Indoor Turf Booking Cost for 1 hour: $" + indoorTurf.getPricePerHour());
        } else {
            System.out.println("Invalid or unavailable slot. Please try again.");
        }

        scanner.close();
    }
}
