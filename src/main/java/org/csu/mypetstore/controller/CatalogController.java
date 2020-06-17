package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalogs")
@CrossOrigin


public class CatalogController {

    @Autowired
    CatalogService catalogService;


    @GetMapping(value = "/categories/{id}", produces="application/Json;charset=UTF-8" )
    public List<Product> viewCategory(@PathVariable("id") String id){
        Category category=catalogService.getCategory (id);
        List<Product> products = catalogService.getProductListByCategory (id);
        return products;
    }
    @GetMapping(value = "/categories/products/{id}", produces = "application/Json;charset=UTF-8")
    public List<Item> viewProduct(@PathVariable("id") String id){
        Product product=catalogService.getProduct (id);
        List<Item> items = catalogService.getItemListByProduct (id);
        return items;
    }

    @GetMapping(value = "/categories/products/items/{id}", produces = "application/Json;charset=UTF-8")
    public Item viewItem(@PathVariable("id") String id){
        Item item = catalogService.getItem(id);
        Product product = item.getProduct();
        processProductDescription(product);
        System.out.println ("quantity"+item.getQuantity ());
        return item;
    }

    @PostMapping(value = "/insert",produces = "application/Json;charset=UTF-8")
    public void addItem(@RequestBody Item item){
        catalogService.insertItem(item);
    }

    @PutMapping(value = "/update", produces = "application/Json;charset=UTF-8")
    public void updateItem(@RequestBody Item item){
        catalogService.updateItem(item);
    }

    @DeleteMapping(value = "/delete", produces = "application/Json;charset=UTF-8")
    public void deleteItem(@RequestBody Item item){
        catalogService.deleteItem(item);
    }


    private void processProductDescription(Product product){
        String [] temp = product.getDescription().split("\\|");
        product.setDescriptionImage(temp[1]);
        product.setDescriptionText(temp[2].substring(1));
    }
    private void processProductDescription(List<Product> productList){
        for(Product product : productList) {
            processProductDescription(product);
        }
    }

}

