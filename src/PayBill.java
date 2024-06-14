import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class PayBill extends JFrame implements ActionListener {
    private final String patientUsername;
    private final JLabel feeLabel;
    private final JTextField cardNumberField;
    private final JTextField expiryDateField;
    private final JTextField cvvField;
    private final JButton payButton;
    private final JButton backButton;

    public PayBill(String patientUsername) {
        this.patientUsername = patientUsername;

        // Set up the GUI
        setTitle("Pay Fee");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizing the window
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Panel to hold the form elements
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Pay Bill"));
        panel.setBackground(new Color(240, 248, 255)); // Light blue background

        gbc.insets = new Insets(10, 10, 10, 10);

        // Adding fee label
        feeLabel = new JLabel("Fee: " + getFee());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(feeLabel, gbc);

        // Adding card number label and field
        JLabel cardNumberLabel = new JLabel("Card Number:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(cardNumberLabel, gbc);

        cardNumberField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(cardNumberField, gbc);

        // Adding expiry date label and field
        JLabel expiryDateLabel = new JLabel("Expiry Date (MM/YY):");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(expiryDateLabel, gbc);

        expiryDateField = new JTextField(5);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(expiryDateField, gbc);

        // Adding CVV label and field
        JLabel cvvLabel = new JLabel("CVV:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(cvvLabel, gbc);

        cvvField = new JTextField(3);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(cvvField, gbc);

        // Adding PAY button
        payButton = new JButton("PAY");
        payButton.addActionListener(this);
        payButton.setBackground(new Color(135, 206, 250)); // Sky blue background
        payButton.setForeground(Color.WHITE); // White text
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(payButton, gbc);

        // Adding Back button
        backButton = new JButton("Back");
        backButton.addActionListener(this);
        backButton.setBackground(new Color(255, 99, 71)); // Tomato background
        backButton.setForeground(Color.WHITE); // White text
        gbc.gridx = 1;
        panel.add(backButton, gbc);

        // Adding panel to the frame
        add(panel);

        // Setting the frame visible
        setVisible(true);
    }

    // Helper method to retrieve the fee from the file
    private String getFee() {
        String fee = "N/A";
        try {
            File file = new File("Fee.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] tokens = line.split(",");
                if (tokens[1].equals(patientUsername)) {
                    fee = tokens[2];
                    break;
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fee;
    }

    // Helper method to remove the fee information from the file
    private void removeFee() {
        try {
            File inputFile = new File("Fee.txt");
            File tempFile = new File("Fee_temp.txt");
            Scanner scanner = new Scanner(inputFile);
            FileWriter writer = new FileWriter(tempFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] tokens = line.split(",");
                if (!tokens[1].equals(patientUsername)) {
                    writer.write(line + "\n");
                }
            }
            scanner.close();
            writer.close();
            inputFile.delete();
            tempFile.renameTo(inputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == payButton) {
            // Validate credit card information
            String cardNumber = cardNumberField.getText();
            String expiryDate = expiryDateField.getText();
            String cvv = cvvField.getText();

            if (cardNumber.isEmpty() || expiryDate.isEmpty() || cvv.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all credit card details");
                return;
            }

            // Add further validation if necessary (e.g., format, length)

            // Process payment (for now, we assume the payment is successful)
            // Remove the fee information from the file
            removeFee();

            // Update the fee label
            feeLabel.setText("Fee: " + getFee());

            // Show a message dialog to confirm payment
            JOptionPane.showMessageDialog(this, "Payment successful!");

        } else if (e.getSource() == backButton) {
            // Go back to the patient page
            new PatientPage(patientUsername);
            dispose();
        }
    }

    public static void main(String[] args) {
        // Example usage
        new PayBill("JohnDoe");
    }
}
