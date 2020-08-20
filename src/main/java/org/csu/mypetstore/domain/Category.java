package org.csu.mypetstore.domain;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Valid
public class Category {
    @NotNull(message = "categoryId不能为空")
    private String categoryId;
    private String name;
    private String description;

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
}
