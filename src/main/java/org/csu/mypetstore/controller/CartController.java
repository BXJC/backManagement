package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.service.CartService;
import org.csu.mypetstore.service.CatalogService;
import org.csu.mypetstore.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
@SessionAttributes({"cart","account"})
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    CatalogService catalogService;

    @Autowired
    LogService logService;

    @GetMapping("/viewCart")
    public String viewCart(@SessionAttribute("account") Account account, Model model){
        Cart cart=cartService.getCart (account.getUsername ());
        model.addAttribute ("cart",cart);
        return "cart/viewCart";
    }

    @GetMapping("/addToCart")
    public String addToCart(String itemId,@SessionAttribute("account") Account account,@SessionAttribute("cart")Cart cart,Model model){
        if(cart == null){
            cart = new Cart ();
        }

       logService.insertAddLog (account,itemId);
        if(cart.containsItemId (itemId)){
            cart.incrementQuantityByItemId (itemId);
            cartService.updateCart (cart,account);
        }
        else {
            boolean isInStock = catalogService.isItemInStock (itemId);
            Item item = catalogService.getItem (itemId);
            cart.addItem (item,isInStock);
            cartService.addItemToCart (account,cart.getCartItemList ().get (cart.getNumberOfItems ()-1));
        }
        model.addAttribute ("cart",cart);
        return "cart/viewCart";
    }

    @GetMapping("/removeItemFromCart")
    public String removeItemFromCart(String itemId,@SessionAttribute("cart") Cart cart,@SessionAttribute("account") Account account, Model model){
        Item item = cart.removeItemById (itemId);
        cartService.removeItemFromCart (account,itemId);

        if(item == null){
            model.addAttribute ("msg","Attempted to remove null CartItem from Cart");
        }

        model.addAttribute ("cart",cart);
        return "cart/viewCart";
    }

}
