/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.service;

import com.sg.SuperHeroSighting.dao.SuperDao;
import com.sg.SuperHeroSighting.dto.Super;
import com.sg.SuperHeroSighting.exceptions.EmptyResultException;
import com.sg.SuperHeroSighting.exceptions.InvalidIdException;
import com.sg.SuperHeroSighting.exceptions.InvalidNameException;
import com.sg.SuperHeroSighting.exceptions.SuperDaoException;
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
public class SuperService {
    
    @Autowired
    SuperDao supDao;

    public Super getSuperById(Integer id) throws InvalidIdException {
        try {
            return supDao.getSuperById(id);
        } catch (SuperDaoException ex) {
            throw new InvalidIdException("No Super found for given ID");
        }
    }
    
    public Super getSuperByName(String name) throws InvalidNameException{
        try {
            return supDao.getSuperByName(name);
        } catch (SuperDaoException ex) {
            throw new InvalidNameException("No super found for given Name");
        }
    }

    public List<Super> getAllSupers() throws EmptyResultException {
        try {
            return supDao.getAllSupers();
        } catch (SuperDaoException ex) {
            throw new EmptyResultException("No supers found");
        }
    }

    public void createSuper(Super toAdd) {
        validateSuper(toAdd);
    }

    private void validateSuper(Super toAdd) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
