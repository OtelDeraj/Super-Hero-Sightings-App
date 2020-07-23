/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.service;

import com.sg.SuperHeroSighting.dao.PowerDao;
import com.sg.SuperHeroSighting.dto.Power;
import com.sg.SuperHeroSighting.exceptions.BadUpdateException;
import com.sg.SuperHeroSighting.exceptions.DuplicateNameException;
import com.sg.SuperHeroSighting.exceptions.EmptyResultException;
import com.sg.SuperHeroSighting.exceptions.InvalidEntityException;
import com.sg.SuperHeroSighting.exceptions.InvalidIdException;
import com.sg.SuperHeroSighting.exceptions.InvalidNameException;
import com.sg.SuperHeroSighting.exceptions.PowerDaoException;
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
public class PowerService {

    @Autowired
    PowerDao powDao;

    public Power getPowerById(int id) throws InvalidIdException {
        try {
            return powDao.getPowerById(id);
        } catch (PowerDaoException ex) {
            throw new InvalidIdException("No Power found for given ID");
        }
    }
    
    public Power getPowerByName(String name) throws InvalidNameException {
        try {
            return powDao.getPowerByName(name);
        } catch (PowerDaoException ex) {
            throw new InvalidNameException("No Power found for given name");
        }
    }

    public List<Power> getAllPowers() throws EmptyResultException {
        try {
            return powDao.getAllPowers();
        } catch (PowerDaoException ex) {
            throw new EmptyResultException("No powers found");
        }
    }
    
    public List<Power> getPowersBySuperId(int id) throws EmptyResultException {
        try {
            return powDao.getPowersForSuperId(id);
        } catch (PowerDaoException ex) {
            throw new EmptyResultException("No powers found for given super Id");
        }
    }
    
    public Power createPower(Power toAdd) throws InvalidEntityException, DuplicateNameException {
        validatePower(toAdd);
        try {
            return powDao.addPower(toAdd);
        } catch (PowerDaoException | BadUpdateException ex) {
            throw new InvalidEntityException("Create Power failed");
        }
    }
    
    public void editPower(Power toEdit) throws InvalidEntityException, DuplicateNameException {
        validatePower(toEdit);
        try {
            powDao.editPower(toEdit);
        } catch (PowerDaoException | BadUpdateException ex) {
            throw new InvalidEntityException("Edit Power failed");
        }
    }
    
    public void removePower(int id) throws InvalidIdException {
        try {
            powDao.removePower(id);
        } catch (PowerDaoException | BadUpdateException ex) {
            throw new InvalidIdException("Remove Power failed");
        }
    }

    private void validatePower(Power toCheck) throws InvalidEntityException {
        if(toCheck == null || toCheck.getName() == null
                || toCheck.getName().isBlank() ) {
            throw new InvalidEntityException("Power Entity and associated fields cannot be null");
        }
        if(toCheck.getName().trim().length() > 30) throw new InvalidEntityException("Power name must be 30 characters or less");
    }

}
