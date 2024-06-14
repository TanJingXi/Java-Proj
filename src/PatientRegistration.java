import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class PatientRegistration extends JFrame implements ActionListener {

    private JLabel nameLabel, ageLabel, bgLabel, genderLabel, userLabel, passLabel;
    private JTextField nameField, ageField, userField;
    private JPasswordField passField;
    private JComboBox<String> bgComboBox;
    private JRadioButton maleRadio, femaleRadio;
    private ButtonGroup genderGroup;
    private JButton submitButton, backButton;

    public PatientRegistration() {
        super("Patient Registration");

        // Setting the frame properties
        setTitle("Patient Registration");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizing the window
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Panel to hold the form elements
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Patient Registration Form"));
        formPanel.setBackground(new Color(240, 248, 255)); // Light blue background

        gbc.insets = new Insets(10, 10, 10, 10);

        // Adding form elements
        nameLabel = new JLabel("Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(nameLabel, gbc);

        nameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(nameField, gbc);

        ageLabel = new JLabel("Age:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(ageLabel, gbc);

        ageField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(ageField, gbc);

        bgLabel = new JLabel("Blood Group:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(bgLabel, gbc);

        String[] bgOptions = {"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
        bgComboBox = new JComboBox<>(bgOptions);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(bgComboBox, gbc);

        genderLabel = new JLabel("Gender:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(genderLabel, gbc);

        maleRadio = new JRadioButton("Male");
        femaleRadio = new JRadioButton("Female");

        genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);

        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.add(maleRadio);
        genderPanel.add(femaleRadio);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(genderPanel, gbc);

        userLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(userLabel, gbc);

        userField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(userField, gbc);

        passLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(passLabel, gbc);

        passField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(passField, gbc);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        submitButton.setBackground(new Color(135, 206, 250)); // Sky blue background
        submitButton.setForeground(Color.WHITE); // White text
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(submitButton, gbc);

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

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String name = nameField.getText();
            int age;
            try {
                age = Integer.parseInt(ageField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid age.", "Invalid Age", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String bg = (String) bgComboBox.getSelectedItem();
            String gender = null;
            if (maleRadio.isSelected()) {
                gender = "Male";
            } else if (femaleRadio.isSelected()) {
                gender = "Female";
            } else {
                JOptionPane.showMessageDialog(this, "Please select a gender.", "No Gender Selected", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String username = userField.getText();
            String password = new String(passField.getPassword());

            try (PrintWriter writer = new PrintWriter(new FileWriter("Patient.txt", true))) {
                writer.println(name + "," + age + "," + bg + "," + gender + "," + username + "," + password);
                JOptionPane.showMessageDialog(this, "Patient registered successfully!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error writing to file: " + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
            }

            // Clearing input fields
            nameField.setText("");
            ageField.setText("");
            userField.setText("");
            passField.setText("");
            genderGroup.clearSelection();
        } else if (e.getSource() == backButton) {
            new HospitalHomepage();
            dispose();
        }
    }

    public static void main(String[] args) {
        new PatientRegistration();
    }
}
