package ru.job4j.model;

import java.util.Objects;

public class User {
    private int id;
    private String userName;
    private String email;
    private String phone;
    private String password;

    public User() {
    }

    public User(int id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public User(int id, String userName, String email, String phone, String password) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id && Objects.equals(userName, user.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName);
    }
}