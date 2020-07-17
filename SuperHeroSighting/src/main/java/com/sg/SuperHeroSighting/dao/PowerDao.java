/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.dao;

import com.sg.SuperHeroSighting.dto.Power;
import com.sg.SuperHeroSighting.exceptions.BadUpdateException;
import com.sg.SuperHeroSighting.exceptions.InvalidEntityException;
import com.sg.SuperHeroSighting.exceptions.PowerDaoException;
import java.util.List;

/**
 *
 * @author Isaia
 */
public interface PowerDao {
    
    public Power getPowerById(int id) throws PowerDaoException;
    
    public Power getPowerByName(String name) throws PowerDaoException;
    
    public List<Power> getAllPowers() throws PowerDaoException;
    
    public List<Power> getPowersForSuperId(int id) throws PowerDaoException;
    
    public Power addPower(Power toAdd) throws PowerDaoException, BadUpdateException, InvalidEntityException;
    
    public void editPower(Power toEdit) throws PowerDaoException, BadUpdateException, InvalidEntityException;
    
    public void removePower(int id) throws PowerDaoException, BadUpdateException;
}
