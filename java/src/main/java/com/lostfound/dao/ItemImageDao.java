package com.lostfound.dao;

import com.lostfound.model.ItemImage;

import java.util.List;

public interface ItemImageDao {

    List<ItemImage> getItemImages();

    ItemImage getItemImageById(int id);

    ItemImage createItemImage(ItemImage itemImage);

    void updateItemImage(ItemImage itemImage);

    void deleteItemImage(int id);
}
