package org.csu.mypetstore;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.service.CartService;
import org.csu.mypetstore.service.CatalogService;
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



}
