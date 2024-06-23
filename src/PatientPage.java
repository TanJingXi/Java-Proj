import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PatientPage extends JFrame implements ActionListener {

    private JLabel titleLabel, optionsLabel;
    private JButton bookAppointmentButton, payBillButton, patientMedicineButton, backButton;
    private String username;

    public PatientPage(String username) {
        this.username = username;
        setTitle("Patient Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizing the window
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Panel to hold the form elements
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Patient Dashboard"));
        panel.setBackground(new Color(240, 248, 255)); // Light blue background

        gbc.insets = new Insets(10, 10, 10, 10);

        // Adding title label
        titleLabel = new JLabel("Welcome " + username + "!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(titleLabel, gbc);

        // Adding options label
        optionsLabel = new JLabel("Please select an option:");
        optionsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridy = 1;
        panel.add(optionsLabel, gbc);

        // Adding Book Appointment button
        bookAppointmentButton = new JButton("Book an Appointment");
        bookAppointmentButton.addActionListener(this);
        bookAppointmentButton.setBackground(new Color(135, 206, 250)); // Sky blue background
        bookAppointmentButton.setForeground(Color.WHITE); // White text
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(bookAppointmentButton, gbc);

        // Adding Pay Bill button
        payBillButton = new JButton("Pay Bill");
        payBillButton.addActionListener(this);
        payBillButton.setBackground(new Color(135, 206, 250)); // Sky blue background
        payBillButton.setForeground(Color.WHITE); // White text
        gbc.gridy = 3;
        panel.add(payBillButton, gbc);

        // Adding Patient Medicine button
        patientMedicineButton = new JButton("Patient Medicine");
        patientMedicineButton.addActionListener(this);
        patientMedicineButton.setBackground(new Color(135, 206, 250)); // Sky blue background
        patientMedicineButton.setForeground(Color.WHITE); // White text
        gbc.gridy = 4;
        panel.add(patientMedicineButton, gbc);

        // Adding Back button
        backButton = new JButton("Back");
        backButton.addActionListener(this);
        backButton.setBackground(new Color(255, 99, 71)); // Tomato background
        backButton.setForeground(Color.WHITE); // White text
        gbc.gridy = 5;
        panel.add(backButton, gbc);

        // Adding panel to the frame
        add(panel);

        // Setting the frame visible
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bookAppointmentButton) {
            // Redirect to BookAppointment class
            new BookAppointment(username);
            dispose();
        } else if (e.getSource() == payBillButton) {
            // Redirect to PayBill class
            new PayBill(username);
            dispose();
        } else if (e.getSource() == patientMedicineButton) {
            // Redirect to PatientMedicine class
            new PatientMedicine(username);
            dispose();
        } else if (e.getSource() == backButton) {
            // Redirect to Home page
            new HospitalHomepage();
            dispose();
        }
    }
}