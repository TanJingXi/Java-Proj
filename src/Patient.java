public class Patient {
    private String name;
    private int age;
    private String bloodGroup;
    private String gender;
    private String username;
    private String password;

    public Patient(String name, int age, String bloodGroup, String gender, String username, String password) {
        this.name = name;
        this.age = age;
        this.bloodGroup = bloodGroup;
        this.gender = gender;
        this.username = username;
        this.password = password;
    }

    // Getters and setters
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getBloodGroup() { return bloodGroup; }
    public String getGender() { return gender; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }

    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
    public void setGender(String gender) { this.gender = gender; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
}
