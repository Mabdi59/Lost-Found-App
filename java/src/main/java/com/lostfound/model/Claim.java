package com.lostfound.model;

import java.util.Date;
import java.util.Objects;

public class Claim {

    private int claimId;
    private int itemId;
    private int claimedBy;
    private Date dateClaimed;

    public Claim() { }

    public Claim(int claimId, int itemId, int claimedBy, Date dateClaimed) {
        this.claimId = claimId;
        this.itemId = itemId;
        this.claimedBy = claimedBy;
        this.dateClaimed = dateClaimed;
    }

    public int getClaimId() {
        return claimId;
    }

    public void setClaimId(int claimId) {
        this.claimId = claimId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getClaimedBy() {
        return claimedBy;
    }

    public void setClaimedBy(int claimedBy) {
        this.claimedBy = claimedBy;
    }

    public Date getDateClaimed() {
        return dateClaimed;
    }

    public void setDateClaimed(Date dateClaimed) {
        this.dateClaimed = dateClaimed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Claim claim = (Claim) o;
        return claimId == claim.claimId &&
                itemId == claim.itemId &&
                claimedBy == claim.claimedBy &&
                Objects.equals(dateClaimed, claim.dateClaimed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(claimId, itemId, claimedBy, dateClaimed);
    }

    @Override
    public String toString() {
        return "Claim{" +
                "claimId=" + claimId +
                ", itemId=" + itemId +
                ", claimedBy=" + claimedBy +
                ", dateClaimed=" + dateClaimed +
                '}';
    }
}
