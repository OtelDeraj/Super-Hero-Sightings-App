/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.service;

import com.sg.SuperHeroSighting.dao.OrgDao;
import com.sg.SuperHeroSighting.dto.Org;
import com.sg.SuperHeroSighting.exceptions.BadUpdateException;
import com.sg.SuperHeroSighting.exceptions.DuplicateNameException;
import com.sg.SuperHeroSighting.exceptions.EmptyResultException;
import com.sg.SuperHeroSighting.exceptions.InvalidEntityException;
import com.sg.SuperHeroSighting.exceptions.InvalidIdException;
import com.sg.SuperHeroSighting.exceptions.InvalidNameException;
import com.sg.SuperHeroSighting.exceptions.OrgDaoException;
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
public class OrgService {
    
    @Autowired
    OrgDao orgDao;

    public Org getOrgById(Integer id) throws InvalidIdException {
        try {
            return orgDao.getOrgById(id);
        } catch (OrgDaoException ex) {
            throw new InvalidIdException("No org found for given ID");
        }
    }
    
    public Org getOrgByName(String name) throws InvalidNameException {
        try {
            return orgDao.getOrgByName(name);
        } catch (OrgDaoException ex) {
            throw new InvalidNameException("No org found for given name");
        }
    }

    public List<Org> getAllOrgs() throws EmptyResultException {
        try {
            return orgDao.getAllOrgs();
        } catch (OrgDaoException ex) {
            throw new EmptyResultException("No organizations found");
        }
    }
    
    public List<Org> getOrgsForSuperId(int id) throws InvalidIdException {
        try {
            return orgDao.getOrgsForSuperId(id);
        } catch (OrgDaoException ex) {
            throw new InvalidIdException("No orgs found for given super id");
        }
    }
    
    public Org addNewOrg(Org toAdd) throws InvalidEntityException, DuplicateNameException {
        validateOrg(toAdd);
        try {
            return orgDao.createOrg(toAdd);
        } catch (OrgDaoException | BadUpdateException ex) {
            throw new InvalidEntityException("Add Org failed");
        }
    }
    
    public void editOrg(Org toEdit) throws InvalidEntityException, DuplicateNameException {
        validateOrg(toEdit);
        try {
            orgDao.editOrg(toEdit);
        } catch (OrgDaoException | BadUpdateException ex) {
            throw new InvalidEntityException("Edit Org failed");
        }
    }
    
    public void removeOrg(int id) throws InvalidIdException {
        try {
            orgDao.removeOrg(id);
        } catch (OrgDaoException | BadUpdateException ex) {
            throw new InvalidIdException("Remove Org failed");
        }
    }

    private void validateOrg(Org toCheck) throws InvalidEntityException {
        if(toCheck == null || toCheck.getName().isBlank() || toCheck.getDescription().isBlank() 
                || toCheck.getAddress().isBlank() || toCheck.getPhone().isBlank() || toCheck.getSupers().isEmpty()) {
            throw new InvalidEntityException("Please ensure all fields are filled in.");
        }
        if(toCheck.getName().trim().length() > 50) throw new InvalidEntityException("Org name must be 50 characters or less");
        if(toCheck.getDescription().trim().length() > 255) throw new InvalidEntityException("Please describe this org in 255 characters or less");
        if(toCheck.getAddress().trim().length() > 60) throw new InvalidEntityException("Org address must be 60 characters or less");
        if(toCheck.getPhone().trim().length() > 15) throw new InvalidEntityException("Phone number cannot be more than 15 characters long");
    }
    
}
