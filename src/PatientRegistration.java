import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.regex.*;

public class PatientRegistration extends UserRegistration {
    private JLabel bgLabel;
    private JComboBox<String> bgComboBox;

    public PatientRegistration() {
        super("Patient Registration");

        // Additional form elements specific to Patient
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;

        bgLabel = new JLabel("Blood Group:");
        formPanel.add(bgLabel, gbc);

        String[] bgOptions = {"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
        bgComboBox = new JComboBox<>(bgOptions);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(bgComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(submitButton, gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(backButton, gbc);

        setVisible(true);
    }

    @Override
    protected void registerUser() {
        String name = nameField.getText();
        if (!name.matches("[a-zA-Z ]+")) {
            JOptionPane.showMessageDialog(this, "Name must contain only alphabetic characters.", "Invalid Name", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageField.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid age.", "Invalid Age", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String bg = (String) bgComboBox.getSelectedItem();
        String gender = maleRadio.isSelected() ? "Male" : femaleRadio.isSelected() ? "Female" : null;
        if (gender == null) {
            JOptionPane.showMessageDialog(this, "Please select a gender.", "No Gender Selected", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String username = userField.getText();
        String password = new String(passField.getPassword());
        if (!isValidPassword(password)) {
            JOptionPane.showMessageDialog(this, "Password is too weak. It must be at least 8 characters long, contain at least one digit, one uppercase letter, one lowercase letter, and one special character.", "Weak Password", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter("Patient.txt", true))) {
            writer.println(name + "," + age + "," + bg + "," + gender + "," + username + "," + password);
            JOptionPane.showMessageDialog(this, "Patient registered successfully!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error writing to file: " + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
        }

        clearFields();
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

    @Override
    protected void clearFields() {
        nameField.setText("");
        ageField.setText("");
        userField.setText("");
        passField.setText("");
        bgComboBox.setSelectedIndex(0);
        genderGroup.clearSelection();
    }

    public static void main(String[] args) {
        new PatientRegistration();
    }
}