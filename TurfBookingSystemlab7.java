import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

class Turf {
    private String name;
    private boolean available;
    private int capacity;

    public Turf(String name, boolean available, int capacity) {
        this.name = name;
        this.available = available;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return available;
    }

    public int getCapacity() {
        return capacity;
    }
}

public class TurfBookingSystemlab7 {
    public static void main(String[] args) {
        List<Turf> availableTurfs = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        // Getting user input for turfs
        System.out.println("Enter details for available turfs:");
        for (int i = 1; i <= 3; i++) {
            System.out.println("Turf " + i + " name:");
            String name = scanner.nextLine();
            System.out.println("Is Turf " + i + " available? (true/false):");
            boolean availability = scanner.nextBoolean();
            System.out.println("Enter Turf " + i + " capacity:");
            int capacity = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            availableTurfs.add(new Turf(name, availability, capacity));
        }

        // Filtering available turfs using a lambda expression
        List<Turf> filteredTurfs = filterTurfs(availableTurfs, turf -> turf.isAvailable());

        // Displaying filtered turfs
        System.out.println("\nAvailable turfs:");
        for (Turf turf : filteredTurfs) {
            System.out.println(turf.getName() + " - Capacity: " + turf.getCapacity());
        }

        scanner.close();
    }

    public static List<Turf> filterTurfs(List<Turf> turfs, Predicate<Turf> predicate) {
        List<Turf> filteredTurfs = new ArrayList<>();
        for (Turf turf : turfs) {
            if (predicate.test(turf)) {
                filteredTurfs.add(turf);
            }
        }
        return filteredTurfs;
    }
}
