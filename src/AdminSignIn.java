import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class AdminSignIn extends JFrame implements ActionListener {
    private JLabel userLabel, passwordLabel, messageLabel, imageLabel;
    private JTextField userField;
    private JPasswordField passwordField;
    private JButton loginButton, backButton;

    public AdminSignIn() {
        // Setting the frame properties
        setTitle("Admin Sign In");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizing the window
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Panel to hold the form elements
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Admin Sign In"));
        formPanel.setBackground(new Color(240, 248, 255)); // Light blue background

        gbc.insets = new Insets(10, 10, 10, 10);

        // Adding form elements
        userLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(userLabel, gbc);

        userField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(userField, gbc);

        passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(passwordField, gbc);

        messageLabel = new JLabel();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(messageLabel, gbc);

        // Adding buttons
        loginButton = createButton("Login", this);
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(loginButton, gbc);

        backButton = createButton("Back", this);
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(backButton, gbc);

        // Load the image and resize it
        ImageIcon originalIcon = new ImageIcon("img/admin.png"); // Replace "admin.png" with the path to your image file
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Resize to 100x100 pixels
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        imageLabel = new JLabel(resizedIcon);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2; // Span across two columns
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(imageLabel, gbc);

        // Adding form panel to the frame
        add(formPanel);

        // Setting the frame visible
        setVisible(true);
    }

    private JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        button.setBackground(new Color(135, 206, 250)); // Sky blue background
        button.setForeground(Color.WHITE); // White text
        button.setPreferredSize(new Dimension(100, 30));
        return button;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = userField.getText().trim();
            String password = new String(passwordField.getPassword());

            // Check credentials
            boolean success = false;
            try (BufferedReader reader = new BufferedReader(new FileReader("Admin.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] fields = line.split(",");
                    String storedUsername = fields[2].trim();
                    String storedPassword = fields[3].trim();
                    if (storedUsername.equals(username) && storedPassword.equals(password)) {
                        success = true;
                        break;
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            // Handle login success
            if (success) {
                messageLabel.setText("Login successful!");
                dispose();
                new AdminPage();
            } else {
                messageLabel.setText("Invalid username or password.");
            }
        } else if (e.getSource() == backButton) {
            dispose();
            new HospitalHomepage();
        }
    }

    public static void main(String[] args) {
        new AdminSignIn();
    }
}
