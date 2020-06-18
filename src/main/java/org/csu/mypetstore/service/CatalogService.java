package org.csu.mypetstore.service;

import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.persistence.CategoryMapper;
import org.csu.mypetstore.persistence.ItemMapper;
import org.csu.mypetstore.persistence.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CatalogService {

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    ItemMapper itemMapper;

    public List<Category> getCategories(){
        return categoryMapper.getCategoryList();
    };

    public Category getCategory(String categoryId) {
        return categoryMapper.getCategory(categoryId);
    }
    public Product getProduct(String productId) {
        return productMapper.getProduct(productId);
    }

    public List<Product> getProductListByCategory(String categoryId) {
        return productMapper.getProductListByCategory(categoryId);
    }


    public List<Item> getItemListByProduct(String productId) {
        return itemMapper.getItemListByProduct(productId);
    }

    public Item getItem(String itemId) {
        return itemMapper.getItem(itemId);
    }

    public void insertItem(Item item){
        itemMapper.insertItem(item);
    }

    public void updateItem(Item item){
        itemMapper.updateItem(item);
    }

    public void deleteItem(Item item){
        itemMapper.deleteItem(item);
    }

    public void insertProduct(Product product){
        productMapper.insertProduct(product);
    }

    public void updateProduct(Product product){
        productMapper.updateProduct(product);
    }

    public void deleteProduct(Product product){
        productMapper.deleteProduct(product);
    }

    public boolean isItemInStock(String itemId) {
        return itemMapper.getInventoryQuantity(itemId) > 0;
    }

    public void updateInventoryQuantity(Map<String, Object> param){


    };

}
