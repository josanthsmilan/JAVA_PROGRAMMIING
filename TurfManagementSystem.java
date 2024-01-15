import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class TurfManagementSystem extends JFrame {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/turf_management";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Smilan28@mysql";
    private static final String QUERY = "select * from turf";

    private JTextArea resultArea;

    public TurfManagementSystem() {
        setTitle("Turf Management System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        resultArea = new JTextArea();
        resultArea.setEditable(false);

        JButton fetchDataButton = new JButton("Fetch Turf Data");
        fetchDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchTurfData();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(fetchDataButton);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(new JScrollPane(resultArea), BorderLayout.CENTER);
        container.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void fetchTurfData() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY)) {

            resultArea.setText(""); // Clear previous results

            while (resultSet.next()) {
                resultArea.append("Turf ID: " + resultSet.getInt("turf_id") + "\n");
                resultArea.append("Turf Name: " + resultSet.getString("turf_name") + "\n");
                resultArea.append("Location: " + resultSet.getString("location") + "\n");
                resultArea.append("Surface Type: " + resultSet.getString("surface_type") + "\n");
                resultArea.append("Capacity: " + resultSet.getInt("capacity") + "\n\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            resultArea.setText("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TurfManagementSystem().setVisible(true);
            }
        });
    }
}
