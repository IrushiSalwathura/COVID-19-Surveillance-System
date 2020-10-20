package util;

import javafx.scene.control.Button;

public class UsersTM {
    private String username;
    private String name;
    private String role;
    private Button remove;
    private String userContactNo;
    private String userEmail;
    private String password;

    public UsersTM() {
    }

    public UsersTM(String username, String name, String role, Button remove, String userContactNo, String userEmail, String password) {
        this.username = username;
        this.name = name;
        this.role = role;
        this.remove = remove;
        this.userContactNo = userContactNo;
        this.userEmail = userEmail;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Button getRemove() {
        return remove;
    }

    public void setRemove(Button remove) {
        this.remove = remove;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
