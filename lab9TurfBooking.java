import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

class Turf {
    private String turfId;
    private String location;
    private boolean isBooked;
    private String turfName;
    private int numberOfMalePlayers;
    private int numberOfFemalePlayers;

    public Turf(String turfId, String location) {
        this.turfId = turfId;
        this.location = location;
        this.isBooked = false;
        this.turfName = "";
        this.numberOfMalePlayers = 0;
        this.numberOfFemalePlayers = 0;
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

    public String getTurfName() {
        return turfName;
    }

    public int getNumberOfMalePlayers() {
        return numberOfMalePlayers;
    }

    public int getNumberOfFemalePlayers() {
        return numberOfFemalePlayers;
    }

    public void bookTurf(String turfName, int numberOfMalePlayers, int numberOfFemalePlayers) {
        if (!isBooked) {
            this.isBooked = true;
            this.turfName = turfName;
            this.numberOfMalePlayers = numberOfMalePlayers;
            this.numberOfFemalePlayers = numberOfFemalePlayers;

            JOptionPane.showMessageDialog(null,
                    "Turf " + turfId + " booked successfully at " + location +
                            "\nTurf Name: " + turfName +
                            "\nNumber of Male Players: " + numberOfMalePlayers +
                            "\nNumber of Female Players: " + numberOfFemalePlayers);
        } else {
            JOptionPane.showMessageDialog(null, "Turf " + turfId + " is already booked.");
        }
    }

    public void releaseTurf() {
        if (isBooked) {
            isBooked = false;
            turfName = "";
            numberOfMalePlayers = 0;
            numberOfFemalePlayers = 0;
            JOptionPane.showMessageDialog(null, "Turf " + turfId + " released.");
        } else {
            JOptionPane.showMessageDialog(null, "Turf " + turfId + " is not booked.");
        }
    }

    @Override
    public String toString() {
        return "Turf{" +
                "turfId='" + turfId + '\'' +
                ", location='" + location + '\'' +
                ", isBooked=" + isBooked +
                ", turfName='" + turfName + '\'' +
                ", numberOfMalePlayers=" + numberOfMalePlayers +
                ", numberOfFemalePlayers=" + numberOfFemalePlayers +
                '}';
    }
}

public class lab9TurfBooking {
    private Map<String, Turf> turfMap;

    public lab9TurfBooking() {
        turfMap = new HashMap<>();
        turfMap.put("T1", new Turf("T1", "City Park"));
        turfMap.put("T2", new Turf("T2", "Sports Arena"));
    }

    public void run() {
        JFrame frame = new JFrame("Turf Booking System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JLabel turfLabel = new JLabel("Enter Turf ID:");
        JTextField turfTextField = new JTextField();

        JLabel nameLabel = new JLabel("Enter Turf Name:");
        JTextField nameTextField = new JTextField();

        JLabel maleLabel = new JLabel("Enter Number of Male Players:");
        JTextField maleTextField = new JTextField();

        JLabel femaleLabel = new JLabel("Enter Number of Female Players:");
        JTextField femaleTextField = new JTextField();

        JButton bookButton = new JButton("Book Turf");
        JButton releaseButton = new JButton("Release Turf");

        panel.add(turfLabel);
        panel.add(turfTextField);
        panel.add(nameLabel);
        panel.add(nameTextField);
        panel.add(maleLabel);
        panel.add(maleTextField);
        panel.add(femaleLabel);
        panel.add(femaleTextField);
        panel.add(bookButton);
        panel.add(releaseButton);

        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String turfId = turfTextField.getText().toUpperCase();
                Turf turf = turfMap.get(turfId);
                if (turf != null) {
                    String turfName = nameTextField.getText();
                    int numberOfMalePlayers = Integer.parseInt(maleTextField.getText());
                    int numberOfFemalePlayers = Integer.parseInt(femaleTextField.getText());
                    turf.bookTurf(turfName, numberOfMalePlayers, numberOfFemalePlayers);
                } else {
                    JOptionPane.showMessageDialog(null, "Turf with ID " + turfId + " not found.");
                }
            }
        });

        releaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String turfId = turfTextField.getText().toUpperCase();
                Turf turf = turfMap.get(turfId);
                if (turf != null) {
                    turf.releaseTurf();
                } else {
                    JOptionPane.showMessageDialog(null, "Turf with ID " + turfId + " not found.");
                }
            }
        });

        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setSize(400, 200);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new lab9TurfBooking().run();
            }
        });
    }
}
