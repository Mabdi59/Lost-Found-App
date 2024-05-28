package com.lostfound.model;

import java.util.Date;
import java.util.Objects;

public class Item {

    private int itemId;
    private String name;
    private String description;
    private Date dateLost;
    private boolean isClaimed;
    private int reportedBy;
    private int categoryId;

    public Item() { }

    public Item(int itemId, String name, String description, Date dateLost, boolean isClaimed, int reportedBy, int categoryId) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.dateLost = dateLost;
        this.isClaimed = isClaimed;
        this.reportedBy = reportedBy;
        this.categoryId = categoryId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateLost() {
        return dateLost;
    }

    public void setDateLost(Date dateLost) {
        this.dateLost = dateLost;
    }

    public boolean isClaimed() {
        return isClaimed;
    }

    public void setClaimed(boolean claimed) {
        isClaimed = claimed;
    }

    public int getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(int reportedBy) {
        this.reportedBy = reportedBy;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return itemId == item.itemId &&
                isClaimed == item.isClaimed &&
                reportedBy == item.reportedBy &&
                categoryId == item.categoryId &&
                Objects.equals(name, item.name) &&
                Objects.equals(description, item.description) &&
                Objects.equals(dateLost, item.dateLost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, name, description, dateLost, isClaimed, reportedBy, categoryId);
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dateLost=" + dateLost +
                ", isClaimed=" + isClaimed +
                ", reportedBy=" + reportedBy +
                ", categoryId=" + categoryId +
                '}';
    }
}
