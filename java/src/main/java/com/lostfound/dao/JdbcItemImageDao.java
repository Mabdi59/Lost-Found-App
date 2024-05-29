package com.lostfound.dao;

import com.lostfound.exception.DaoException;
import com.lostfound.model.ItemImage;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcItemImageDao implements ItemImageDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcItemImageDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ItemImage> getItemImages() {
        List<ItemImage> itemImages = new ArrayList<>();
        String sql = "SELECT image_id, item_id, image_url FROM item_images";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                ItemImage itemImage = mapRowToItemImage(results);
                itemImages.add(itemImage);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return itemImages;
    }

    @Override
    public ItemImage getItemImageById(int imageId) {
        ItemImage itemImage = null;
        String sql = "SELECT image_id, item_id, image_url FROM item_images WHERE image_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, imageId);
            if (results.next()) {
                itemImage = mapRowToItemImage(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return itemImage;
    }

    @Override
    public ItemImage createItemImage(ItemImage itemImage) {
        ItemImage newItemImage = null;
        String sql = "INSERT INTO item_images (item_id, image_url) VALUES (?, ?) RETURNING image_id";
        try {
            int newImageId = jdbcTemplate.queryForObject(sql, int.class, itemImage.getItemId(), itemImage.getImageUrl());
            newItemImage = getItemImageById(newImageId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newItemImage;
    }

    @Override
    public void updateItemImage(ItemImage itemImage) {
        String sql = "UPDATE item_images SET item_id = ?, image_url = ? WHERE image_id = ?";
        try {
            jdbcTemplate.update(sql, itemImage.getItemId(), itemImage.getImageUrl(), itemImage.getImageId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public void deleteItemImage(int imageId) {
        String sql = "DELETE FROM item_images WHERE image_id = ?";
        try {
            jdbcTemplate.update(sql, imageId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    private ItemImage mapRowToItemImage(SqlRowSet rs) {
        ItemImage itemImage = new ItemImage();
        itemImage.setImageId(rs.getInt("image_id"));
        itemImage.setItemId(rs.getInt("item_id"));
        itemImage.setImageUrl(rs.getString("image_url"));
        return itemImage;
    }
}
