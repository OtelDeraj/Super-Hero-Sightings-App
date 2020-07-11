/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.dao;

import com.sg.SuperHeroSighting.dto.Org;
import com.sg.SuperHeroSighting.exceptions.BadUpdateException;
import com.sg.SuperHeroSighting.exceptions.OrgDaoException;
import java.util.List;

/**
 *
 * @author Isaia
 */
public interface OrgDao {
    
    public Org getOrgById(int id) throws OrgDaoException;
    
    public Org getOrgByName(String name) throws OrgDaoException;
    
    public List<Org> getAllOrgs() throws OrgDaoException;
    
    public List<Org> getOrgsForSuperId(int id) throws OrgDaoException;
    
    public Org createOrg(Org toAdd) throws OrgDaoException, BadUpdateException;
    
    public void editOrg(Org toEdit) throws OrgDaoException, BadUpdateException;
    
    public void removeOrg(int id) throws OrgDaoException, BadUpdateException;
}
