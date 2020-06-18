package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.AppResult;
import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.other.ResultBuilder;
import org.csu.mypetstore.other.ResultCode;
import org.csu.mypetstore.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;
import java.util.List;

@RestController
@RequestMapping("/catalogs")
@CrossOrigin


public class CatalogController {

    @Autowired
    CatalogService catalogService;

    @GetMapping(value = "/categories/{catId}/products", produces="application/Json;charset=UTF-8" )
    public AppResult<List<Product>> viewCategory(@PathVariable("catId") String id){
        AppResult<List<Product>> appResult = new AppResult<>();
        Category category=catalogService.getCategory (id);
        List<Product> products = catalogService.getProductListByCategory (id);
        if (products.size() == 0)
        {
            appResult = ResultBuilder.fail(ResultCode.NoInfoFind);
        }
        else
        {
            appResult = ResultBuilder.successData(ResultCode.OK,products);
        }
        return appResult;
    }
    @GetMapping(value = "/products/{proId}/items", produces = "application/Json;charset=UTF-8")
    public AppResult<List<Item>> viewProduct(@PathVariable("proId") String id){
        AppResult<List<Item>> appResult = new AppResult<>();
        Product product=catalogService.getProduct (id);
        List<Item> items = catalogService.getItemListByProduct (id);
        if (items.size() == 0)
        {
            appResult = ResultBuilder.fail(ResultCode.NoInfoFind);
        }
        else
        {
            appResult = ResultBuilder.successData(ResultCode.OK,items);
        }
        return appResult;
    }

    @GetMapping(value = "/items/{id}", produces = "application/Json;charset=UTF-8")
    public AppResult<Item> viewItem(@PathVariable("id") String id){
        AppResult<Item> appResult = new AppResult<>();
        Item item = catalogService.getItem(id);
        Product product = item.getProduct();
        processProductDescription(product);
        if (item.getItemId().isEmpty())
        {
            appResult = ResultBuilder.fail(ResultCode.NoInfoFind);
        }
        else
        {
            appResult = ResultBuilder.successData(ResultCode.OK,item);
        }
        System.out.println ("quantity"+item.getQuantity ());
        return appResult;
    }

    @PostMapping(value = "/items",produces = "application/Json;charset=UTF-8")
    public AppResult<Null> addItem(@RequestBody Item item){
        AppResult<Null> appResult = new AppResult<>();
        catalogService.insertItem(item);
        appResult = ResultBuilder.successNoData(ResultCode.Handled);
        return appResult;
    }

    @PutMapping(value = "/items", produces = "application/Json;charset=UTF-8")
    public AppResult<Null> updateItem(@RequestBody Item item){
        AppResult<Null> appResult = new AppResult<>();
        catalogService.updateItem(item);
        appResult = ResultBuilder.successNoData(ResultCode.Handled);
        return appResult;
    }

    @DeleteMapping(value = "/items", produces = "application/Json;charset=UTF-8")
    public AppResult<Null> deleteItem(@RequestBody Item item){
        AppResult<Null> appResult = new AppResult<>();
        catalogService.deleteItem(item);
        appResult = ResultBuilder.successNoData(ResultCode.Handled);
        return appResult;
    }


    private void processProductDescription(Product product){
        String [] temp = product.getDescription().split("\\|");
        product.setDescriptionImage(temp[0]);
        product.setDescriptionText(temp[1]);
    }
    private void processProductDescription(List<Product> productList){
        for(Product product : productList) {
            processProductDescription(product);
        }
    }

}

