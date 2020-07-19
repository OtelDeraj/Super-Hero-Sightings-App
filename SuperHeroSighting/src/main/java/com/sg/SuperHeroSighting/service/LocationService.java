/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.service;

import com.sg.SuperHeroSighting.dao.LocationDao;
import com.sg.SuperHeroSighting.dto.Coord;
import com.sg.SuperHeroSighting.dto.Location;
import com.sg.SuperHeroSighting.exceptions.BadUpdateException;
import com.sg.SuperHeroSighting.exceptions.EmptyResultException;
import com.sg.SuperHeroSighting.exceptions.InvalidEntityException;
import com.sg.SuperHeroSighting.exceptions.InvalidIdException;
import com.sg.SuperHeroSighting.exceptions.InvalidNameException;
import com.sg.SuperHeroSighting.exceptions.LocationDaoException;
import java.math.BigDecimal;
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
public class LocationService {
    
    @Autowired
    LocationDao locDao;

    public Location getLocationById(Integer id) throws InvalidIdException {
        try {
            return locDao.getLocationById(id);
        } catch (LocationDaoException ex) {
            throw new InvalidIdException("Could not find location for given ID");
        }
    }
    
    public Location getLocationByName(String name) throws InvalidNameException {
        try {
            return locDao.getLocationByName(name);
        } catch (LocationDaoException ex) {
            throw new InvalidNameException("Could not find location for given Name");
        }
    }
    
    public Location getLocationByAddress(String address) throws InvalidNameException {
        try {
            return locDao.getLocationByAddress(address);
        } catch (LocationDaoException ex) {
            throw new InvalidNameException("Could not find location for given address");
        }
    }
    
    public Location getLocationByCoord(Coord toSearch) throws InvalidEntityException {
        validateCoord(toSearch);
        try {
            return locDao.getLocationByCoord(toSearch);
        } catch (LocationDaoException ex) {
            throw new InvalidEntityException("Could not find location for given Coord");
        }
    }
    
    public List<Location> getAllLocations() throws EmptyResultException{
        try {
            return locDao.getAllLocations();
        } catch (LocationDaoException ex) {
            throw new EmptyResultException("No Locations found");
        }
    }

    public Location createLocation(Location toAdd) throws InvalidEntityException{
        validateLocation(toAdd);
        validateCoord(toAdd.getCoord());
        try {
            return locDao.createLocation(toAdd);
        } catch (LocationDaoException | BadUpdateException ex) {
            throw new InvalidEntityException("Create Location failed");
        }
    }
    
    public void editLocation(Location toEdit) throws InvalidEntityException {
        validateLocation(toEdit);
        validateCoord(toEdit.getCoord());
        try {
            locDao.editLocation(toEdit);
        } catch (LocationDaoException | BadUpdateException ex) {
            throw new InvalidEntityException("Edit Location failed");
        }
    }
    
    public void removeLocation(int id) throws InvalidIdException {
        try {
            locDao.removeLocation(id);
        } catch (LocationDaoException | BadUpdateException ex) {
            throw new InvalidIdException("Remove Location failed");
        }
    }
    
    private void validateCoord(Coord toCheck) throws InvalidEntityException {
        if(toCheck == null
                || toCheck.getLat() == null || toCheck.getLat().compareTo(new BigDecimal("90.00000")) == 1 
                || toCheck.getLat().compareTo(new BigDecimal("-90.00000")) == -1
                || toCheck.getLon() == null || toCheck.getLon().compareTo(new BigDecimal("180.00000")) == 1
                || toCheck.getLon().compareTo(new BigDecimal("-180.00000")) == -1){
            throw new InvalidEntityException("Coord entity and associated fields cannot be null and must be valid");
        }
    }

    private void validateLocation(Location toCheck) throws InvalidEntityException {
        if(toCheck == null
                || toCheck.getName().isBlank() || toCheck.getName().trim().length() > 50
                || toCheck.getDescription().isBlank() || toCheck.getDescription().trim().length() > 255
                || toCheck.getAddress().isBlank() || toCheck.getAddress().trim().length() > 60){
            throw new InvalidEntityException("Location entity and associated fields cannot be null and must be valid");
        }
    }
    
    
}
