import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ViewAllAppointments extends JFrame implements ActionListener {
    private JTable appointmentTable;
    private JTextField searchField;
    private JComboBox<String> filterComboBox;
    private JButton backButton;
    private List<Appointment> appointmentList;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> rowSorter;

    public ViewAllAppointments() {
        // Read all the files first
        appointmentList = loadAppointments();

        // Set up the JFrame
        setTitle("View Appointments");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Panel to hold form elements
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("View Appointments"));
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

        String[] filterOptions = {"Doctor", "Patient", "Date", "Time"};
        filterComboBox = new JComboBox<>(filterOptions);
        gbc.gridx = 2;
        formPanel.add(filterComboBox, gbc);

        // Adding appointment table
        String[] columnNames = {"Doctor", "Patient", "Date", "Time"};
        tableModel = new DefaultTableModel(columnNames, 0);
        appointmentTable = new JTable(tableModel);
        appointmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rowSorter = new TableRowSorter<>(tableModel);
        appointmentTable.setRowSorter(rowSorter);

        // Populate the table with appointment data
        loadAppointmentTable(appointmentList);

        JScrollPane scrollPane = new JScrollPane(appointmentTable);
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

    private List<Appointment> loadAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("Appointment.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 4) {
                    Appointment appointment = new Appointment(fields[0], fields[1], fields[2], fields[3]);
                    appointments.add(appointment);
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error loading appointments: " + ex.getMessage());
            ex.printStackTrace();
        }
        return appointments;
    }

    private void loadAppointmentTable(List<Appointment> appointments) {
        tableModel.setRowCount(0);
        for (Appointment appointment : appointments) {
            Object[] row = {appointment.getDoctor(), appointment.getPatient(), appointment.getDate(), appointment.getTime()};
            tableModel.addRow(row);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchField) {
            String searchText = searchField.getText().toLowerCase();
            String filterOption = (String) filterComboBox.getSelectedItem();

            List<Appointment> filteredAppointments = appointmentList.stream()
                    .filter(appointment -> {
                        switch (filterOption) {
                            case "Doctor":
                                return appointment.getDoctor().toLowerCase().contains(searchText);
                            case "Patient":
                                return appointment.getPatient().toLowerCase().contains(searchText);
                            case "Date":
                                return appointment.getDate().toLowerCase().contains(searchText);
                            case "Time":
                                return appointment.getTime().toLowerCase().contains(searchText);
                            default:
                                return false;
                        }
                    })
                    .collect(Collectors.toList());

            loadAppointmentTable(filteredAppointments);
        } else if (e.getSource() == backButton) {
            // Go back to admin page
            new AdminPage();
            dispose();
        }
    }

    public static void main(String[] args) {
        new ViewAllAppointments();
    }
}

class Appointment {
    private String doctor;
    private String patient;
    private String date;
    private String time;

    public Appointment(String doctor, String patient, String date, String time) {
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.time = time;
    }

    public String getDoctor() {
        return doctor;
    }

    public String getPatient() {
        return patient;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}