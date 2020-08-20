package org.csu.mypetstore.domain;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Valid
public class Product {
    @NotNull(message = "productId不能为空")
    private String productId;
    @NotNull(message = "categoryId不能为空")
    private String categoryId;
    @NotNull(message = "商品名不能为空")
    private String name;
    @NotNull(message = "商品描述不能为空")
    private String description;
    private String descriptionImage;
    private String descriptionText;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionImage() {
        return descriptionImage;
    }

    public void setDescriptionImage(String descriptionImage) {
        this.descriptionImage = descriptionImage;
    }

    public String getDescriptionText() {
        return descriptionText;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }
}
