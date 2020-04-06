package org.csu.mypetstore;

import com.aliyuncs.exceptions.ClientException;
import org.csu.mypetstore.domain.*;
import org.csu.mypetstore.persistence.ItemMapper;
import org.csu.mypetstore.service.*;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    ItemMapper itemMapper;



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

    @Test
    void testInventoryQuantity(){
        Map<String, Object> param = new HashMap<String, Object> (2);
        String itemId = "EST-4";
        int increment = 2;
        param.put("itemId", itemId);
        param.put("increment", increment);
        System.out.println (itemId+"id+incre"+increment);
        System.out.println ("increment:"+increment);
        itemMapper.updateInventoryQuantity(param);
        System.out.println (itemMapper.getInventoryQuantity (itemId));
    }
    @Test
    void testVcode() throws ClientException {
        accountService.sendMsg("18956778818");
    }

}
