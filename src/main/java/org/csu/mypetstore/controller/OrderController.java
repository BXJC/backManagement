package org.csu.mypetstore.controller;


import com.sun.org.apache.xpath.internal.operations.Or;
import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.AppResult;
import org.csu.mypetstore.domain.CartItem;
import org.csu.mypetstore.domain.Order;
import org.csu.mypetstore.other.ResultBuilder;
import org.csu.mypetstore.other.ResultCode;
import org.csu.mypetstore.service.CatalogService;
import org.csu.mypetstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    CatalogService catalogService;

    @GetMapping(value = "/view", produces = "application/Json;charset=UTF-8")
    public AppResult<List<Order>> ViewOrders(){
        AppResult<List<Order>> appResult = new AppResult<>();
        List<Order> orders = orderService.getOrders();
        appResult = ResultBuilder.successData(ResultCode.OK,orders);
        return appResult;
    }

    @GetMapping("/view/{id}")
    public AppResult<Order> viewOrder(@PathVariable("id") int orderId){
        AppResult<Order> appResult = new AppResult<>();
        Order order = orderService.getOrder(orderId);
        appResult = ResultBuilder.successData(ResultCode.OK,order);
        return appResult;
    }
}

