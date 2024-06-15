public class Patient extends Person {
    private String bloodGroup;

    public Patient(String name, int age, String bloodGroup, String gender, String username, String password) {
        super(name, age, gender, username, password);
        this.bloodGroup = bloodGroup;
    }

    // Getters and setters
    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
}
