/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.dao;

import com.sg.SuperHeroSighting.dto.Super;
import java.util.List;

/**
 *
 * @author Isaia
 */
public interface SuperDao {
    
    public Super getSuperById(int id);
    
    public Super getSuperByName(String name);
    
    public List<Super> getAllSupers();
    
    public List<Super> getSupersByPowerId(int id);
    
    public List<Super> getSupersByOrgId(int id);
    
    public Super createSuper(Super toAdd);
    
    public void editSuper(Super toEdit);
    
    public void removeSuper(int id);
}
