/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.dao;

import com.sg.SuperHeroSighting.dto.Power;
import java.util.List;

/**
 *
 * @author Isaia
 */
public interface PowerDao {
    
    public Power getPowerById(int id);
    
    public Power getPowerByName(String name);
    
    public List<Power> getAllPowers();
    
    public List<Power> getPowersForSuperId(int id);
    
    public Power addPower(Power toAdd);
    
    public void editPower(Power toEdit);
    
    public void removePower(int id);
}
