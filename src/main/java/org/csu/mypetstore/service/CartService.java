package org.csu.mypetstore.service;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.domain.CartItem;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.persistence.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    CartMapper cartMapper;

    @Autowired
    CatalogService catalogService;

    public Cart getCart(String username)
    {
        Cart cart=new Cart ();
        List<CartItem> cartItemList=cartMapper.getCartItemList (username);

        Item item=new Item ();

        for(int i=0;i<cartItemList.size ();i++){
            item=catalogService.getItem (cartItemList.get (i).getItemId ());
            cartItemList.get(i).setItem (item);
        }
        cart.setCartItemList (cartItemList);
        return cart;
    }

    public void updateCart(Cart cart, Account account){
        for(int i=0;i<cart.getCartItemList ().size ();i++) {
            cartMapper.updateCart (cart.getItemList ().get (i), account);
        }
    }

    public void addItemToCart(Account account,CartItem cartItem)
    {
        cartMapper.addItemToCart (account,cartItem);
    }

    public void removeCart(Account account){
        cartMapper.removeCart (account);
    }

    public void removeItemFromCart(Account account,String itemId){
        cartMapper.removeItemFromCart (account,itemId);
    }
}
