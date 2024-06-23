import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DoctorPage extends JFrame implements ActionListener {

    private String username;
    private JButton appointmentButton, medicineButton, backButton;

    public DoctorPage(String username) {
        this.username = username;
        setTitle("Doctor Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizing the window
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Panel to hold the form elements
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Doctor Dashboard"));
        panel.setBackground(new Color(240, 248, 255)); // Light blue background

        gbc.insets = new Insets(10, 10, 10, 10);

        // Adding welcome label
        JLabel welcomeLabel = new JLabel("Welcome, Dr. " + username);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(welcomeLabel, gbc);

        // Adding See Appointments button
        appointmentButton = new JButton("See Appointments");
        appointmentButton.addActionListener(this);
        appointmentButton.setBackground(new Color(135, 206, 250)); // Sky blue background
        appointmentButton.setForeground(Color.WHITE); // White text
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(appointmentButton, gbc);

        // Adding Write Medicine button
        medicineButton = new JButton("Write Medicine");
        medicineButton.addActionListener(this);
        medicineButton.setBackground(new Color(135, 206, 250)); // Sky blue background
        medicineButton.setForeground(Color.WHITE); // White text
        gbc.gridy = 2;
        panel.add(medicineButton, gbc);

        // Adding Back button
        backButton = new JButton("Back");
        backButton.addActionListener(this);
        backButton.setBackground(new Color(255, 99, 71)); // Tomato background
        backButton.setForeground(Color.WHITE); // White text
        gbc.gridy = 3;
        panel.add(backButton, gbc);

        // Adding panel to the frame
        add(panel);

        // Setting the frame visible
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == appointmentButton) {
            new SeeAppointment(username);
        } else if (e.getSource() == medicineButton) {
            dispose();
            new WriteMedicine(username);
        } else if (e.getSource() == backButton) {
            dispose();
            new HospitalHomepage();
        }
    }
}