package com.lostfound.dao;

import com.lostfound.exception.DaoException;
import com.lostfound.model.Category;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCategoryDao implements CategoryDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCategoryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT category_id, category_name FROM categories";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Category category = mapRowToCategory(results);
                categories.add(category);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return categories;
    }

    @Override
    public Category getCategoryById(int categoryId) {
        Category category = null;
        String sql = "SELECT category_id, category_name FROM categories WHERE category_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, categoryId);
            if (results.next()) {
                category = mapRowToCategory(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return category;
    }

    @Override
    public Category createCategory(Category category) {
        Category newCategory = null;
        String sql = "INSERT INTO categories (category_name) VALUES (?) RETURNING category_id";
        try {
            int newCategoryId = jdbcTemplate.queryForObject(sql, int.class, category.getCategoryName());
            newCategory = getCategoryById(newCategoryId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newCategory;
    }

    @Override
    public void updateCategory(Category category) {
        String sql = "UPDATE categories SET category_name = ? WHERE category_id = ?";
        try {
            jdbcTemplate.update(sql, category.getCategoryName(), category.getCategoryId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public void deleteCategory(int categoryId) {
        String sql = "DELETE FROM categories WHERE category_id = ?";
        try {
            jdbcTemplate.update(sql, categoryId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    private Category mapRowToCategory(SqlRowSet rs) {
        Category category = new Category();
        category.setCategoryId(rs.getInt("category_id"));
        category.setCategoryName(rs.getString("category_name"));
        return category;
    }
}
