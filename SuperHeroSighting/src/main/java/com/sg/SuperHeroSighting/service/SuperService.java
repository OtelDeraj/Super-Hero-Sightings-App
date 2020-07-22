/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.service;

import com.sg.SuperHeroSighting.dao.SuperDao;
import com.sg.SuperHeroSighting.dto.Super;
import com.sg.SuperHeroSighting.exceptions.BadUpdateException;
import com.sg.SuperHeroSighting.exceptions.DuplicateNameException;
import com.sg.SuperHeroSighting.exceptions.EmptyResultException;
import com.sg.SuperHeroSighting.exceptions.InvalidEntityException;
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

    public Super getSuperById(int id) throws InvalidIdException {
        try {
            return supDao.getSuperById(id);
        } catch (SuperDaoException ex) {
            throw new InvalidIdException("No Super found for given ID");
        }
    }

    public Super getSuperByName(String name) throws InvalidNameException {
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

    public List<Super> getSupersByPowerId(int id) throws InvalidIdException {
        try {
            return supDao.getSupersByPowerId(id);
        } catch (SuperDaoException ex) {
            throw new InvalidIdException("No Supers found for given power Id");
        }
    }

    public List<Super> getSupersByOrgId(int id) throws InvalidIdException {
        try {
            return supDao.getSupersByOrgId(id);
        } catch (SuperDaoException ex) {
            throw new InvalidIdException("No Supers found for given power Id");
        }
    }

    public Super createSuper(Super toAdd) throws InvalidEntityException, DuplicateNameException {
        validateSuper(toAdd);
        try {
            return supDao.createSuper(toAdd);
        } catch (SuperDaoException | BadUpdateException ex) {
            throw new InvalidEntityException("Create Super entity was invalid");
        }
    }
    
    public void editSuper(Super toEdit) throws InvalidEntityException, DuplicateNameException {
        validateSuper(toEdit);
        try {
            supDao.editSuper(toEdit);
        } catch (SuperDaoException | BadUpdateException ex) {
            throw new InvalidEntityException("Super fields were invalid or missing");
        }
    }
    
    public void removeSuper(int id) throws InvalidIdException {
        try {
            supDao.removeSuper(id);
        } catch (SuperDaoException | BadUpdateException ex) {
            throw new InvalidIdException("Remove Super failed due to invalid Id");
        }
    }

    private void validateSuper(Super toCheck) throws InvalidEntityException {
        if(toCheck == null
                || toCheck.getName().isBlank() || toCheck.getDescription().isBlank()
                || toCheck.getOrgs().isEmpty() || toCheck.getPowers().isEmpty()){
            throw new InvalidEntityException("Please provide a name, description, and a set of powers and orgs. These fields cannot be empty");
        } 
        if(toCheck.getName().trim().length() > 30) throw new InvalidEntityException("Super name must be 30 characters or less");
        if(toCheck.getDescription().trim().length() > 255) throw new InvalidEntityException("Please describe your super in 255 characters or less");
    }

}
