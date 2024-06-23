import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ViewPatients extends JFrame implements ActionListener {
    private JTable patientTable;
    private JTextField searchField;
    private JComboBox<String> filterComboBox;
    private JButton backButton;
    private List<Patient> patientList;
    private DefaultTableModel tableModel;

    public ViewPatients() {
        // Read all the files first
        patientList = loadPatients();

        // Set up the JFrame
        setTitle("View Patients");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Panel to hold form elements
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("View Patients"));
        formPanel.setBackground(new Color(240, 248, 255));

        gbc.insets = new Insets(10, 10, 10, 10);

        // Adding search bar and filter options
        JLabel searchLabel = new JLabel("Search:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(searchLabel, gbc);

        searchField = new JTextField(20);
        searchField.addActionListener(this);
        gbc.gridx = 1;
        formPanel.add(searchField, gbc);

        String[] filterOptions = {"Name", "Username", "Blood Group", "Gender"};
        filterComboBox = new JComboBox<>(filterOptions);
        gbc.gridx = 2;
        formPanel.add(filterComboBox, gbc);

        // Adding patient table
        String[] columnNames = {"Name", "Age", "Blood Group", "Gender", "Username"};
        tableModel = new DefaultTableModel(columnNames, 0);
        patientTable = new JTable(tableModel);
        patientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Populate the table with patient data
        loadPatientTable(patientList);

        JScrollPane scrollPane = new JScrollPane(patientTable);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        formPanel.add(scrollPane, gbc);

        backButton = new JButton("Back to Admin Page");
        backButton.addActionListener(this);
        backButton.setBackground(new Color(135, 206, 250));
        backButton.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
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

    private void loadPatientTable(List<Patient> patients) {
        tableModel.setRowCount(0);
        for (Patient patient : patients) {
            Object[] row = {patient.getName(), patient.getAge(), patient.getBloodGroup(), patient.getGender(), patient.getUsername()};
            tableModel.addRow(row);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchField) {
            String searchText = searchField.getText().toLowerCase();
            String filterOption = (String) filterComboBox.getSelectedItem();

            List<Patient> filteredPatients = patientList.stream()
                    .filter(patient -> {
                        switch (filterOption) {
                            case "Name":
                                return patient.getName().toLowerCase().contains(searchText);
                            case "Username":
                                return patient.getUsername().toLowerCase().contains(searchText);
                            case "Blood Group":
                                return patient.getBloodGroup().toLowerCase().contains(searchText);
                            case "Gender":
                                return patient.getGender().toLowerCase().contains(searchText);
                            default:
                                return false;
                        }
                    })
                    .collect(Collectors.toList());

            loadPatientTable(filteredPatients);
        } else if (e.getSource() == backButton) {
            // Go back to admin page
            new AdminPage();
            dispose();
        }
    }

    public static void main(String[] args) {
        new ViewPatients();
    }
}