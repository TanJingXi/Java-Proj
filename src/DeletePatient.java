import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DeletePatient extends JFrame implements ActionListener {
    private JTable patientTable;
    private JButton deleteButton, backButton;
    private List<Patient> patientList;
    private DefaultTableModel tableModel;

    public DeletePatient() {
        setTitle("Delete Patient");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Load patients from the file
        patientList = loadPatients();

        // Panel to hold form elements
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Delete Patient"));
        formPanel.setBackground(new Color(240, 248, 255));

        gbc.insets = new Insets(10, 10, 10, 10);

        // Adding patient table
        String[] columnNames = {"Name", "Age", "Blood Group", "Gender", "Username"};
        tableModel = new DefaultTableModel(columnNames, 0);
        patientTable = new JTable(tableModel);
        patientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Populate the table with patient data
        loadPatientTable();

        JScrollPane scrollPane = new JScrollPane(patientTable);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        formPanel.add(scrollPane, gbc);

        deleteButton = new JButton("Delete Patient");
        deleteButton.addActionListener(this);
        deleteButton.setBackground(new Color(255, 99, 71));
        deleteButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(deleteButton, gbc);

        backButton = new JButton("Back to Admin Page");
        backButton.addActionListener(this);
        backButton.setBackground(new Color(135, 206, 250));
        backButton.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(backButton, gbc);

        add(formPanel);
        setVisible(true);
    }

    private List<Patient> loadPatients() {
        List<Patient> patients = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("Patient.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 6) {
                    Patient patient = new Patient(fields[0], Integer.parseInt(fields[1]), fields[2], fields[3], fields[4], fields[5]);
                    patients.add(patient);
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error loading patients: " + ex.getMessage());
            ex.printStackTrace();
        }
        return patients;
    }

    private void loadPatientTable() {
        for (Patient patient : patientList) {
            Object[] row = {patient.getName(), patient.getAge(), patient.getBloodGroup(), patient.getGender(), patient.getUsername()};
            tableModel.addRow(row);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == deleteButton) {
            int selectedRow = patientTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a patient from the table");
                return;
            }

            String username = (String) tableModel.getValueAt(selectedRow, 4);
            deletePatient(username);
            tableModel.removeRow(selectedRow);

            JOptionPane.showMessageDialog(this, "Patient deleted successfully");

        } else if (e.getSource() == backButton) {
            // Go back to admin page
            new AdminPage();
            dispose();
        }
    }

    private void deletePatient(String username) {
        patientList.removeIf(patient -> patient.getUsername().equals(username));
        savePatients();
    }

    private void savePatients() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Patient.txt"))) {
            for (Patient patient : patientList) {
                writer.write(patient.getName() + "," + patient.getAge() + "," + patient.getBloodGroup() + "," + patient.getGender() + "," + patient.getUsername() + "," + patient.getPassword());
                writer.newLine();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving patients: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new DeletePatient();
    }
}