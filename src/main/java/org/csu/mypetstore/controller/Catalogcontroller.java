package org.csu.mypetstore.controller;

import org.apache.http.HttpResponse;
import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.service.CatalogService;
import org.csu.mypetstore.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.List;

@Controller
@RequestMapping("/catalog")
@SessionAttributes({"product","item"})
public class Catalogcontroller {

    @Autowired
    CatalogService catalogService;

    @Autowired
    LogService logService;

    @GetMapping("/view")
    public String view(Model model, HttpSession session, HttpServletResponse response){
        model.addAttribute("account",null);
        return "catalog/main";
    }
    @GetMapping(value = "/Categorys/{id}", produces="application/Json;charset=UTF-8" )
    @ResponseBody
    public List<Product> viewCategory(@PathVariable("id") String id){
        Category category=catalogService.getCategory (id);
        List<Product> products=catalogService.getProductListByCategory (id);
        return products;
    }
    @GetMapping("/viewProduct")
    public String viewProduct(String productId,Model model){
        Product product=catalogService.getProduct (productId);
        List<Item> items=catalogService.getItemListByProduct (productId);
        model.addAttribute ("product",product);
        model.addAttribute ("itemList",items);

        return "catalog/product";
    }

    @GetMapping(value = "/autoComplete", produces="application/Json;charset=UTF-8" )
    @ResponseBody
    public List<Product> autoComplete(String keyword){
        List<Product> productList=catalogService.searchProductList (keyword);
        System.out.println ("发送请求成功");
        return productList;
    }
    @PostMapping("/searchProducts")
    public String searchProduct(String keyword,Model model){
        List<Product> products=catalogService.searchProductList (keyword);
        processProductDescription(products);
        model.addAttribute ("productList",products);

        return "catalog/searchProducts";
    }

    @GetMapping("viewItem")
    public String viewItem( Account account, String itemId, Model model){
        Item item = catalogService.getItem(itemId);
        Product product = item.getProduct();
        processProductDescription(product);
        System.out.println ("quantity"+item.getQuantity ());


        model.addAttribute("item",item);
        model.addAttribute("product",product);
        return "catalog/item";
    }

    @GetMapping("viewItemWithAccount")
    public String viewItemWithAccount(@SessionAttribute("account") Account account, String itemId, Model model){
        Item item = catalogService.getItem(itemId);

        Product product = item.getProduct();
        processProductDescription(product);
        if(account != null)
            logService.insertBrowseLog (account,itemId);

        model.addAttribute("item",item);
        model.addAttribute("product",product);
        return "catalog/item";
    }


    private void processProductDescription(Product product){
        String [] temp = product.getDescription().split("\"");
        product.setDescriptionImage(temp[1]);
        product.setDescriptionText(temp[2].substring(1));
    }
    private void processProductDescription(List<Product> productList){
        for(Product product : productList) {
            processProductDescription(product);
        }
    }

}

