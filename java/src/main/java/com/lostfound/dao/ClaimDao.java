package com.lostfound.dao;

import com.lostfound.model.Claim;

import java.util.List;

public interface ClaimDao {

    List<Claim> getClaims();

    Claim getClaimById(int id);

    Claim createClaim(Claim claim);

    void updateClaim(Claim claim);

    void deleteClaim(int id);
}
