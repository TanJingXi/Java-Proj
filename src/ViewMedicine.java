import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ViewMedicine extends JFrame implements ActionListener {
    private JTable medicineTable;
    private JTextField searchField;
    private JComboBox<String> filterComboBox;
    private JButton backButton;
    private List<Medicine> medicineList;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> rowSorter;

    public ViewMedicine() {
        // Load all the medicine records first
        medicineList = loadMedicines();

        // Set up the JFrame
        setTitle("View Medicines");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Panel to hold form elements
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("View Medicines"));
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

        String[] filterOptions = {"Doctor", "Patient", "Disease", "Medicine 1", "Medicine 2", "Medicine 3", "Date"};
        filterComboBox = new JComboBox<>(filterOptions);
        gbc.gridx = 2;
        formPanel.add(filterComboBox, gbc);

        // Adding medicine table
        String[] columnNames = {"Doctor", "Patient", "Disease", "Medicine 1", "Medicine 2", "Medicine 3", "Date"};
        tableModel = new DefaultTableModel(columnNames, 0);
        medicineTable = new JTable(tableModel);
        medicineTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rowSorter = new TableRowSorter<>(tableModel);
        medicineTable.setRowSorter(rowSorter);

        // Populate the table with medicine data
        loadMedicineTable(medicineList);

        JScrollPane scrollPane = new JScrollPane(medicineTable);
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

    private List<Medicine> loadMedicines() {
        List<Medicine> medicines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("Medicine.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 7) {
                    Medicine medicine = new Medicine(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5], fields[6]);
                    medicines.add(medicine);
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error loading medicines: " + ex.getMessage());
            ex.printStackTrace();
        }
        return medicines;
    }

    private void loadMedicineTable(List<Medicine> medicines) {
        tableModel.setRowCount(0);
        for (Medicine medicine : medicines) {
            Object[] row = {
                medicine.getDoctorUsername(),
                medicine.getPatientUsername(),
                medicine.getDisease(),
                medicine.getMedicine1(),
                medicine.getMedicine2(),
                medicine.getMedicine3(),
                medicine.getDate()
            };
            tableModel.addRow(row);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchField) {
            String searchText = searchField.getText().toLowerCase();
            String filterOption = (String) filterComboBox.getSelectedItem();

            List<Medicine> filteredMedicines = medicineList.stream()
                .filter(medicine -> {
                    switch (filterOption) {
                        case "Doctor":
                            return medicine.getDoctorUsername().toLowerCase().contains(searchText);
                        case "Patient":
                            return medicine.getPatientUsername().toLowerCase().contains(searchText);
                        case "Disease":
                            return medicine.getDisease().toLowerCase().contains(searchText);
                        case "Medicine 1":
                            return medicine.getMedicine1().toLowerCase().contains(searchText);
                        case "Medicine 2":
                            return medicine.getMedicine2().toLowerCase().contains(searchText);
                        case "Medicine 3":
                            return medicine.getMedicine3().toLowerCase().contains(searchText);
                        case "Date":
                            return medicine.getDate().toLowerCase().contains(searchText);
                        default:
                            return false;
                    }
                })
                .collect(Collectors.toList());

            loadMedicineTable(filteredMedicines);
        } else if (e.getSource() == backButton) {
            // Go back to admin page
            new AdminPage();
            dispose();
        }
    }

    public static void main(String[] args) {
        new ViewMedicine();
    }
}

class Medicine {
    private String doctorUsername;
    private String patientUsername;
    private String disease;
    private String medicine1;
    private String medicine2;
    private String medicine3;
    private String date;

    public Medicine(String doctorUsername, String patientUsername, String disease, String medicine1, String medicine2, String medicine3, String date) {
        this.doctorUsername = doctorUsername;
        this.patientUsername = patientUsername;
        this.disease = disease;
        this.medicine1 = medicine1;
        this.medicine2 = medicine2;
        this.medicine3 = medicine3;
        this.date = date;
    }

    public String getDoctorUsername() {
        return doctorUsername;
    }

    public String getPatientUsername() {
        return patientUsername;
    }

    public String getDisease() {
        return disease;
    }

    public String getMedicine1() {
        return medicine1;
    }

    public String getMedicine2() {
        return medicine2;
    }

    public String getMedicine3() {
        return medicine3;
    }

    public String getDate() {
        return date;
    }
}
