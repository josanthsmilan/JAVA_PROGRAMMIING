import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Turf {
    private String turfId;
    private String location;
    private boolean isBooked;

    public Turf(String turfId, String location) {
        this.turfId = turfId;
        this.location = location;
        this.isBooked = false;
    }

    public String getTurfId() {
        return turfId;
    }

    public String getLocation() {
        return location;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void bookTurf() {
        if (!isBooked) {
            isBooked = true;
            System.out.println("Turf " + turfId + " booked successfully at " + location);
        } else {
            System.out.println("Turf " + turfId + " is already booked.");
        }
    }

    public void releaseTurf() {
        if (isBooked) {
            isBooked = false;
            System.out.println("Turf " + turfId + " released.");
        } else {
            System.out.println("Turf " + turfId + " is not booked.");
        }
    }

    @Override
    public String toString() {
        return "Turf{" +
                "turfId='" + turfId + '\'' +
                ", location='" + location + '\'' +
                ", isBooked=" + isBooked +
                '}';
    }
}

public class lab8BookingSystem {
    public static void main(String[] args) {
        // Creating a map of turfs with turfId as the key
        Map<String, Turf> turfMap = new HashMap<>();
        turfMap.put("T1", new Turf("T1", "City Park"));
        turfMap.put("T2", new Turf("T2", "Sports Arena"));

        // Displaying the available turfs
        System.out.println("Available Turfs:");
        for (Turf turf : turfMap.values()) {
            System.out.println(turf);
        }

        // Booking a turf based on user input
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the turf ID to book: ");
        String turfToBook = scanner.nextLine().toUpperCase();

        Turf bookedTurf = turfMap.get(turfToBook);
        if (bookedTurf != null) {
            bookedTurf.bookTurf();
        } else {
            System.out.println("Turf with ID " + turfToBook + " not found.");
        }

        // Releasing a turf based on user input
        System.out.print("Enter the turf ID to release: ");
        String turfToRelease = scanner.nextLine().toUpperCase();

        Turf releasedTurf = turfMap.get(turfToRelease);
        if (releasedTurf != null) {
            releasedTurf.releaseTurf();
        } else {
            System.out.println("Turf with ID " + turfToRelease + " not found.");
        }

        // Close the scanner
        scanner.close();
    }
}
