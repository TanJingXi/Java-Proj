import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class DeleteDoctor extends JFrame implements ActionListener {
    private JLabel usernameLabel;
    private JTextField usernameField;
    private JButton deleteButton, backButton;

    public DeleteDoctor() {
        // Setting the frame properties
        setTitle("Delete Doctor");
        setSize(600, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizing the window
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Menu bar setup
        JMenuBar menuBar = new JMenuBar();
        JMenu adminMenu = new JMenu("Admin Options");

        JMenuItem deletePatientItem = new JMenuItem("Delete Patient");
        deletePatientItem.addActionListener(e -> {
            dispose();
            new DeletePatient();
        });
        adminMenu.add(deletePatientItem);

        JMenuItem deleteDoctorItem = new JMenuItem("Delete Doctor");
        deleteDoctorItem.addActionListener(e -> {
            dispose();
            new DeleteDoctor();
        });
        adminMenu.add(deleteDoctorItem);

        JMenuItem updateDoctorItem = new JMenuItem("Update Doctor");
        updateDoctorItem.addActionListener(e -> {
            dispose();
            new UpdateDoctor();
        });
        adminMenu.add(updateDoctorItem);

        JMenuItem updatePatientItem = new JMenuItem("Update Patient");
        updatePatientItem.addActionListener(e -> {
            dispose();
            new UpdatePatient();
        });
        adminMenu.add(updatePatientItem);

        JMenuItem backItem = new JMenuItem("Back");
        backItem.addActionListener(e -> {
            dispose();
            new AdminPage();
        });
        adminMenu.add(backItem);

        menuBar.add(adminMenu);
        setJMenuBar(menuBar);

        // Panel to hold the form elements
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Delete Doctor Form"));
        formPanel.setBackground(new Color(240, 248, 255)); // Light blue background

        gbc.insets = new Insets(10, 10, 10, 10);

        // Adding form elements
        usernameLabel = new JLabel("Enter Doctor Username:");
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
                String username = usernameField.getText().trim();

                if (username.isEmpty()) {
                    JOptionPane.showMessageDialog(DeleteDoctor.this, "Please enter a valid username.");
                    return;
                }

                try {
                    File inputFile = new File("Doctor.txt");
                    File tempFile = new File("temp.txt");
                    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
                    String line;
                    boolean found = false;

                    while ((line = reader.readLine()) != null) {
                        String[] doctorData = line.split(",");
                        if (!doctorData[4].equals(username)) {
                            writer.write(line + System.getProperty("line.separator"));
                        } else {
                            found = true;
                        }
                    }
                    reader.close();
                    writer.close();

                    if (found) {
                        inputFile.delete();
                        tempFile.renameTo(new File("Doctor.txt"));
                        JOptionPane.showMessageDialog(DeleteDoctor.this, "Doctor information deleted successfully.");
                    } else {
                        JOptionPane.showMessageDialog(DeleteDoctor.this, "Doctor with username '" + username + "' not found.");
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
        new DeleteDoctor();
    }
}
