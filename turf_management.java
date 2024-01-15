package turf_management;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class turf_management extends JFrame {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/turf_management";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Smilan28@mysql";
    private static final String QUERY = "select * from turf where surface_type = ?";
    private static final String BOOKING_QUERY = "insert into bookings (turf_id, customer_name, num_people, payment_mode, mobile_number) values (?, ?, ?, ?, ?)";
    private static final String BOOKED_TURF_QUERY = "select * from bookings where turf_id = ?";

    private JTextArea resultArea;
    private JComboBox<String> surfaceTypeDropdown;
    private JTextField customerNameField;
    private JTextField numPeopleField;
    private JComboBox<String> paymentModeDropdown;
    private JTextField mobileNumberField;

    public turf_management() {
        setTitle("Turf Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        resultArea = new JTextArea();
        resultArea.setEditable(false);

        surfaceTypeDropdown = new JComboBox<>(new String[]{"Natural Grass", "Artificial Turf", "Hybrid Grass"});

        customerNameField = new JTextField(20);
        numPeopleField = new JTextField(5);

        paymentModeDropdown = new JComboBox<>(new String[]{"UPI", "Netbanking", "Cash", "Debit/Credit"});

        mobileNumberField = new JTextField(15);

        JButton fetchDataButton = new JButton("Fetch Turf Data");
        fetchDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchTurfData();
            }
        });

        JButton bookTurfButton = new JButton("Book Turf");
        bookTurfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookTurf();
            }
        });

        JButton bookedTurfButton = new JButton("Show Booked Turf");
        bookedTurfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBookedTurf();
            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Select Surface Type: "));
        inputPanel.add(surfaceTypeDropdown);
        inputPanel.add(new JLabel("Customer Name: "));
        inputPanel.add(customerNameField);
        inputPanel.add(new JLabel("Number of People: "));
        inputPanel.add(numPeopleField);
        inputPanel.add(new JLabel("Payment Mode: "));
        inputPanel.add(paymentModeDropdown);
        inputPanel.add(new JLabel("Mobile Number: "));
        inputPanel.add(mobileNumberField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(fetchDataButton);
        buttonPanel.add(bookTurfButton);
        buttonPanel.add(bookedTurfButton);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(inputPanel, BorderLayout.NORTH);
        container.add(new JScrollPane(resultArea), BorderLayout.CENTER);
        container.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void fetchTurfData() {
        String selectedSurfaceType = (String) surfaceTypeDropdown.getSelectedItem();

        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY)) {

            preparedStatement.setString(1, selectedSurfaceType);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultArea.setText(""); // Clear previous results

                while (resultSet.next()) {
                    resultArea.append("Turf ID: " + resultSet.getInt("turf_id") + "\n");
                    resultArea.append("Turf Name: " + resultSet.getString("turf_name") + "\n");
                    resultArea.append("Location: " + resultSet.getString("location") + "\n");
                    resultArea.append("Surface Type: " + resultSet.getString("surface_type") + "\n");
                    resultArea.append("Capacity: " + resultSet.getInt("capacity") + "\n\n");
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            resultArea.setText("Error: " + ex.getMessage());
        }
    }

    private void bookTurf() {
        String selectedSurfaceType = (String) surfaceTypeDropdown.getSelectedItem();
        String customerName = customerNameField.getText();
        int numPeople = Integer.parseInt(numPeopleField.getText());
        String paymentMode = (String) paymentModeDropdown.getSelectedItem();
        String mobileNumber = mobileNumberField.getText();

        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(BOOKING_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            try (PreparedStatement turfIdStatement = connection.prepareStatement("select turf_id from turf where surface_type = ?")) {
                turfIdStatement.setString(1, selectedSurfaceType);
                try (ResultSet resultSet = turfIdStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int turfId = resultSet.getInt("turf_id");

                        preparedStatement.setInt(1, turfId);
                        preparedStatement.setString(2, customerName);
                        preparedStatement.setInt(3, numPeople);
                        preparedStatement.setString(4, paymentMode);
                        preparedStatement.setString(5, mobileNumber);

                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            resultArea.setText("Booking successful! Turf ID: " + turfId);
                        } else {
                            resultArea.setText("Booking failed. Please try again.");
                        }
                    } else {
                        resultArea.setText("Turf not found for the selected surface type.");
                    }
                }
            }

        } catch (SQLException | NumberFormatException ex) {
            ex.printStackTrace();
            resultArea.setText("Error: " + ex.getMessage());
        }
    }

    private void showBookedTurf() {
        String selectedSurfaceType = (String) surfaceTypeDropdown.getSelectedItem();

        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(BOOKED_TURF_QUERY)) {

            try (PreparedStatement turfIdStatement = connection.prepareStatement("select turf_id from turf where surface_type = ?")) {
                turfIdStatement.setString(1, selectedSurfaceType);
                try (ResultSet resultSet = turfIdStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int turfId = resultSet.getInt("turf_id");

                        preparedStatement.setInt(1, turfId);

                        try (ResultSet bookedTurfResultSet = preparedStatement.executeQuery()) {
                            resultArea.setText(""); // Clear previous results

                            while (bookedTurfResultSet.next()) {
                                resultArea.append("Booking ID: " + bookedTurfResultSet.getInt("booking_id") + "\n");
                                resultArea.append("Turf ID: " + bookedTurfResultSet.getInt("turf_id") + "\n");
                                resultArea.append("Customer Name: " + bookedTurfResultSet.getString("customer_name") + "\n");
                                resultArea.append("Number of People: " + bookedTurfResultSet.getInt("num_people") + "\n");
                                resultArea.append("Payment Mode: " + bookedTurfResultSet.getString("payment_mode") + "\n");
                                resultArea.append("Mobile Number: " + bookedTurfResultSet.getString("mobile_number") + "\n");
                                resultArea.append("Booking Date: " + bookedTurfResultSet.getTimestamp("booking_date") + "\n\n");
                            }
                        }

                    } else {
                        resultArea.setText("Turf not found for the selected surface type.");
                    }
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            resultArea.setText("Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new turf_management().setVisible(true);
            }
        });
    }
}
