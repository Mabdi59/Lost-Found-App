package com.lostfound.controller;

import javax.validation.Valid;
import java.util.List;

import com.lostfound.dao.ItemImageDao;
import com.lostfound.exception.DaoException;
import com.lostfound.model.ItemImage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin
@RequestMapping("/item-images")
public class ItemImagesController {

    private final ItemImageDao itemImageDao;

    public ItemImagesController(ItemImageDao itemImageDao) {
        this.itemImageDao = itemImageDao;
    }

    @GetMapping
    public List<ItemImage> getItemImages() {
        try {
            return itemImageDao.getItemImages();
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrieve item images.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemImage> getItemImageById(@PathVariable int id) {
        try {
            ItemImage itemImage = itemImageDao.getItemImageById(id);
            if (itemImage == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item image not found.");
            }
            return new ResponseEntity<>(itemImage, HttpStatus.OK);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrieve item image.");
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemImage createItemImage(@Valid @RequestBody ItemImage itemImage) {
        try {
            return itemImageDao.createItemImage(itemImage);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to create item image.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemImage> updateItemImage(@PathVariable int id, @Valid @RequestBody ItemImage itemImage) {
        try {
            ItemImage existingItemImage = itemImageDao.getItemImageById(id);
            if (existingItemImage == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item image not found.");
            }
            itemImage.setImageId(id);
            itemImageDao.updateItemImage(itemImage);
            return new ResponseEntity<>(itemImage, HttpStatus.OK);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to update item image.");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItemImage(@PathVariable int id) {
        try {
            ItemImage existingItemImage = itemImageDao.getItemImageById(id);
            if (existingItemImage == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item image not found.");
            }
            itemImageDao.deleteItemImage(id);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to delete item image.");
        }
    }
}
