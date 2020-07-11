/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.dao;

import com.sg.SuperHeroSighting.dto.Sighting;
import com.sg.SuperHeroSighting.exceptions.BadUpdateException;
import com.sg.SuperHeroSighting.exceptions.SightingDaoException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Isaia
 */
public interface SightingDao {
    
    public Sighting getSightingById(int id) throws SightingDaoException;
    
    public Sighting getSightingBySuperId(int id) throws SightingDaoException;
    
    public Sighting getSightingByLocId(int id) throws SightingDaoException;
    
    public Sighting getSightingByDate(LocalDate date) throws SightingDaoException;
    
    public List<Sighting> getAllSightings() throws SightingDaoException;
    
    public Sighting addSighting(Sighting toAdd) throws SightingDaoException, BadUpdateException;
    
    public void updateSighting(Sighting toEdit) throws SightingDaoException, BadUpdateException;
    
    public void removeSighting(int id) throws SightingDaoException, BadUpdateException;
    
}
