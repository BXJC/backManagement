package org.csu.mypetstore.controller;


import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.domain.Order;
import org.csu.mypetstore.service.CartService;
import org.csu.mypetstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/order")
@SessionAttributes({"order","cardList","orderList"})
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    CartService cartService;

    private static final List<String> cardList;

    static {
        List<String> cList = new ArrayList<String>();
        cList.add("Visa");
        cList.add("MasterCard");
        cList.add("American Express");
        cardList = Collections.unmodifiableList(cList);
    }

    @GetMapping("/newOrder")
    public String newOrder(@SessionAttribute("account") Account account, @SessionAttribute("cart") Cart cart, Model model){
        Order order = new Order();
        order.initOrder(account,cart);
        model.addAttribute("order",order);
        model.addAttribute("cardList",cardList);
        return "order/newOrderForm";
    }

    @PostMapping("/confirmOrder")
    public String ConfirmOrder(){
        return "order/confirmOrder";
    }

    @GetMapping("/viewNewOrder")
    public String ViewOrder(@SessionAttribute("order") Order order,@SessionAttribute("account") Account account){
            cartService.removeCart(account);
            orderService.insertOrder(order);
        return "order/viewOrder";
    }

    @GetMapping("/viewOldOrder")
    public String viewOrder(int orderId, Model model){
        Order order = orderService.getOrder(orderId);
        model.addAttribute("order",order);
        return "order/viewOrder";
    }


    @GetMapping("/listOrders")
    public String ListOrders(@SessionAttribute("account")Account account,Model model){
        List<Order> orderList = orderService.getOrdersByUsername(account.getUsername());
        model.addAttribute("orderList",orderList);
        return "order/listOrder";
    }

}
