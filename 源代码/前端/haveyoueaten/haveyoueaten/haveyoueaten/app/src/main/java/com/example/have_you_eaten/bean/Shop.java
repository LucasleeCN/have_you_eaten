package com.example.have_you_eaten.bean;

/**
 * 店铺类shop
 */
public class Shop {
   private String Name;
   private String phone;
   private String address;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
