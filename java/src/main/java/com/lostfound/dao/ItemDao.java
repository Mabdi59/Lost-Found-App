package com.lostfound.dao;

import com.lostfound.model.Item;

import java.util.List;

public interface ItemDao {

    List<Item> getItems();

    Item getItemById(int id);

    Item createItem(Item item);

    void updateItem(Item item);

    void deleteItem(int id);
}
