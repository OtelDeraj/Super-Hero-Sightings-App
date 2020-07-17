/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.dao;

import com.sg.SuperHeroSighting.dto.Super;
import com.sg.SuperHeroSighting.exceptions.BadUpdateException;
import com.sg.SuperHeroSighting.exceptions.InvalidEntityException;
import com.sg.SuperHeroSighting.exceptions.SuperDaoException;
import java.util.List;

/**
 *
 * @author Isaia
 */
public interface SuperDao {
    
    public Super getSuperById(int id) throws SuperDaoException;
    
    public Super getSuperByName(String name) throws SuperDaoException;
    
    public List<Super> getAllSupers() throws SuperDaoException;
    
    public List<Super> getSupersByPowerId(int id) throws SuperDaoException;
    
    public List<Super> getSupersByOrgId(int id) throws SuperDaoException;
    
    public Super createSuper(Super toAdd) throws SuperDaoException, BadUpdateException, InvalidEntityException;
    
    public void editSuper(Super toEdit) throws SuperDaoException, BadUpdateException, InvalidEntityException;
    
    public void removeSuper(int id) throws SuperDaoException, BadUpdateException;
}
