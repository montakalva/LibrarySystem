package user;

public class User {

    private int userId;
    private String userName;
    private String password;
    private int age;
    private String email;
    private String phoneNumber;
    private String specialMarks;

    public User() {}

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSpecialMarks() {
        return specialMarks;
    }

    public void setSpecialMarks(String specialMarks) {
        this.specialMarks = specialMarks;
    }

    @Override
    public String toString() {
        return "UserId | " + userId +
                "\t userName | " + userName +
                "\t password | " + password +
                "\t age | " + age +
                "\t email | " + email +
                "\t phoneNumber | " + phoneNumber +
                "\t specialMarks | " + specialMarks;
    }
}
