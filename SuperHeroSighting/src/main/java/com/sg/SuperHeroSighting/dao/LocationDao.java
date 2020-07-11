/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.dao;

import com.sg.SuperHeroSighting.dto.Location;
import java.awt.Point;
import java.util.List;

/**
 *
 * @author Isaia
 */
public interface LocationDao {
    
    public Location getLocationById(int id);
    
    public Location getLocationByName(String name);
    
    public Location getLocationByAddress(String address);
    
    public Location getLocationByCoord(Point coord);
    
    public List<Location> getAllLocations();
    
    public Location createLocation(Location toAdd);
    
    public void editLocation(Location toEdit);
    
    public void removeLocation(int id);
    
    
}
