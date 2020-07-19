/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.service;

import com.sg.SuperHeroSighting.dao.OrgDao;
import com.sg.SuperHeroSighting.dto.Org;
import com.sg.SuperHeroSighting.exceptions.BadUpdateException;
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
    
    public Org addNewOrg(Org toAdd) throws InvalidEntityException {
        validateOrg(toAdd);
        try {
            return orgDao.createOrg(toAdd);
        } catch (OrgDaoException | BadUpdateException ex) {
            throw new InvalidEntityException("Add Org failed");
        }
    }
    
    public void editOrg(Org toEdit) throws InvalidEntityException {
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
        if(toCheck == null 
                || toCheck.getName().isBlank() || toCheck.getName().trim().length() > 50
                || toCheck.getDescription().isBlank() || toCheck.getDescription().trim().length() > 255
                || toCheck.getAddress().isBlank() || toCheck.getAddress().trim().length() > 60
                || toCheck.getPhone().isBlank() || toCheck.getPhone().trim().length() > 15
                || toCheck.getSupers().isEmpty()) {
            throw new InvalidEntityException("Org and associated fields cannot be null");
        }
    }
    
}
