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
import com.sg.SuperHeroSighting.exceptions.DuplicateNameException;
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

    public Location createLocation(Location toAdd) throws InvalidEntityException, DuplicateNameException{
        validateLocation(toAdd);
        Coord toValidate = new Coord(toAdd.getLat(), toAdd.getLon());
        validateCoord(toValidate);
        try {
            return locDao.createLocation(toAdd);
        } catch (LocationDaoException | BadUpdateException ex) {
            throw new InvalidEntityException("Create Location failed");
        }
    }
    
    public void editLocation(Location toEdit) throws InvalidEntityException, DuplicateNameException {
        validateLocation(toEdit);
        Coord toValidate = new Coord(toEdit.getLat(), toEdit.getLon());
        validateCoord(toValidate);
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
        if(toCheck == null || toCheck.getLat() == null || toCheck.getLon() == null ){
            throw new InvalidEntityException("Please enter both a latitude and longitude.");
        }
        if(toCheck.getLat().compareTo(new BigDecimal("90.00000")) == 1 || toCheck.getLat().compareTo(new BigDecimal("-90.00000")) == -1){
            throw new InvalidEntityException("Latitude must be between -90.00000 and 90.00000");
        }
        if(toCheck.getLon().compareTo(new BigDecimal("180.00000")) == 1 || toCheck.getLon().compareTo(new BigDecimal("-180.00000")) == -1){
            throw new InvalidEntityException("Longitude must be between -180.00000 and 180.00000");
        }
    }

    private void validateLocation(Location toCheck) throws InvalidEntityException {
        if(toCheck == null || toCheck.getName().isBlank() || toCheck.getDescription().isBlank() || toCheck.getAddress().isBlank() ){
            throw new InvalidEntityException("Please fill in all fields");
        }
        if(toCheck.getName().trim().length() > 50) throw new InvalidEntityException("Location name must be 50 characters or less");
        if(toCheck.getDescription().trim().length() > 255) throw new InvalidEntityException("Please describe the location in 255 characters or less");  
        if(toCheck.getAddress().trim().length() > 60) throw new InvalidEntityException("Location address must be 60 characters or less");
    }
    
    
}
