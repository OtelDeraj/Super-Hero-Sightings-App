/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.dao;

import com.sg.SuperHeroSighting.dto.Sighting;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Isaia
 */
public interface SightingDao {
    
    public Sighting getSightingById(int id);
    
    public Sighting getSightingBySuperId(int id);
    
    public Sighting getSightingByLocId(int id);
    
    public Sighting getSightingByDate(LocalDate date);
    
    public List<Sighting> getAllSightings();
    
    public Sighting addSighting(Sighting toAdd);
    
    public void updateSighting(Sighting toEdit);
    
    public void removeSighting(int id);
    
}
