import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DeleteDoctor extends JFrame implements ActionListener {
    private JTable doctorTable;
    private JButton deleteButton, backButton;
    private List<Doctor> doctorList;
    private DefaultTableModel tableModel;

    public DeleteDoctor() {
        setTitle("Delete Doctor");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Load doctors from the file
        doctorList = loadDoctors();

        // Panel to hold form elements
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Delete Doctor"));
        formPanel.setBackground(new Color(240, 248, 255));

        gbc.insets = new Insets(10, 10, 10, 10);

        // Adding doctor table
        String[] columnNames = {"Name", "Age", "Qualification", "Gender", "Username"};
        tableModel = new DefaultTableModel(columnNames, 0);
        doctorTable = new JTable(tableModel);
        doctorTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Populate the table with doctor data
        loadDoctorTable();

        JScrollPane scrollPane = new JScrollPane(doctorTable);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        formPanel.add(scrollPane, gbc);

        deleteButton = new JButton("Delete Doctor");
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

    private void loadDoctorTable() {
        for (Doctor doctor : doctorList) {
            Object[] row = {doctor.getName(), doctor.getAge(), doctor.getQualification(), doctor.getGender(), doctor.getUsername()};
            tableModel.addRow(row);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == deleteButton) {
            int selectedRow = doctorTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a doctor from the table");
                return;
            }

            String username = (String) tableModel.getValueAt(selectedRow, 4);
            deleteDoctor(username);
            tableModel.removeRow(selectedRow);

            JOptionPane.showMessageDialog(this, "Doctor deleted successfully");

        } else if (e.getSource() == backButton) {
            // Go back to admin page
            new AdminPage();
            dispose();
        }
    }

    private void deleteDoctor(String username) {
        doctorList.removeIf(doctor -> doctor.getUsername().equals(username));
        saveDoctors();
    }

    private void saveDoctors() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Doctor.txt"))) {
            for (Doctor doctor : doctorList) {
                writer.write(doctor.getName() + "," + doctor.getAge() + "," + doctor.getQualification() + "," + doctor.getGender() + "," + doctor.getUsername() + "," + doctor.getPassword());
                writer.newLine();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving doctors: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new DeleteDoctor();
    }
}