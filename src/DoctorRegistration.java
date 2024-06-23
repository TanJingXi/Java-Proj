import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.regex.*;

public class DoctorRegistration extends UserRegistration {
    private JLabel qualificationLabel;
    private JTextField qualificationField;

    public DoctorRegistration() {
        super("Doctor Registration");

        // Additional form elements specific to Doctor
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;

        qualificationLabel = new JLabel("Qualification:");
        formPanel.add(qualificationLabel, gbc);

        qualificationField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(qualificationField, gbc);

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

        String qualification = qualificationField.getText();
        if (!qualification.matches("[a-zA-Z ]+")) {
            JOptionPane.showMessageDialog(this, "Qualification must contain only alphabetic characters.", "Invalid Qualification", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String gender = maleRadio.isSelected() ? "Male" : femaleRadio.isSelected() ? "Female" : null;
        if (gender == null) {
            JOptionPane.showMessageDialog(this, "Please select a gender.", "Gender Required", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String username = userField.getText();
        if (isUsernameTaken(username, "Doctor.txt")) {
            JOptionPane.showMessageDialog(this, "Username is already taken. Please choose another one.", "Username Taken", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String password = new String(passField.getPassword());
        if (!isValidPassword(password)) {
            JOptionPane.showMessageDialog(this, "Password is too weak. It must be at least 8 characters long, contain at least one digit, one uppercase letter, one lowercase letter, and one special character.", "Weak Password", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter("Doctor.txt", true))) {
            writer.println(name + "," + age + "," + qualification + "," + gender + "," + username + "," + password);
            JOptionPane.showMessageDialog(this, "Doctor registered successfully!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error writing to file: " + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
        }

        clearFields();
    }

    private boolean isUsernameTaken(String username, String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[4].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
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
        qualificationField.setText("");
        genderGroup.clearSelection();
    }

    public static void main(String[] args) {
        new DoctorRegistration();
    }
}