import javax.swing.*;
import java.awt.*;
import java.io.*;

public class PatientMedicine extends JFrame {
    private JTextArea medicineArea;
    private String username;

    public PatientMedicine(String username) {
        this.username = username;

        setTitle("Patient Medicine");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the window
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Panel to hold the form elements
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Medicines Prescribed"));
        panel.setBackground(new Color(240, 248, 255)); // Light blue background

        gbc.insets = new Insets(10, 10, 10, 10);

        // Adding heading
        JLabel heading = new JLabel("Medicines Prescribed for " + username);
        heading.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(heading, gbc);

        // Adding medicine area
        medicineArea = new JTextArea(20, 40);
        medicineArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(medicineArea);
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(scrollPane, gbc);

        // Adding back button
        JButton backButton = new JButton("Back to Home");
        backButton.addActionListener(e -> {
            new PatientPage(username);
            dispose();
        });
        backButton.setBackground(new Color(255, 99, 71)); // Tomato background
        backButton.setForeground(Color.WHITE); // White text
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(backButton, gbc);

        // Read and display medicine information from the file
        try {
            File file = new File("Medicine.txt");
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine();
                while (line != null) {
                    String[] medicineInfo = line.split(",");
                    if (medicineInfo[1].equals(username)) {
                        medicineArea.append("Doctor: " + medicineInfo[0] + "\n");
                        medicineArea.append("Disease: " + medicineInfo[2] + "\n");
                        medicineArea.append("Medicine 1: " + medicineInfo[3] + "\n");
                        medicineArea.append("Medicine 2: " + medicineInfo[4] + "\n");
                        medicineArea.append("Medicine 3: " + medicineInfo[5] + "\n");
                        medicineArea.append("Date: " + medicineInfo[6] + "\n\n");
                    }
                    line = reader.readLine();
                }
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Adding panel to the frame
        add(panel);

        // Setting the frame visible
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}