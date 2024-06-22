import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UpdateDoctor extends JFrame implements ActionListener {
    private JLabel nameLabel, ageLabel, qualificationLabel, genderLabel, usernameLabel, passwordLabel;
    private JTextField nameTextField, ageTextField, qualificationTextField, usernameTextField;
    private JRadioButton maleRadioButton, femaleRadioButton;
    private ButtonGroup genderButtonGroup;
    private JPasswordField passwordField;
    private JButton updateButton, backButton;
    private JTable doctorTable;
    private List<Doctor> doctors;
    private Doctor selectedDoctor;

    public UpdateDoctor() {
        setTitle("Update Doctor");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizing the window
        setLayout(new BorderLayout());

        doctors = loadDoctors();

        String[] columnNames = {"Name", "Age", "Qualification", "Gender", "Username"};
        Object[][] data = new Object[doctors.size()][5];
        for (int i = 0; i < doctors.size(); i++) {
            Doctor doctor = doctors.get(i);
            data[i][0] = doctor.getName();
            data[i][1] = doctor.getAge();
            data[i][2] = doctor.getQualification();
            data[i][3] = doctor.getGender();
            data[i][4] = doctor.getUsername();
        }

        doctorTable = new JTable(data, columnNames);
        doctorTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        doctorTable.getSelectionModel().addListSelectionListener(event -> {
            int selectedRow = doctorTable.getSelectedRow();
            if (selectedRow >= 0) {
                selectedDoctor = doctors.get(selectedRow);
                loadDoctorData(selectedDoctor);
            }
        });

        JScrollPane scrollPane = new JScrollPane(doctorTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Update Doctor Form"));
        formPanel.setBackground(new Color(240, 248, 255)); // Light blue background
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

        qualificationLabel = new JLabel("Qualification:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(qualificationLabel, gbc);

        qualificationTextField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(qualificationTextField, gbc);

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

    private List<Doctor> loadDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("Doctor.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                String name = fields[0];
                int age = Integer.parseInt(fields[1]);
                String qualification = fields[2];
                String gender = fields[3];
                String username = fields[4];
                String password = fields[5];
                doctors.add(new Doctor(name, age, qualification, gender, username, password));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doctors;
    }

    private void loadDoctorData(Doctor doctor) {
        nameTextField.setText(doctor.getName());
        ageTextField.setText(String.valueOf(doctor.getAge()));
        qualificationTextField.setText(doctor.getQualification());
        if (doctor.getGender().equals("Male")) {
            maleRadioButton.setSelected(true);
        } else {
            femaleRadioButton.setSelected(true);
        }
        usernameTextField.setText(doctor.getUsername());
        passwordField.setText(doctor.getPassword());
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == updateButton) {
            if (selectedDoctor != null) {
                if (validateFields()) {
                    selectedDoctor.setName(nameTextField.getText());
                    selectedDoctor.setAge(Integer.parseInt(ageTextField.getText()));
                    selectedDoctor.setQualification(qualificationTextField.getText());
                    selectedDoctor.setGender(maleRadioButton.isSelected() ? "Male" : "Female");
                    selectedDoctor.setUsername(usernameTextField.getText());
                    selectedDoctor.setPassword(new String(passwordField.getPassword()));

                    saveDoctors();
                    JOptionPane.showMessageDialog(this, "Doctor information updated successfully!");
                }
            }
        } else if (e.getSource() == backButton) {
            new AdminPage();
            dispose();
        }
    }

    private boolean validateFields() {
        String name = nameTextField.getText().trim();
        String qualification = qualificationTextField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (!name.matches("[a-zA-Z ]+")) {
            JOptionPane.showMessageDialog(this, "Name must contain only alphabetic characters.");
            return false;
        }

        if (!qualification.matches("[a-zA-Z ]+")) {
            JOptionPane.showMessageDialog(this, "Qualification must contain only alphabetic characters.");
            return false;
        }

        if (!isStrongPassword(password)) {
            JOptionPane.showMessageDialog(this, "Password is too weak. It must be at least 8 characters long and contain a mix of upper and lower case letters, numbers, and special characters.");
            return false;
        }

        return true;
    }

    private boolean isStrongPassword(String password) {
        if (password.length() < 8) return false;
        boolean hasUpper = false, hasLower = false, hasDigit = false, hasSpecial = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            if (Character.isLowerCase(c)) hasLower = true;
            if (Character.isDigit(c)) hasDigit = true;
            if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }
        return hasUpper && hasLower && hasDigit && hasSpecial;
    }

    private void saveDoctors() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("Doctor.txt"))) {
            for (Doctor doctor : doctors) {
                writer.println(doctor.getName() + "," + doctor.getAge() + "," + doctor.getQualification() + "," + doctor.getGender() + "," + doctor.getUsername() + "," + doctor.getPassword());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new UpdateDoctor();
    }
}
