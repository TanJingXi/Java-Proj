import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class ChargeFee extends JFrame implements ActionListener {
    private JLabel chargeLabel;
    private JTextField chargeField;
    private JButton submitButton, backButton;

    private String doctorUsername, patientUsername;

    public ChargeFee(String doctorUsername, String patientUsername) {
        this.doctorUsername = doctorUsername;
        this.patientUsername = patientUsername;

        // Setting the frame properties
        setTitle("Charge Fee");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizing the window
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Panel to hold the form elements
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Charge Fee Form"));
        formPanel.setBackground(new Color(240, 248, 255)); // Light blue background

        gbc.insets = new Insets(10, 10, 10, 10);

        // Adding form elements
        chargeLabel = new JLabel("Charge:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(chargeLabel, gbc);

        chargeField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(chargeField, gbc);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        submitButton.setBackground(new Color(135, 206, 250)); // Sky blue background
        submitButton.setForeground(Color.WHITE); // White text
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(submitButton, gbc);

        backButton = new JButton("Back");
        backButton.addActionListener(this);
        backButton.setBackground(new Color(255, 99, 71)); // Tomato background
        backButton.setForeground(Color.WHITE); // White text
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(backButton, gbc);

        // Adding form panel to the frame
        add(formPanel);

        // Setting the frame visible
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String charge = chargeField.getText();
            if (charge.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter the charge.");
            } else {
                // Write the fee information to the file
                try {
                    FileWriter writer = new FileWriter("Fee.txt", true);
                    writer.write(doctorUsername + "," + patientUsername + "," + charge + "\n");
                    writer.close();
                    JOptionPane.showMessageDialog(this, "Fee charged successfully.");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error occurred while writing to the file.");
                }
            }
        } else if (e.getSource() == backButton) {
            new HospitalHomepage();
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        // For testing purpose
        new ChargeFee("doctor123", "patient456");
    }
}

// HospitalHomepage class stub for testing
class HospitalHomepage extends JFrame {
    public HospitalHomepage() {
        // Set the frame properties
        setTitle("Hospital Homepage");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
