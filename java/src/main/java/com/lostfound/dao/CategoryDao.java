package com.lostfound.dao;

import com.lostfound.model.Category;

import java.util.List;

public interface CategoryDao {

    List<Category> getCategories();

    Category getCategoryById(int id);

    Category createCategory(Category category);

    void updateCategory(Category category);

    void deleteCategory(int id);
}
