/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.dao;

import com.sg.SuperHeroSighting.dto.Location;
import com.sg.SuperHeroSighting.exceptions.BadUpdateException;
import com.sg.SuperHeroSighting.exceptions.LocationDaoException;
import java.awt.Point;
import java.util.List;

/**
 *
 * @author Isaia
 */
public interface LocationDao {
    
    public Location getLocationById(int id) throws LocationDaoException;
    
    public Location getLocationByName(String name) throws LocationDaoException;
    
    public Location getLocationByAddress(String address) throws LocationDaoException;
    
    public Location getLocationByCoord(Point coord) throws LocationDaoException;
    
    public List<Location> getAllLocations() throws LocationDaoException;
    
    public Location createLocation(Location toAdd) throws LocationDaoException, BadUpdateException;
    
    public void editLocation(Location toEdit) throws LocationDaoException, BadUpdateException;
    
    public void removeLocation(int id) throws LocationDaoException, BadUpdateException;
    
    
}
