import java.util.Scanner;

class BookingThread extends Thread {
    private IndoorTurf indoorTurf;
    private int slot;
    private int males;
    private int females;

    public BookingThread(IndoorTurf indoorTurf, int slot, int males, int females) {
        this.indoorTurf = indoorTurf;
        this.slot = slot;
        this.males = males;
        this.females = females;
    }

    @Override
    public void run() {
        synchronized (indoorTurf) {
            if (indoorTurf.isSlotAvailable(slot)) {
                indoorTurf.setPlayersCount(males, females);
                indoorTurf.bookSlot(slot);
                System.out.println("Slot " + (slot + 1) + " booked successfully by Thread-" + this.getId());
                System.out.println("Indoor Turf Booking Cost for 1 hour: $" + indoorTurf.getPricePerHour());
            } else {
                System.out.println("Slot " + (slot + 1) + " is not available. Booking failed for Thread-" + this.getId());
            }
        }
    }
}

public class OnlineTurfBooking {
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

        // Booking a slot using multithreading
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            System.out.print("Enter the number of males for slot " + (i + 1) + ": ");
            int males = scanner.nextInt();
            System.out.print("Enter the number of females for slot " + (i + 1) + ": ");
            int females = scanner.nextInt();

            Thread bookingThread = new BookingThread(indoorTurf, finalI, males, females);
            bookingThread.start(); // Start the thread for booking
        }

        scanner.close();
    }
}
