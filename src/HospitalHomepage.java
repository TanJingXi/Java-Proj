import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.border.EmptyBorder;

public class HospitalHomepage extends JFrame implements ActionListener {

    JLabel nameLabel, addressLabel, name1Label, name2Label, name3Label, name4Label;
    JButton aboutBtn, doctorRegBtn, patientRegBtn, adminRegBtn, doctorSignBtn, patientSignBtn, adminSignBtn, exitBtn;

    public HospitalHomepage() {

        nameLabel = new JLabel("Medical Record System", JLabel.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        addressLabel = new JLabel("Group JavaFun", JLabel.CENTER);
        addressLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        name1Label = new JLabel("Tan Jing Xi", JLabel.CENTER);
        name1Label.setFont(new Font("Arial", Font.PLAIN, 18));
        name2Label = new JLabel("Samuel Fung Wen Kai", JLabel.CENTER);
        name2Label.setFont(new Font("Arial", Font.PLAIN, 18));
        name3Label = new JLabel("Chong Chen Jet", JLabel.CENTER);
        name3Label.setFont(new Font("Arial", Font.PLAIN, 18));
        name4Label = new JLabel("Tan Zhi Yong", JLabel.CENTER);
        name4Label.setFont(new Font("Arial", Font.PLAIN, 18));

        aboutBtn = new JButton("About Page");
        doctorRegBtn = new JButton("Doctor Registration");
        patientRegBtn = new JButton("Patient Registration");
        adminRegBtn = new JButton("Admin Registration");
        doctorSignBtn = new JButton("Doctor Sign-in");
        patientSignBtn = new JButton("Patient Sign-in");
        adminSignBtn = new JButton("Admin Sign-in");

        // Create the exit button with a resized icon
        exitBtn = new JButton();
        try {
            Image img = ImageIO.read(new File("img/logout.png"));
            Image resizedImg = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            exitBtn.setIcon(new ImageIcon(resizedImg));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        exitBtn.addActionListener(this);

        aboutBtn.addActionListener(this);
        doctorRegBtn.addActionListener(this);
        patientRegBtn.addActionListener(this);
        adminRegBtn.addActionListener(this);
        doctorSignBtn.addActionListener(this);
        patientSignBtn.addActionListener(this);
        adminSignBtn.addActionListener(this);

        JPanel headerPanel = new JPanel(new GridLayout(6, 1));
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(100, 0, 0, 0)); // Add top padding to lower the labels
        headerPanel.add(nameLabel);
        headerPanel.add(addressLabel);
        headerPanel.add(name1Label);
        headerPanel.add(name2Label);
        headerPanel.add(name3Label);
        headerPanel.add(name4Label);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(aboutBtn, gbc);

        gbc.gridy++;
        buttonPanel.add(doctorRegBtn, gbc);

        gbc.gridy++;
        buttonPanel.add(patientRegBtn, gbc);

        gbc.gridy++;
        buttonPanel.add(adminRegBtn, gbc);

        gbc.gridy++;
        buttonPanel.add(doctorSignBtn, gbc);

        gbc.gridy++;
        buttonPanel.add(patientSignBtn, gbc);

        gbc.gridy++;
        buttonPanel.add(adminSignBtn, gbc);

        // Add the exit button at the end
        gbc.gridy++;
        buttonPanel.add(exitBtn, gbc);

        BackgroundPanel backgroundPanel = new BackgroundPanel("img/hospital.png");
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.add(headerPanel, BorderLayout.NORTH);
        backgroundPanel.add(buttonPanel, BorderLayout.CENTER);

        setContentPane(backgroundPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Hospital Page");

        // Maximize the frame
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == aboutBtn) {
            JOptionPane.showMessageDialog(this, "Java Project");
        } else if (e.getSource() == doctorRegBtn) {
            dispose();
            // Ensure these classes are defined
            new DoctorRegistration();
        } else if (e.getSource() == patientRegBtn) {
            dispose();
            // Ensure these classes are defined
            new PatientRegistration();
        } else if (e.getSource() == adminRegBtn) {
            dispose();
            // Ensure these classes are defined
            new AdminRegistration();
        } else if (e.getSource() == doctorSignBtn) {
            dispose();
            // Ensure these classes are defined
            new DoctorSignIn();
        } else if (e.getSource() == patientSignBtn) {
            dispose();
            // Ensure these classes are defined
            new PatientSignIn();
        } else if (e.getSource() == adminSignBtn) {
            dispose();
            // Ensure these classes are defined
            new AdminSignIn();
        } else if (e.getSource() == exitBtn) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new HospitalHomepage();
    }
}

class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String filePath) {
        try {
            backgroundImage = ImageIO.read(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
