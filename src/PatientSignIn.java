import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class PatientSignIn extends JFrame implements ActionListener {

    private JLabel usernameLabel, passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton signInButton, backButton;

    public PatientSignIn() {
        super("Patient Sign In");

        // Setting the frame properties
        setTitle("Patient Sign In");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizing the window
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Panel to hold the form elements
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Patient Sign In Form"));
        formPanel.setBackground(new Color(240, 248, 255)); // Light blue background

        gbc.insets = new Insets(10, 10, 10, 10);

        // Adding form elements
        usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(usernameField, gbc);

        passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(passwordField, gbc);

        signInButton = new JButton("Sign In");
        signInButton.addActionListener(this);
        signInButton.setBackground(new Color(135, 206, 250)); // Sky blue background
        signInButton.setForeground(Color.WHITE); // White text
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(signInButton, gbc);

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

    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();
        if (action.equals("Sign In")) {
            String username = usernameField.getText();
            char[] passwordChars = passwordField.getPassword();
            String password = new String(passwordChars);
            boolean validLogin = false;

            try (BufferedReader br = new BufferedReader(new FileReader("Patient.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] fields = line.split(",");
                    String uniqueUsername = fields[4];
                    String uniquePassword = fields[5];
                    if (uniqueUsername.equals(username) && uniquePassword.equals(password)) {
                        validLogin = true;
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (validLogin) {
                dispose();
                new PatientPage(username).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.");
            }
        } else if (action.equals("Back")) {
            dispose();
            new HospitalHomepage().setVisible(true);
        }
    }

    public static void main(String[] args) {
        new PatientSignIn();
    }
}
