import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ViewDoctors extends JFrame implements ActionListener {
    private JTable doctorTable;
    private JTextField searchField;
    private JComboBox<String> filterComboBox;
    private JButton backButton;
    private List<Doctor> doctorList;
    private DefaultTableModel tableModel;

    public ViewDoctors() {
        // Read all the files first
        doctorList = loadDoctors();

        // Set up the JFrame
        setTitle("View Doctors");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Panel to hold form elements
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("View Doctors"));
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

        String[] filterOptions = {"Name", "Username", "Gender", "Qualification"};
        filterComboBox = new JComboBox<>(filterOptions);
        gbc.gridx = 2;
        formPanel.add(filterComboBox, gbc);

        // Adding doctor table
        String[] columnNames = {"Name", "Age", "Qualification", "Gender", "Username"};
        tableModel = new DefaultTableModel(columnNames, 0);
        doctorTable = new JTable(tableModel);
        doctorTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Populate the table with doctor data
        loadDoctorTable(doctorList);

        JScrollPane scrollPane = new JScrollPane(doctorTable);
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

    private List<Doctor> loadDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("Doctor.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 6) {
                    Doctor doctor = new Doctor(fields[0], Integer.parseInt(fields[1]), fields[2], fields[3], fields[4], fields[5]);
                    doctors.add(doctor);
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error loading doctors: " + ex.getMessage());
            ex.printStackTrace();
        }
        return doctors;
    }

    private void loadDoctorTable(List<Doctor> doctors) {
        tableModel.setRowCount(0);
        for (Doctor doctor : doctors) {
            Object[] row = {doctor.getName(), doctor.getAge(), doctor.getQualification(), doctor.getGender(), doctor.getUsername()};
            tableModel.addRow(row);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchField) {
            String searchText = searchField.getText().toLowerCase();
            String filterOption = (String) filterComboBox.getSelectedItem();

            List<Doctor> filteredDoctors = doctorList.stream()
                    .filter(doctor -> {
                        switch (filterOption) {
                            case "Name":
                                return doctor.getName().toLowerCase().contains(searchText);
                            case "Username":
                                return doctor.getUsername().toLowerCase().contains(searchText);
                            case "Gender":
                                return doctor.getGender().toLowerCase().contains(searchText);
                            case "Qualification":
                                return doctor.getQualification().toLowerCase().contains(searchText);
                            default:
                                return false;
                        }
                    })
                    .collect(Collectors.toList());

            loadDoctorTable(filteredDoctors);
        } else if (e.getSource() == backButton) {
            // Go back to admin page
            new AdminPage();
            dispose();
        }
    }

    public static void main(String[] args) {
        new ViewDoctors();
    }
}
