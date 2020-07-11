/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.dao;

import com.sg.SuperHeroSighting.dto.Org;
import java.util.List;

/**
 *
 * @author Isaia
 */
public interface OrgDao {
    
    public Org getOrgById(int id);
    
    public Org getOrgByName(String name);
    
    public List<Org> getAllOrgs();
    
    public List<Org> getOrgsForSuperId(int id);
    
    public Org createOrg(Org toAdd);
    
    public void editOrg(Org toEdit);
    
    public void removeOrg(int id);
}
