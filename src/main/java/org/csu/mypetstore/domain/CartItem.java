package org.csu.mypetstore.domain;

import java.math.BigDecimal;

public class CartItem {

    private String itemId;
    private Item item;
    private int quantity;
    private boolean inStock;
    private BigDecimal total;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void incrementQuantity(){
        quantity++;
        calculateTotal ();

    }
    private void calculateTotal(){
        if(item != null && item.getListPrice () != null){
            total = item.getListPrice ().multiply (new BigDecimal (quantity));
        }
        else {
            total = null;
        }
    }
}
