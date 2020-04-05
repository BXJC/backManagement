package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Log;
import org.csu.mypetstore.domain.Order;
import org.csu.mypetstore.service.LogService;
import org.csu.mypetstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Controller
@RequestMapping("/log")
@SessionAttributes({"account","browseLogList","addToItemToCartLogList","orderList"})
public class LogController {

    @Autowired
    LogService logService;

    @Autowired
    OrderService orderService;

    @GetMapping("/viewLog")
    public String viewLog(@SessionAttribute("account") Account account, Model model){
        List<Log> browseLogList=logService.getBrowseLogListByUsername (account.getUsername ());
        System.out.println (account.getUsername ());
        List<Log> addToItemToCartLogList=logService.getAddLogListByUsername (account.getUsername ());
        model.addAttribute ("browseLogList",browseLogList);
        model.addAttribute ("addToItemToCartLogList",addToItemToCartLogList);
        return "log/viewLog";
    }

    @GetMapping("viewOrderLog")
    public String viewOrderLog(@SessionAttribute("account") Account account, Model model){
        List<Order> orderList = orderService.getOrdersByUsername(account.getUsername());
        model.addAttribute("orderList",orderList);
        System.out.println(orderList);
        return "log/orderLog";
    }
}
