package com.example.have_you_eaten.bean;

/**
 * 商品实体类
 */
public class Commodity {
    //商品价格
    private double price;
    //商品名称
    private String name;
    //商品数量
    private int number;



    /**
     * setter&getter
     * @return
     */

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
