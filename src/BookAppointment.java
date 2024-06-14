import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BookAppointment extends JFrame implements ActionListener {
    private JTable doctorTable;
    private JButton bookButton, backButton;
    private String patientUsername;
    private List<Doctor> doctorList;

    public BookAppointment(String patientUsername) {
        this.patientUsername = patientUsername;
        doctorList = loadDoctors(); // Load doctors from the file
        setTitle("Book Appointment");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizing the window
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Panel to hold the form elements
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Book Appointment"));
        formPanel.setBackground(new Color(240, 248, 255)); // Light blue background

        gbc.insets = new Insets(10, 10, 10, 10);

        // Adding form elements
        String[] columnNames = {"Name", "Age", "Qualification", "Gender", "Username"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        doctorTable = new JTable(model);
        doctorTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Populate the table with doctor data
        for (Doctor doctor : doctorList) {
            Object[] row = {doctor.getName(), doctor.getAge(), doctor.getQualification(), doctor.getGender(), doctor.getUsername()};
            model.addRow(row);
        }

        JScrollPane scrollPane = new JScrollPane(doctorTable);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        formPanel.add(scrollPane, gbc);

        bookButton = new JButton("Book Appointment");
        bookButton.addActionListener(this);
        bookButton.setBackground(new Color(135, 206, 250)); // Sky blue background
        bookButton.setForeground(Color.WHITE); // White text
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(bookButton, gbc);

        backButton = new JButton("Back to Home");
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

    private List<Doctor> loadDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Doctor.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 6) {
                    Doctor doctor = new Doctor(fields[0], Integer.parseInt(fields[1]), fields[2], fields[3], fields[4], fields[5]);
                    doctors.add(doctor);
                }
            }
            reader.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error loading doctors: " + ex.getMessage());
            ex.printStackTrace();
        }
        return doctors;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bookButton) {
            int selectedRow = doctorTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a doctor from the table");
                return;
            }

            String doctorUsername = (String) doctorTable.getValueAt(selectedRow, 4);

            try {
                // Append appointment to Appointment.txt
                BufferedWriter writer = new BufferedWriter(new FileWriter("Appointment.txt", true));
                writer.write(doctorUsername + "," + patientUsername);
                writer.newLine();
                writer.close();

                JOptionPane.showMessageDialog(this, "Appointment booked successfully");

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                ex.printStackTrace();
            }

        } else if (e.getSource() == backButton) {
            // Go back to user page
            new PatientPage(patientUsername);
            dispose();
        }
    }

    public static void main(String[] args) {
        // Example usage
        new BookAppointment("JohnDoe");
    }
}
