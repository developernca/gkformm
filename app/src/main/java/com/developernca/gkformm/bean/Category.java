package com.developernca.gkformm.bean;

/**
 * Created on 8/15/2018.
 *
 * @author Nyein Chan Aung
 */

public class Category {

    /**
     * Category name to be displayed in recycler view list.
     */
    private String categoryName;
    /**
     * Detail file name to be opened for this category.
     */
    private String detailName;

    public Category(String categoryName, String detailName) {
        this.categoryName = categoryName;
        this.detailName = detailName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getDetailName() {
        return detailName;
    }
}
