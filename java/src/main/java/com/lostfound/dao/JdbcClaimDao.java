package com.lostfound.dao;

import com.lostfound.exception.DaoException;
import com.lostfound.model.Claim;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcClaimDao implements ClaimDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcClaimDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Claim> getClaims() {
        List<Claim> claims = new ArrayList<>();
        String sql = "SELECT claim_id, item_id, claimed_by, date_claimed FROM claims";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Claim claim = mapRowToClaim(results);
                claims.add(claim);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return claims;
    }

    @Override
    public Claim getClaimById(int claimId) {
        Claim claim = null;
        String sql = "SELECT claim_id, item_id, claimed_by, date_claimed FROM claims WHERE claim_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, claimId);
            if (results.next()) {
                claim = mapRowToClaim(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return claim;
    }

    @Override
    public Claim createClaim(Claim claim) {
        Claim newClaim = null;
        String sql = "INSERT INTO claims (item_id, claimed_by, date_claimed) VALUES (?, ?, ?) RETURNING claim_id";
        try {
            int newClaimId = jdbcTemplate.queryForObject(sql, int.class, claim.getItemId(), claim.getClaimedBy(), claim.getDateClaimed());
            newClaim = getClaimById(newClaimId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newClaim;
    }

    @Override
    public void updateClaim(Claim claim) {
        String sql = "UPDATE claims SET item_id = ?, claimed_by = ?, date_claimed = ? WHERE claim_id = ?";
        try {
            jdbcTemplate.update(sql, claim.getItemId(), claim.getClaimedBy(), claim.getDateClaimed(), claim.getClaimId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public void deleteClaim(int claimId) {
        String sql = "DELETE FROM claims WHERE claim_id = ?";
        try {
            jdbcTemplate.update(sql, claimId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    private Claim mapRowToClaim(SqlRowSet rs) {
        Claim claim = new Claim();
        claim.setClaimId(rs.getInt("claim_id"));
        claim.setItemId(rs.getInt("item_id"));
        claim.setClaimedBy(rs.getInt("claimed_by"));
        claim.setDateClaimed(rs.getDate("date_claimed"));
        return claim;
    }
}
