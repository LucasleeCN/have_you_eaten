package com.example.have_you_eaten.bean;

/**
 * 用户注册类，封装用户信息
 */
public class UserRegister {
    private String username;
    private String password;
    private String phone;
    private String name;
    private String idenNum;
    private String type;

    public UserRegister() {
    }

    public UserRegister(String username, String password, String phone, String name, String idenNum, String type) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.name = name;
        this.idenNum = idenNum;
        this.type = type;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdenNum() {
        return idenNum;
    }

    public void setIdenNum(String idenNum) {
        this.idenNum = idenNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String userType) {
        this.type = userType;
    }

}
