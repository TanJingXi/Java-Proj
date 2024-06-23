import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookAppointment extends JFrame implements ActionListener {
    private JTable doctorTable;
    private JButton bookButton, backButton;
    private String patientUsername;
    private List<Doctor> doctorList;
    private JDateChooser dateChooser;
    private JComboBox<String> timePicker;
    private DefaultTableModel model;

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
        model = new DefaultTableModel(columnNames, 0);
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

        // Adding date chooser
        JLabel dateLabel = new JLabel("Select Date:");
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(dateLabel, gbc);

        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        dateChooser.setMinSelectableDate(new Date());
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(dateChooser, gbc);

        // Adding time picker
        JLabel timeLabel = new JLabel("Select Time:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(timeLabel, gbc);

        timePicker = new JComboBox<>();
        for (int hour = 10; hour <= 20; hour++) {
            timePicker.addItem(String.format("%02d:00", hour));
            timePicker.addItem(String.format("%02d:30", hour));
        }
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(timePicker, gbc);

        bookButton = new JButton("Book Appointment");
        bookButton.addActionListener(this);
        bookButton.setBackground(new Color(135, 206, 250)); // Sky blue background
        bookButton.setForeground(Color.WHITE); // White text
        gbc.gridx = 0;
        gbc.gridy = 3;
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
            Date selectedDate = dateChooser.getDate();
            String selectedTime = (String) timePicker.getSelectedItem();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String appointmentDate = sdf.format(selectedDate);

            if (isTimeSlotAvailable(doctorUsername, appointmentDate, selectedTime)) {
                try {
                    // Append appointment to Appointment.txt
                    BufferedWriter writer = new BufferedWriter(new FileWriter("Appointment.txt", true));
                    writer.write(doctorUsername + "," + patientUsername + "," + appointmentDate + "," + selectedTime);
                    writer.newLine();
                    writer.close();

                    JOptionPane.showMessageDialog(this, "Appointment booked successfully");

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selected time slot is not available. Please choose another time.");
            }

        } else if (e.getSource() == backButton) {
            // Go back to user page
            new PatientPage(patientUsername);
            dispose();
        }
    }

    private boolean isTimeSlotAvailable(String doctorUsername, String appointmentDate, String appointmentTime) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Appointment.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 4) {
                    String bookedDoctor = fields[0];
                    String bookedDate = fields[2];
                    String bookedTime = fields[3];
                    if (bookedDoctor.equals(doctorUsername) && bookedDate.equals(appointmentDate) && bookedTime.equals(appointmentTime)) {
                        reader.close();
                        return false;
                    }
                }
            }
            reader.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error checking appointments: " + ex.getMessage());
            ex.printStackTrace();
        }
        return true;
    }
}

class Doctor {
    private String name;
    private int age;
    private String qualification;
    private String gender;
    private String username;
    private String password;

    public Doctor(String name, int age, String qualification, String gender, String username, String password) {
        this.name = name;
        this.age = age;
        this.qualification = qualification;
        this.gender = gender;
        this.username = username;
        this.password = password;
    }

    // Getters and setters
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getQualification() { return qualification; }
    public String getGender() { return gender; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }

    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setQualification(String qualification) { this.qualification = qualification; }
    public void setGender(String gender) { this.gender = gender; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
}