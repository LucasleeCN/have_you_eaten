package com.example.have_you_eaten.api;

public class ApiUrl {
        private static final String BASE_URL = "http://fanfou.finlu.com.cn:8080";
//    private static final String BASE_URL = "http://192.168.43.118:8080";
    public static final String REGISTER_URL = BASE_URL + "/auth/register";
    public static final String LOGIN_URL = BASE_URL + "/auth/login";
    public static final String AUTHER_USER_INFO= BASE_URL + "/auth/userinfo";
    public static final String AUTHER_USER_PASSWORD=BASE_URL+"/auth/user/pwd";
    public static final String AUTHER_USER_ORDER=BASE_URL+"/order/rider/all";
    public static final String AUTHER_USER_MENU=BASE_URL+"/menu/add";
    public static final String AUTHER_GET_SHOPINFO=BASE_URL+"/shop/all";
    public static final String AUTHER_GET_MENU=BASE_URL+"/menu/all";
    public static final String AUTHER_GET_CUMSTOM_ORDER=BASE_URL+"/order/custom/all";
    public static final String AUTHER_GET_FOODMENU_ORDER=BASE_URL+"/menu/shop/get";
    public static final String AUTHER_SEND_FOODMENU_ORDER=BASE_URL+"/order/submit";

}