package util;

public class UserTM {
    private String name;
    private String userContactNo;
    private String userEmail;
    private String username;
    private String password;
    private String userRole;

    public UserTM() {
    }

    public UserTM(String name, String userContactNo, String userEmail, String username, String password, String userRole) {
        this.name = name;
        this.userContactNo = userContactNo;
        this.userEmail = userEmail;
        this.username = username;
        this.password = password;
        this.userRole = userRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserContactNo() {
        return userContactNo;
    }

    public void setUserContactNo(String userContactNo) {
        this.userContactNo = userContactNo;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
