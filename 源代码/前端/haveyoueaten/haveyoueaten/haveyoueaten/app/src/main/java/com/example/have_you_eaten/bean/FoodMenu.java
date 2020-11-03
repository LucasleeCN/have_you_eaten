package com.example.have_you_eaten.bean;

/**
 * 封装商品菜单类
 */
public class FoodMenu {

    private String name;
    private double price;

    public String getName(){
        return name;
    }

    public double getPrice(){
        return price;
    }

    public int getImageId(){
        return imageId;
    }
    public FoodMenu(String name, int imageId){
        this.name=name;
        this.imageId=imageId;
    }
    private int imageId;

}
