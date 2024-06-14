import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UpdatePatient extends JFrame implements ActionListener {
    private JLabel nameLabel, ageLabel, bloodGroupLabel, genderLabel, usernameLabel, passwordLabel;
    private JTextField nameTextField, ageTextField, bloodGroupTextField, usernameTextField;
    private JRadioButton maleRadioButton, femaleRadioButton;
    private ButtonGroup genderButtonGroup;
    private JPasswordField passwordField;
    private JButton updateButton, backButton;
    private JTable patientTable;
    private List<Patient> patients;
    private Patient selectedPatient;

    public UpdatePatient() {
        setTitle("Update Patient");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizing the window
        setLayout(new BorderLayout());

        patients = loadPatients();

        String[] columnNames = {"Name", "Age", "Blood Group", "Gender", "Username"};
        Object[][] data = new Object[patients.size()][5];
        for (int i = 0; i < patients.size(); i++) {
            Patient patient = patients.get(i);
            data[i][0] = patient.getName();
            data[i][1] = patient.getAge();
            data[i][2] = patient.getBloodGroup();
            data[i][3] = patient.getGender();
            data[i][4] = patient.getUsername();
        }

        patientTable = new JTable(data, columnNames);
        patientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        patientTable.getSelectionModel().addListSelectionListener(event -> {
            int selectedRow = patientTable.getSelectedRow();
            if (selectedRow >= 0) {
                selectedPatient = patients.get(selectedRow);
                loadPatientData(selectedPatient);
            }
        });

        JScrollPane scrollPane = new JScrollPane(patientTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Update Patient Form"));
        formPanel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        nameLabel = new JLabel("Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(nameLabel, gbc);

        nameTextField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(nameTextField, gbc);

        ageLabel = new JLabel("Age:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(ageLabel, gbc);

        ageTextField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(ageTextField, gbc);

        bloodGroupLabel = new JLabel("Blood Group:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(bloodGroupLabel, gbc);

        bloodGroupTextField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(bloodGroupTextField, gbc);

        genderLabel = new JLabel("Gender:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(genderLabel, gbc);

        maleRadioButton = new JRadioButton("Male");
        femaleRadioButton = new JRadioButton("Female");
        genderButtonGroup = new ButtonGroup();
        genderButtonGroup.add(maleRadioButton);
        genderButtonGroup.add(femaleRadioButton);

        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.add(maleRadioButton);
        genderPanel.add(femaleRadioButton);
        gbc.gridx = 1;
        formPanel.add(genderPanel, gbc);

        usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(usernameLabel, gbc);

        usernameTextField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(usernameTextField, gbc);

        passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(passwordField, gbc);

        updateButton = new JButton("Update");
        updateButton.addActionListener(this);
        updateButton.setBackground(new Color(135, 206, 250)); // Sky blue background
        updateButton.setForeground(Color.WHITE); // White text
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(updateButton, gbc);

        backButton = new JButton("Back");
        backButton.addActionListener(this);
        backButton.setBackground(new Color(255, 99, 71)); // Tomato background
        backButton.setForeground(Color.WHITE); // White text
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(backButton, gbc);

        add(formPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private List<Patient> loadPatients() {
        List<Patient> patients = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("Patient.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                String name = fields[0];
                int age = Integer.parseInt(fields[1]);
                String bloodGroup = fields[2];
                String gender = fields[3];
                String username = fields[4];
                String password = fields[5];
                patients.add(new Patient(name, age, bloodGroup, gender, username, password));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return patients;
    }

    private void loadPatientData(Patient patient) {
        nameTextField.setText(patient.getName());
        ageTextField.setText(String.valueOf(patient.getAge()));
        bloodGroupTextField.setText(patient.getBloodGroup());
        if (patient.getGender().equals("Male")) {
            maleRadioButton.setSelected(true);
        } else {
            femaleRadioButton.setSelected(true);
        }
        usernameTextField.setText(patient.getUsername());
        passwordField.setText(patient.getPassword());
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == updateButton) {
            if (selectedPatient != null) {
                selectedPatient.setName(nameTextField.getText());
                selectedPatient.setAge(Integer.parseInt(ageTextField.getText()));
                selectedPatient.setBloodGroup(bloodGroupTextField.getText());
                selectedPatient.setGender(maleRadioButton.isSelected() ? "Male" : "Female");
                selectedPatient.setUsername(usernameTextField.getText());
                selectedPatient.setPassword(new String(passwordField.getPassword()));

                savePatients();
                JOptionPane.showMessageDialog(this, "Patient information updated successfully!");
            }
        } else if (e.getSource() == backButton) {
            new AdminPage();
            dispose();
        }
    }

    private void savePatients() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("Patient.txt"))) {
            for (Patient patient : patients) {
                writer.println(patient.getName() + "," + patient.getAge() + "," + patient.getBloodGroup() + "," + patient.getGender() + "," + patient.getUsername() + "," + patient.getPassword());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new UpdatePatient();
    }
}
