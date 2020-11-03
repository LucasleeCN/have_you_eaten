package com.example.have_you_eaten.lsy_modle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order {
    public Map<Good, Integer> buyList;
    double total;

    public double getTotal() {
        countTotal();
        return total;
    }

    public void countTotal() {
        total = 0;
        for (Good good : buyList.keySet()) {
            total += good.price * buyList.get(good);
        }
    }

    public String printBuyList() {
        String str = "";
        for (Good good : buyList.keySet()) {
            str += good.name + "-----" + buyList.get(good) + "份\n";
        }
        return str;
    }

    public List<Map<String, Object>> getMsg() {
        List<Map<String, Object>> returnList = new ArrayList<>();
        for (Good good : buyList.keySet()) {
            Map<String, Object> map = new HashMap<>();
            map.put("menuId", good.getId());
            map.put("count", buyList.get(good));
            returnList.add(map);
        }
        return returnList;
    }
}
