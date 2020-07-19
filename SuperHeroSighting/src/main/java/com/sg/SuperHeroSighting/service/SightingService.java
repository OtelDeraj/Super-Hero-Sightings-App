/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.service;

import com.sg.SuperHeroSighting.dao.SightingDao;
import com.sg.SuperHeroSighting.dto.Sighting;
import com.sg.SuperHeroSighting.exceptions.BadUpdateException;
import com.sg.SuperHeroSighting.exceptions.EmptyResultException;
import com.sg.SuperHeroSighting.exceptions.InvalidEntityException;
import com.sg.SuperHeroSighting.exceptions.InvalidIdException;
import com.sg.SuperHeroSighting.exceptions.SightingDaoException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Isaia
 */
@Service
public class SightingService { // TODO all the throws in this service will be changed to catches as the service is further implemented

    @Autowired
    SightingDao sigDao;

    //  | | Method for adding a Sighting to the database | |
    //  V V                                              V V
    public Sighting addSighting(Sighting toAdd) throws InvalidEntityException {
        validateSighting(toAdd);
        Sighting toReturn;
        try {
            toReturn = sigDao.addSighting(toAdd);
        } catch (SightingDaoException | BadUpdateException ex) {
            throw new InvalidEntityException("Add Sighting Failed");
        }
        return toReturn;
    }
    //        -------------------------------------------------------------------------

    //  | |  This method will be used when retrieving the details of a specific sighting | |
    //  V V                                                                              V V   
    public Sighting getSightingById(int id) throws InvalidIdException {
        try {
            return sigDao.getSightingById(id);
        } catch (SightingDaoException ex) {
            throw new InvalidIdException("No Sightings found for given ID");
        }
    }
    //        ---------------------------------------------    

    //  | |  These methods will be used for display purposes | |       
    //  V V                                                  V V
    public List<Sighting> getAllSightings() throws EmptyResultException {
        try {
            return sigDao.getAllSightings();
        } catch (SightingDaoException ex) {
            throw new EmptyResultException("Returned no sightings");
        }
    }

    public List<Sighting> getSightingsBySuper(int id) throws EmptyResultException {
        try {
            return sigDao.getSightingsBySuperId(id);
        } catch (SightingDaoException ex) {
            throw new EmptyResultException("Given Super returned no sightings");
        }
    }

    public List<Sighting> getSightingsByLoc(int id) throws EmptyResultException {
        try {
            return sigDao.getSightingsByLocId(id);
        } catch (SightingDaoException ex) {
            throw new EmptyResultException("Given Location returned no sightings");
        }
    }

    public List<Sighting> getSightingsByDate(Date d) throws EmptyResultException {
        try {
            return sigDao.getSightingsByDate(d);
        } catch (SightingDaoException ex) {
            throw new EmptyResultException("Given date returned no sightings");
        }
    }
    
    //       -------------------------
    
    //  | | UpdateSighting feature path | |
    //  V V                             V V
    public void updateSighting(Sighting toEdit) throws InvalidEntityException {
        validateSighting(toEdit);
        try {
            sigDao.updateSighting(toEdit);
        } catch (SightingDaoException | BadUpdateException ex) {
            throw new InvalidEntityException("Edit Sighting failed");
        }
    }
    
    
    //  | | RemoveSighting feature path | |
    //  V V                             V V
    public void removeSighting(int id) throws InvalidIdException {
        try {
            sigDao.removeSighting(id);
        } catch (SightingDaoException | BadUpdateException ex) {
            throw new InvalidIdException("Remove Sighting failed due to bad Id");
        }
    }
    
    
    
    //          -------------------------

    //  | |    Sighting Validation Methods   | |
    //  V V                                  V V 
    private void validateSighting(Sighting toValidate) throws InvalidEntityException {
        if (toValidate.getDate() == null
                || toValidate.getDate().before(new Date())
                || toValidate.getLocation() == null
                || toValidate.getSpottedSuper() == null) {
            throw new InvalidEntityException("Sighting Fields cannot be null");
        }
    }

}
