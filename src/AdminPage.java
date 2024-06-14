import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class AdminPage extends JFrame {

    public AdminPage() {
        // Setting the frame properties
        setTitle("Admin Page");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizing the window
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Panel to hold the buttons
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Admin Options"));
        panel.setBackground(new Color(240, 248, 255)); // Light blue background

        gbc.insets = new Insets(10, 10, 10, 10);

        // Adding buttons
        JButton deletePatientButton = createButton("Delete Patient", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new DeletePatient();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(deletePatientButton, gbc);

        JButton deleteDoctorButton = createButton("Delete Doctor", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new DeleteDoctor();
            }
        });
        gbc.gridy = 1;
        panel.add(deleteDoctorButton, gbc);

        JButton updateDoctorButton = createButton("Update Doctor", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new UpdateDoctor();
            }
        });
        gbc.gridy = 2;
        panel.add(updateDoctorButton, gbc);

        JButton updatePatientButton = createButton("Update Patient", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new UpdatePatient();
            }
        });
        gbc.gridy = 3;
        panel.add(updatePatientButton, gbc);

        JButton backButton = createButton("Back", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current window
                new HospitalHomepage(); // Open the HospitalHomepage
            }
        });
        gbc.gridy = 4;
        panel.add(backButton, gbc);

        // Adding the panel to the frame
        add(panel);

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

    public static void main(String[] args) {
        new AdminPage();
    }
}
