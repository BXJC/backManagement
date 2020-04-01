package org.csu.mypetstore;

import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.service.CatalogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MypetstoreApplicationTests {

    @Autowired
    CatalogService service;
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


}
