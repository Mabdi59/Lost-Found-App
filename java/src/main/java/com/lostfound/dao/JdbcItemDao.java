package com.lostfound.dao;

import com.lostfound.exception.DaoException;
import com.lostfound.model.Item;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcItemDao implements ItemDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcItemDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Item> getItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT item_id, name, description, date_lost, is_claimed, reported_by, category_id FROM items";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Item item = mapRowToItem(results);
                items.add(item);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return items;
    }

    @Override
    public Item getItemById(int itemId) {
        Item item = null;
        String sql = "SELECT item_id, name, description, date_lost, is_claimed, reported_by, category_id FROM items WHERE item_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, itemId);
            if (results.next()) {
                item = mapRowToItem(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return item;
    }

    @Override
    public Item createItem(Item item) {
        Item newItem = null;
        String sql = "INSERT INTO items (name, description, date_lost, is_claimed, reported_by, category_id) VALUES (?, ?, ?, ?, ?, ?) RETURNING item_id";
        try {
            int newItemId = jdbcTemplate.queryForObject(sql, int.class, item.getName(), item.getDescription(), item.getDateLost(), item.isClaimed(), item.getReportedBy(), item.getCategoryId());
            newItem = getItemById(newItemId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newItem;
    }

    @Override
    public void updateItem(Item item) {
        String sql = "UPDATE items SET name = ?, description = ?, date_lost = ?, is_claimed = ?, reported_by = ?, category_id = ? WHERE item_id = ?";
        try {
            jdbcTemplate.update(sql, item.getName(), item.getDescription(), item.getDateLost(), item.isClaimed(), item.getReportedBy(), item.getCategoryId(), item.getItemId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public void deleteItem(int itemId) {
        String sql = "DELETE FROM items WHERE item_id = ?";
        try {
            jdbcTemplate.update(sql, itemId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    private Item mapRowToItem(SqlRowSet rs) {
        Item item = new Item();
        item.setItemId(rs.getInt("item_id"));
        item.setName(rs.getString("name"));
        item.setDescription(rs.getString("description"));
        item.setDateLost(rs.getDate("date_lost"));
        item.setClaimed(rs.getBoolean("is_claimed"));
        item.setReportedBy(rs.getInt("reported_by"));
        item.setCategoryId(rs.getInt("category_id"));
        return item;
    }
}
