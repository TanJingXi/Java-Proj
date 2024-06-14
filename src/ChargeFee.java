import javax.swing.*;
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

        // Initialize components
        chargeLabel = new JLabel("Charge:");
        chargeField = new JTextField(10);
        submitButton = new JButton("Submit");
        backButton = new JButton("Back");

        // Set layout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        // Add components to the panel
        panel.add(chargeLabel);
        panel.add(chargeField);
        panel.add(submitButton);
        panel.add(backButton);

        // Add action listeners
        submitButton.addActionListener(this);
        backButton.addActionListener(this);

        // Set the frame properties
        setTitle("Charge Fee");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 150);
        setLocationRelativeTo(null);
        setResizable(false);
        add(panel);
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
}
