package org.csu.mypetstore;

import org.csu.mypetstore.domain.*;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.service.CartService;
import org.csu.mypetstore.service.CatalogService;
import org.csu.mypetstore.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@MapperScan("org.csu.mypetstore.persistence")

class MypetstoreApplicationTests {

    @Autowired
    CatalogService service;

    @Autowired
    CartService cartService;

    @Autowired
    AccountService accountService;

    @Autowired
    OrderService orderService;

    @Test
    void contextLoads() {
    }

    @Test
    void testProduct(){
        List<Product> products=service.getProductListByCategory ("BIRDS");
        System.out.println (products.size ());
        Product product=service.getProduct ("AV-CB-01");
        System.out.println (product.getCategoryId ());

    }
    @Test
    void testCart(){
        Account account=accountService.getAccount ("a");
        Cart cart=cartService.getCart ("a");

      //  cartService.removeCart (account);

    }

    @Test
    void testOrder(){
        Order order = orderService.getOrder(1033);
        System.out.println(order.getUsername() + " " + order.getOrderDate() + " " + order.getBillCity());
    }

    @Test
    void testGetOrder(){
        List<Order> orderList = orderService.getOrdersByUsername("a");
        System.out.println(orderList);
    }



}
