/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.dao;

import com.sg.SuperHeroSighting.dto.Sighting;
import com.sg.SuperHeroSighting.exceptions.BadUpdateException;
import com.sg.SuperHeroSighting.exceptions.InvalidEntityException;
import com.sg.SuperHeroSighting.exceptions.SightingDaoException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Isaia
 */
public interface SightingDao {
    
    public Sighting getSightingById(int id) throws SightingDaoException;
    
    public List<Sighting> getSightingsBySuperId(int id) throws SightingDaoException;
    
    public List<Sighting> getSightingsByLocId(int id) throws SightingDaoException;
    
    public List<Sighting> getSightingsByDate(Date date) throws SightingDaoException;
    
    public List<Sighting> getAllSightings() throws SightingDaoException;
    
    public Sighting addSighting(Sighting toAdd) throws SightingDaoException, BadUpdateException, InvalidEntityException;
    
    public void updateSighting(Sighting toEdit) throws SightingDaoException, BadUpdateException, InvalidEntityException;
    
    public void removeSighting(int id) throws SightingDaoException, BadUpdateException;
    
}
