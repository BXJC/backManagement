package org.csu.mypetstore.controller;



import org.csu.mypetstore.domain.AppResult;
import org.csu.mypetstore.domain.Order;
import org.csu.mypetstore.other.ResultBuilder;
import org.csu.mypetstore.other.ResultCode;
import org.csu.mypetstore.other.UserLoginToken;
import org.csu.mypetstore.service.CatalogService;
import org.csu.mypetstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin
@ResponseBody
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    CatalogService catalogService;

    @UserLoginToken
    @GetMapping(value = "/view", produces = "application/Json;charset=UTF-8")
    public AppResult<List<Order>> ViewOrders(){
        AppResult<List<Order>> appResult = new AppResult<>();
        List<Order> orders = orderService.getOrders();
        appResult = ResultBuilder.successData(ResultCode.OK,orders);
        return appResult;
    }

    @UserLoginToken
    @GetMapping(value = "/view/{id}",produces = "application/Json;charset=UTF-8")
    public AppResult<Order> viewOrder(@PathVariable("id") int orderId){
        AppResult<Order> appResult = new AppResult<>();
        Order order = orderService.getOrder(orderId);
        appResult = ResultBuilder.successData(ResultCode.OK,order);
        return appResult;
    }

    @UserLoginToken
    @PatchMapping(value = "/view/{id}",produces = "application/Json;charset=UTF-8")
    public AppResult<String> updateOrderStatus(@PathVariable("id") int orderId,@RequestBody Order order){
        AppResult<String> appResult = new AppResult<>();
        orderService.updateOrderStatus(order);
        appResult = ResultBuilder.successNoData(ResultCode.Handled);
        return appResult;
    }
}

