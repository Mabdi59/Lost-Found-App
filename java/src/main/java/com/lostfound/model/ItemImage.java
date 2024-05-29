package com.lostfound.model;

import java.util.Objects;

public class ItemImage {

    private int imageId;
    private int itemId;
    private String imageUrl;

    public ItemImage() { }

    public ItemImage(int imageId, int itemId, String imageUrl) {
        this.imageId = imageId;
        this.itemId = itemId;
        this.imageUrl = imageUrl;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemImage itemImage = (ItemImage) o;
        return imageId == itemImage.imageId &&
                itemId == itemImage.itemId &&
                Objects.equals(imageUrl, itemImage.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageId, itemId, imageUrl);
    }

    @Override
    public String toString() {
        return "ItemImage{" +
                "imageId=" + imageId +
                ", itemId=" + itemId +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
