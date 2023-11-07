import java.util.Scanner;

public class TurfBookingSystem {
    private String turfName;
    private int maxCapacity;
    protected int currentOccupancy;
    private double costPerHour;
    private int maleMembers;
    private int femaleMembers;
    private String mobileNumber; // New data member to store mobile number

    public TurfBookingSystem() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter Turf Name: ");
        turfName = input.nextLine();
        System.out.print("Enter Maximum Capacity: ");
        maxCapacity = input.nextInt();
        System.out.print("Enter Cost Per Hour: $");
        costPerHour = input.nextDouble();
        currentOccupancy = 0;
        maleMembers = 0;
        femaleMembers = 0;
        input.nextLine(); // Consume the newline character
        System.out.print("Enter Mobile Number: ");
        mobileNumber = input.nextLine();
    }

    public void bookTurf(int hours, int numPeople, int numMaleMembers, int numFemaleMembers) {
        if (currentOccupancy + hours <= maxCapacity) {
            double totalCost = hours * costPerHour;
            currentOccupancy += hours;
            maleMembers += numMaleMembers;
            femaleMembers += numFemaleMembers;
            System.out.println("Booking is successful for " + numPeople + " people for " + hours + " hours.");
            System.out.println("Total Cost: $" + totalCost);
            System.out.println("Male Members: " + maleMembers);
            System.out.println("Female Members: " + femaleMembers);
            System.out.println("Mobile Number: " + mobileNumber); // Display mobile number
        } else {
            System.out.println("Booking failed. Turf is already fully occupied.");
        }
    }

    public void bookTurf(int hours, int numPeople) {
        if (currentOccupancy + hours <= maxCapacity) {
            double totalCost = hours * costPerHour;
            currentOccupancy += hours;
            System.out.println("Booking is successful for " + numPeople + " people for " + hours + " hours.");
            System.out.println("Total Cost: $" + totalCost);
            System.out.println("Mobile Number: " + mobileNumber); // Display mobile number
        } else {
            System.out.println("Booking failed. Turf is already fully occupied.");
        }
    }

    public static void main(String[] args) {
        TurfBookingSystem turf = new TurfBookingSystem(); // Create a turf object using user input

        Scanner input = new Scanner(System.in);
        System.out.print("Enter the number of hours to book: ");
        int hoursToBook = input.nextInt();
        System.out.print("Enter the number of people: ");
        int numPeopleToBook = input.nextInt();
        System.out.print("Enter the number of male members: ");
        int numMaleMembers = input.nextInt();
        System.out.print("Enter the number of female members: ");
        int numFemaleMembers = input.nextInt();

        // Call the first bookTurf method with four parameters
        turf.bookTurf(hoursToBook, numPeopleToBook, numMaleMembers, numFemaleMembers);

        // Call the second bookTurf method with two parameters
        turf.bookTurf(hoursToBook, numPeopleToBook);
    }
}
