import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class DeletePatient extends JFrame implements ActionListener {

    private JLabel usernameLabel;
    private JTextField usernameField;
    private JButton deleteButton, backButton;

    public DeletePatient() {
        // Setting the frame properties
        setTitle("Delete Patient");
        setSize(600, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizing the window
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Panel to hold the form elements
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Delete Patient Form"));
        formPanel.setBackground(new Color(240, 248, 255)); // Light blue background

        gbc.insets = new Insets(10, 10, 10, 10);

        // Adding form elements
        usernameLabel = new JLabel("Enter Patient Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(usernameField, gbc);

        // Adding buttons
        deleteButton = createButton("Delete", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String patientUsername = usernameField.getText().trim();

                try {
                    File inputFile = new File("Patient.txt");
                    File tempFile = new File("temp.txt");
                    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
                    String line;
                    boolean deleted = false;
                    while ((line = reader.readLine()) != null) {
                        String[] data = line.split(",");
                        if (data[4].equals(patientUsername)) {
                            deleted = true;
                            continue;
                        }
                        writer.write(line + System.getProperty("line.separator"));
                    }
                    reader.close();
                    writer.close();
                    if (!deleted) {
                        JOptionPane.showMessageDialog(DeletePatient.this, "Patient not found.");
                    } else {
                        inputFile.delete();
                        tempFile.renameTo(inputFile);
                        JOptionPane.showMessageDialog(DeletePatient.this, "Patient deleted successfully.");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(deleteButton, gbc);

        backButton = createButton("Back", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AdminPage();
            }
        });
        gbc.gridx = 1;
        formPanel.add(backButton, gbc);

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
        button.setPreferredSize(new Dimension(200, 30));
        return button;
    }

    public void actionPerformed(ActionEvent e) {
        // Implementation of actionPerformed is now handled in the button creation
    }

    public static void main(String[] args) {
        new DeletePatient();
    }
}
