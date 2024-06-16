import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;

public class SeeAppointment extends JFrame {
    private String doctorUsername;
    private JTextArea appointmentList;

    public SeeAppointment(String username) {
        super("Appointments for " + username);
        this.doctorUsername = username;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the window on startup

        JPanel mainPanel = new JPanel(new BorderLayout());

        appointmentList = new JTextArea(10, 30);
        appointmentList.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(appointmentList);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        try {
            displayAppointments();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        getContentPane().add(mainPanel);
        setVisible(true);
    }

    private void displayAppointments() throws IOException {
        String line;
        BufferedReader reader = new BufferedReader(new FileReader("Appointment.txt"));
        appointmentList.setText("Appointments for " + doctorUsername + ":\n\n");

        while ((line = reader.readLine()) != null) {
            String[] appointment = line.split(",");
            if (appointment[0].equals(doctorUsername)) {
                appointmentList.append("- Patient: " + appointment[1] + ", Date: " + appointment[2] + ", Time: " + appointment[3] + "\n");
            }
        }

        reader.close();
    }

    public static void main(String[] args) {
        new SeeAppointment("doctorUsername");
    }
}
