package com.example.have_you_eaten.bean;

/**
 * 封装商品菜单类
 */
public class FoodMenu {

    private String name;
    private double price;
    private int imageId;
    public FoodMenu(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public FoodMenu() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getImageId() {
        return imageId;
    }


}
