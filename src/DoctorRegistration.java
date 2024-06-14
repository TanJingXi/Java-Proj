import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class DoctorRegistration extends JFrame implements ActionListener {
    private JLabel nameLabel, ageLabel, qualificationLabel, genderLabel, usernameLabel, passwordLabel;
    private JTextField nameTextField, ageTextField, qualificationTextField, usernameTextField;
    private JRadioButton maleRadioButton, femaleRadioButton;
    private ButtonGroup genderButtonGroup;
    private JPasswordField passwordField;
    private JButton registerButton, backButton;

    public DoctorRegistration() {
        // Setting the frame properties
        setTitle("Doctor Registration");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizing the window
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Panel to hold the form elements
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Doctor Registration Form"));
        formPanel.setBackground(new Color(240, 248, 255)); // Light blue background

        gbc.insets = new Insets(10, 10, 10, 10);

        // Adding form elements
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

        JPanel genderPanel = new JPanel();
        genderPanel.setBackground(new Color(240, 248, 255));
        genderPanel.add(maleRadioButton);
        genderPanel.add(femaleRadioButton);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
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

        registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        registerButton.setBackground(new Color(135, 206, 250)); // Sky blue background
        registerButton.setForeground(Color.WHITE); // White text
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(registerButton, gbc);

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

    // Action performed when a button is clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            String name = nameTextField.getText();
            int age;
            try {
                age = Integer.parseInt(ageTextField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid age.", "Invalid Age", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String qualification = qualificationTextField.getText();
            String gender = maleRadioButton.isSelected() ? "Male" : femaleRadioButton.isSelected() ? "Female" : null;
            if (gender == null) {
                JOptionPane.showMessageDialog(this, "Please select a gender.", "Gender Required", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String username = usernameTextField.getText();
            String password = new String(passwordField.getPassword());

            // Writing doctor information to file
            try (PrintWriter writer = new PrintWriter(new FileWriter("Doctor.txt", true))) {
                writer.println(name + "," + age + "," + qualification + "," + gender + "," + username + "," + password);
                JOptionPane.showMessageDialog(this, "Registration successful!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error writing to file: " + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
            }

            // Clearing input fields
            nameTextField.setText("");
            ageTextField.setText("");
            qualificationTextField.setText("");
            genderButtonGroup.clearSelection();
            usernameTextField.setText("");
            passwordField.setText("");
        } else if (e.getSource() == backButton) {
            new HospitalHomepage();
            dispose();
        }
    }

    public static void main(String[] args) {
        new DoctorRegistration();
    }
}
