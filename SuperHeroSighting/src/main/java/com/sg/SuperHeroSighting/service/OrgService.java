/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.service;

import com.sg.SuperHeroSighting.dao.OrgDao;
import com.sg.SuperHeroSighting.dto.Org;
import com.sg.SuperHeroSighting.exceptions.EmptyResultException;
import com.sg.SuperHeroSighting.exceptions.InvalidIdException;
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

    public List<Org> getAllOrgs() throws EmptyResultException {
        try {
            return orgDao.getAllOrgs();
        } catch (OrgDaoException ex) {
            throw new EmptyResultException("No organizations found");
        }
    }
    
}
