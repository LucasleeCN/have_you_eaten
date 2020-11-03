package com.fanfou.food.controller;

import com.fanfou.food.annotation.UserLoginToken;
import com.fanfou.food.controller.form.OrderItem;
import com.fanfou.food.domain.ResultData;
import com.fanfou.food.domain.RiderOrder;
import com.fanfou.food.domain.User;
import com.fanfou.food.exceptions.OrderException;
import com.fanfou.food.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jzw
 * @date 2020/10/29 12:03
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    final IOrderService orderService;
    final HttpServletRequest request;

    @Autowired
    public OrderController(IOrderService orderService, HttpServletRequest request) {
        this.orderService = orderService;
        this.request = request;
    }

    /**
     * 获取所有骑手的订单
     *
     * @return
     */
    @GetMapping("/rider/all")
    @UserLoginToken
    public ResultData getAllRiderOrder() {
        User user = (User) request.getAttribute("user");
        Map<String, Object> res = new HashMap<>();
        try {
            List<RiderOrder> allRiderOrder = orderService.getAllRiderFinishOrder(user.getId());
            res.put("orderList", allRiderOrder);
            log.info(res.toString());
        } catch (OrderException e) {
            return ResultData.fail(e.getMessage());
        }
        return ResultData.success(res);
    }

    @GetMapping("/custom/all")
    @UserLoginToken
    public ResultData getAllCustomOrder() {
        User user = (User) request.getAttribute("user");
        Map<String, Object> res = new HashMap<>();
        try {
            List<RiderOrder> allRiderOrder = orderService.getAllRiderFinishOrder(user.getId());
            res.put("orderList", allRiderOrder);
            log.info(res.toString());
        } catch (OrderException e) {
            return ResultData.fail(e.getMessage());
        }
        return ResultData.success(res);
    }

    @PostMapping("/submit")
    public ResultData submitOrder(@RequestBody List<OrderItem> orderItemList) {
        log.info(orderItemList.toString());
        return ResultData.success();
    }
}
