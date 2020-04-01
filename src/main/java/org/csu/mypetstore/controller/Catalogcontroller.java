package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;
import java.util.List;

@Controller
@RequestMapping("/catalog")
public class Catalogcontroller {

    @Autowired
    CatalogService catalogService;

    @GetMapping("/view")
    public String view(){
        return "catalog/main";
    }
    @GetMapping("/viewCategory")
    public String viewCategory(String categoryId, Model model){
        Category category=catalogService.getCategory (categoryId);
        List<Product> products=catalogService.getProductListByCategory (categoryId);
        model.addAttribute ("category",category);
        model.addAttribute ("productList",products);

        return "catalog/category";
    }
    @GetMapping("/viewProduct")
    public String viewProduct(String productId,Model model){
        Product product=catalogService.getProduct (productId);
        List<Item> items=catalogService.getItemListByProduct (productId);
        model.addAttribute ("product",product);
        model.addAttribute ("itemList",items);

        return "catalog/product";
    }

    @GetMapping(value = "/autoComplete", produces="application/XML;charset=UTF-8" )
    @ResponseBody
    public List<Product> autoComplete(String keyword){
        List<Product> productList=catalogService.searchProductList (keyword);
        System.out.println ("发送请求成功");
        return productList;
    }

    @PostMapping("/searchProducts")
    public String searchProduct(String keyword,Model model){
        List<Product> products=catalogService.searchProductList (keyword);
        model.addAttribute ("productList",products);

        return "catalog/searchProducts";
    }

}

