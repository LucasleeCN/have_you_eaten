package com.example.have_you_eaten.bean;

/**
 * 实现订单实体类
 */
public class Order {
    //姓名
    private String userName ;
    //手机号
    private String  phone;
    //账单号
    private String account;
    //身份证号
    private String iden_number;
    //商家名字
    private String producer;
    //商品名字
    private String commodity;
    //时间
    private String time;
    //地址
    private String address;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    /**
     * setter&getter
     * @return
     */


    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getAccount(){
        return account;
    }

    public void setAccount(String account){
        this.account = account;
    }

    public String getIden_number(){
        return iden_number;
    }

    public void setIden_number(String iden_number){
        this.iden_number = iden_number;
    }
}
