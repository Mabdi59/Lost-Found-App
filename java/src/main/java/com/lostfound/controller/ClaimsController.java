package com.lostfound.controller;

import javax.validation.Valid;
import java.util.List;

import com.lostfound.dao.ClaimDao;
import com.lostfound.exception.DaoException;
import com.lostfound.model.Claim;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin
@RequestMapping("/claims")
public class ClaimsController {

    private final ClaimDao claimDao;

    public ClaimsController(ClaimDao claimDao) {
        this.claimDao = claimDao;
    }

    @GetMapping
    public List<Claim> getClaims() {
        try {
            return claimDao.getClaims();
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrieve claims.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Claim> getClaimById(@PathVariable int id) {
        try {
            Claim claim = claimDao.getClaimById(id);
            if (claim == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Claim not found.");
            }
            return new ResponseEntity<>(claim, HttpStatus.OK);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrieve claim.");
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Claim createClaim(@Valid @RequestBody Claim claim) {
        try {
            return claimDao.createClaim(claim);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to create claim.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Claim> updateClaim(@PathVariable int id, @Valid @RequestBody Claim claim) {
        try {
            Claim existingClaim = claimDao.getClaimById(id);
            if (existingClaim == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Claim not found.");
            }
            claim.setClaimId(id);
            claimDao.updateClaim(claim);
            return new ResponseEntity<>(claim, HttpStatus.OK);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to update claim.");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClaim(@PathVariable int id) {
        try {
            Claim existingClaim = claimDao.getClaimById(id);
            if (existingClaim == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Claim not found.");
            }
            claimDao.deleteClaim(id);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to delete claim.");
        }
    }
}
