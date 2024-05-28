package com.lostfound.controller;

import javax.validation.Valid;
import java.util.List;

import com.lostfound.dao.ItemDao;
import com.lostfound.exception.DaoException;
import com.lostfound.model.Item;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin
@RequestMapping("/items")
public class ItemsController {

    private final ItemDao itemDao;

    public ItemsController(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @GetMapping
    public List<Item> getItems() {
        try {
            return itemDao.getItems();
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrieve items.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable int id) {
        try {
            Item item = itemDao.getItemById(id);
            if (item == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found.");
            }
            return new ResponseEntity<>(item, HttpStatus.OK);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrieve item.");
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Item createItem(@Valid @RequestBody Item item) {
        try {
            return itemDao.createItem(item);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to create item.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable int id, @Valid @RequestBody Item item) {
        try {
            Item existingItem = itemDao.getItemById(id);
            if (existingItem == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found.");
            }
            item.setItemId(id);
            itemDao.updateItem(item);
            return new ResponseEntity<>(item, HttpStatus.OK);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to update item.");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable int id) {
        try {
            Item existingItem = itemDao.getItemById(id);
            if (existingItem == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found.");
            }
            itemDao.deleteItem(id);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to delete item.");
        }
    }
}
