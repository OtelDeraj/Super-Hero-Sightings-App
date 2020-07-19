/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.service;

import com.sg.SuperHeroSighting.dao.LocationDao;
import com.sg.SuperHeroSighting.dto.Location;
import com.sg.SuperHeroSighting.exceptions.InvalidIdException;
import com.sg.SuperHeroSighting.exceptions.LocationDaoException;
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
    
    
    
}
