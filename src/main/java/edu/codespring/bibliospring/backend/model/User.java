package edu.codespring.bibliospring.backend.model;

public class User extends BaseEntity {
    private String userName;
    private String password;

    public String toString() {
        return "User{userName='" + userName + "', password='" + this.password + "', UUID = " + this.getUuid() + "', ID = " + this.getId() + "'}";
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
