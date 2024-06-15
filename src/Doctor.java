public class Doctor extends Person {
    private String qualification;

    public Doctor(String name, int age, String qualification, String gender, String username, String password) {
        super(name, age, gender, username, password);
        this.qualification = qualification;
    }

    // Getters and setters
    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }
}
