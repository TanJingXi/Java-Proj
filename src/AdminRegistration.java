import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.regex.*;

public class AdminRegistration extends JFrame implements ActionListener {
    private JLabel nameLabel, ageLabel, usernameLabel, passwordLabel;
    private JTextField nameTextField, ageTextField, usernameTextField;
    private JPasswordField passwordField;
    private JButton registerButton, backButton;

    public AdminRegistration() {
        // Setting the frame properties
        setTitle("Admin Registration");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizing the window
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Panel to hold the form elements
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Admin Registration Form"));
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

        usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(usernameLabel, gbc);

        usernameTextField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(usernameTextField, gbc);

        passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 3;
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
        gbc.gridy = 4;
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
            if (!name.matches("[a-zA-Z ]+")) {
                JOptionPane.showMessageDialog(this, "Name must contain only alphabetic characters.", "Invalid Name", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int age;
            try {
                age = Integer.parseInt(ageTextField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid age.", "Invalid Age", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String username = usernameTextField.getText();
            String password = new String(passwordField.getPassword());
            if (!isValidPassword(password)) {
                JOptionPane.showMessageDialog(this, "Password is too weak. It must be at least 8 characters long, contain at least one digit, one uppercase letter, one lowercase letter, and one special character.", "Weak Password", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Writing admin information to file
            try (PrintWriter writer = new PrintWriter(new FileWriter("Admin.txt", true))) {
                writer.println(name + "," + age + "," + username + "," + password);
                JOptionPane.showMessageDialog(this, "Admin registered successfully!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error writing to file: " + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
            }

            // Clearing input fields
            nameTextField.setText("");
            ageTextField.setText("");
            usernameTextField.setText("");
            passwordField.setText("");
        } else if (e.getSource() == backButton) {
            new HospitalHomepage();
            dispose();
        }
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 8) return false;
        boolean hasDigit = false;
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasSpecial = false;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) hasDigit = true;
            else if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isLowerCase(c)) hasLower = true;
            else if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }
        return hasDigit && hasUpper && hasLower && hasSpecial;
    }

    public static void main(String[] args) {
        new AdminRegistration();
    }
}