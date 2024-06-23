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

public class WriteMedicine extends JFrame implements ActionListener {
    private JLabel patientUsernameLabel, diseaseLabel, medicine1Label, medicine2Label, medicine3Label, dateLabel;
    private JTextField diseaseField, medicine1Field, medicine2Field, medicine3Field;
    private JButton saveButton, backButton;
    private JTable patientTable;
    private DefaultTableModel tableModel;
    private String doctorUsername;
    private List<Patient> patientList;
    private JDateChooser dateChooser;

    public WriteMedicine(String doctorUsername) {
        this.doctorUsername = doctorUsername;
        this.patientList = loadPatients(); // Load patient data from the file
        setTitle("Write Medicine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizing the window
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Panel to hold the form elements
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Write Medicine"));
        formPanel.setBackground(new Color(240, 248, 255)); // Light blue background

        gbc.insets = new Insets(10, 10, 10, 10);

        // Adding patient table
        patientUsernameLabel = new JLabel("Select Patient:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(patientUsernameLabel, gbc);

        tableModel = new DefaultTableModel(new String[]{"Username", "Name"}, 0);
        patientTable = new JTable(tableModel);
        loadPatientTable();

        JScrollPane scrollPane = new JScrollPane(patientTable);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        formPanel.add(scrollPane, gbc);
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;

        diseaseLabel = new JLabel("Disease:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(diseaseLabel, gbc);

        diseaseField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(diseaseField, gbc);

        medicine1Label = new JLabel("Medicine 1:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(medicine1Label, gbc);

        medicine1Field = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(medicine1Field, gbc);

        medicine2Label = new JLabel("Medicine 2:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(medicine2Label, gbc);

        medicine2Field = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(medicine2Field, gbc);

        medicine3Label = new JLabel("Medicine 3:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(medicine3Label, gbc);

        medicine3Field = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(medicine3Field, gbc);

        dateLabel = new JLabel("Date:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(dateLabel, gbc);

        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        dateChooser.setDate(new Date()); // Set today's date as the default date
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(dateChooser, gbc);

        saveButton = new JButton("Save");
        saveButton.addActionListener(this);
        saveButton.setBackground(new Color(135, 206, 250)); // Sky blue background
        saveButton.setForeground(Color.WHITE); // White text
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(saveButton, gbc);

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

    private void loadPatientTable() {
        for (Patient patient : patientList) {
            tableModel.addRow(new Object[]{patient.getUsername(), patient.getName()});
        }
    }

    private List<Patient> loadPatients() {
        List<Patient> patients = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("Patient.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 6) {
                    String name = data[0];
                    int age = Integer.parseInt(data[1]);
                    String bloodGroup = data[2];
                    String gender = data[3];
                    String username = data[4];
                    String password = data[5];
                    patients.add(new Patient(name, age, bloodGroup, gender, username, password));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return patients;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            int selectedRow = patientTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a patient");
                return;
            }

            String patientUsername = (String) tableModel.getValueAt(selectedRow, 0);
            String disease = diseaseField.getText();
            if (!disease.matches("[a-zA-Z ]+")) {
                JOptionPane.showMessageDialog(this, "Disease must contain only alphabetic characters.", "Invalid Disease", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String medicine1 = medicine1Field.getText();
            String medicine2 = medicine2Field.getText();
            String medicine3 = medicine3Field.getText();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(dateChooser.getDate());
            String medicineData = doctorUsername + "," + patientUsername + "," + disease + "," + medicine1 + "," + medicine2 + "," + medicine3 + "," + date + "\n";
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("Medicine.txt", true));
                writer.write(medicineData);
                writer.close();
                JOptionPane.showMessageDialog(null, "Medicine data saved successfully");
                dispose();
                new ChargeFee(doctorUsername, patientUsername);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == backButton) {
            dispose();
            new DoctorPage(doctorUsername);
        }
    }

    public static void main(String[] args) {
        new WriteMedicine("doctorUsername");
    }
}