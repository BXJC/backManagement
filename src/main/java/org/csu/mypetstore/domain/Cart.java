package org.csu.mypetstore.domain;

import java.math.BigDecimal;
import java.util.*;

public class Cart {

    private final Map<String, CartItem> itemMap ;
    private final List<CartItem> itemList ;


    public Cart(){
        itemList=new ArrayList<CartItem> ();
        itemMap = Collections.synchronizedMap(new HashMap<String, CartItem>());
    }

    public Iterator<CartItem> getCartItems() {
        return itemList.iterator();
    }

    public List<CartItem> getCartItemList() {
        return itemList;
    }



    public Map<String, CartItem> getItemMap() {
        return itemMap;
    }

    public List<CartItem> getItemList() {
        return itemList;
    }

    public int getNumberOfItems(){
        return itemList.size ();
    }

    public boolean containsItemId(String itemId){
        return itemMap.containsKey (itemId);
    }

    public void addItem(Item item, boolean isInStock) {
        CartItem cartItem = itemMap.get(item.getItemId());
        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setItem(item);
            cartItem.setQuantity(0);
            cartItem.setInStock(isInStock);
            itemMap.put(item.getItemId(), cartItem);
            itemList.add(cartItem);
        }
        cartItem.incrementQuantity();
    }


    public Iterator<CartItem> getAllCartItems() {
        return itemList.iterator();
    }


    public Item removeItemById(String itemId) {
        CartItem cartItem = (CartItem) itemMap.remove(itemId);
        if (cartItem == null) {
            return null;
        } else {
            itemList.remove(cartItem);
            return cartItem.getItem();
        }
    }

    public void incrementQuantityByItemId(String itemId) {
        CartItem cartItem = (CartItem) itemMap.get(itemId);
        cartItem.incrementQuantity();
    }

    public void setQuantityByItemId(String itemId, int quantity) {
        CartItem cartItem = (CartItem) itemMap.get(itemId);
        cartItem.setQuantity(quantity);
    }

    public BigDecimal getSubTotal() {
        BigDecimal subTotal = new BigDecimal("0");
        Iterator<CartItem> items = getAllCartItems();
        while (items.hasNext()) {
            CartItem cartItem = (CartItem) items.next();
            Item item = cartItem.getItem();
            BigDecimal listPrice = item.getListPrice();
            BigDecimal quantity = new BigDecimal(String.valueOf(cartItem.getQuantity()));
            subTotal = subTotal.add(listPrice.multiply(quantity));
        }
        return subTotal;
    }

    public void setCartItemList(List<CartItem> cartItemList)
    {
        System.out.println ("list"+cartItemList);

        for(int i=0;i<cartItemList.size ();i++){

            addItem (cartItemList.get(i).getItem (),cartItemList.get(i).isInStock ());
            setQuantityByItemId (cartItemList.get(i).getItem ().getItemId (),cartItemList.get(i).getQuantity ());
        }
    }

}
