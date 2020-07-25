/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.inMem;

import com.sg.SuperHeroSighting.dao.OrgDao;
import com.sg.SuperHeroSighting.dto.Org;
import com.sg.SuperHeroSighting.dto.Super;
import com.sg.SuperHeroSighting.exceptions.BadUpdateException;
import com.sg.SuperHeroSighting.exceptions.DuplicateNameException;
import com.sg.SuperHeroSighting.exceptions.OrgDaoException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Isaia
 */
@Repository
@Profile("memory")
public class OrgDaoInMem implements OrgDao{
    
    List<Org> allOrgs = new ArrayList<>();
    Set<Org> orgSet = new HashSet<>();
    Set<Super> superSet1 = new HashSet<>();
    Set<Super> superSet2 = new HashSet<>();
    Super sup1 = new Super(1, "FN", "FD", null, orgSet);
    

    @Override
    public Org getOrgById(int id) throws OrgDaoException {
        List<Org> toCheck = allOrgs.stream().filter(o -> o.getId() == id).collect(Collectors.toList());
        if(toCheck.isEmpty()){
            throw new OrgDaoException("Invalid id");
        }
        return toCheck.get(0);
    }

    @Override
    public Org getOrgByName(String name) throws OrgDaoException {
        List<Org> toCheck = allOrgs.stream().filter(o -> o.getName().equalsIgnoreCase(name)).collect(Collectors.toList());
        if(toCheck.isEmpty()){
            throw new OrgDaoException("Invalid name");
        }
        return toCheck.get(0);
    }

    @Override
    public List<Org> getAllOrgs() throws OrgDaoException {
        List<Org> toReturn = allOrgs;
        if(toReturn.isEmpty()){
            throw new OrgDaoException("Invalid name");
        }
        return toReturn;
    }

    @Override
    public List<Org> getOrgsForSuperId(int id) throws OrgDaoException {
        List<Org> toReturn = new ArrayList<>();
        boolean valid = false;
        Super current = null;
        switch(id){
            case 1:
                current = sup1;
                break;
            default:
                throw new OrgDaoException("No super for that ID");
        }
        for(Org o : allOrgs){
            if(current.getOrgs().contains(o)){
                toReturn.add(o);
                valid = true;
            }
        }
        if(!valid){
            throw new OrgDaoException("Super returned no powers");
        } else {
            return toReturn;
        }
    }

    @Override
    public Org createOrg(Org toAdd) throws OrgDaoException, BadUpdateException, DuplicateNameException {
        for(Org o : allOrgs){
            if(o.getName().equalsIgnoreCase(toAdd.getName())){
                throw new DuplicateNameException("Name already exists");
            }
        }
        int nextId = allOrgs.stream().mapToInt(o -> o.getId()).max().orElse(0) + 1;
        toAdd.setId(nextId);
        allOrgs.add(toAdd);
        return toAdd;
    }

    @Override
    public void editOrg(Org toEdit) throws OrgDaoException, BadUpdateException, DuplicateNameException {
        for(Org o : allOrgs){
            if(o.getName().equalsIgnoreCase(toEdit.getName())){
                if(o.getId() != toEdit.getId()){
                    throw new DuplicateNameException("Name already exists");
                } else{
                }
            }
        }
        boolean isValid = false;
        for(Org o : allOrgs){
            if(o.getId() == toEdit.getId()){
                o.setName(toEdit.getName());
                o.setDescription(toEdit.getDescription());
                o.setAddress(toEdit.getAddress());
                o.setPhone(toEdit.getPhone());
                o.setSupers(toEdit.getSupers());
                isValid = true;
            }
        }
        if(!isValid){
            throw new BadUpdateException("Edit failed");
        }
    }

    @Override
    public void removeOrg(int id) throws OrgDaoException, BadUpdateException {
        boolean isValid = false;
        Org toRemove = null;
        for(Org o : allOrgs){
            if(o.getId() == id){
                toRemove = o;
                isValid = true;
                break;
            }
        }
        if(!isValid){
            throw new BadUpdateException("Edit failed");
        } else {
            allOrgs.remove(toRemove);
        }
    }
    
    public void setUp(){
        
        orgSet.clear();
        superSet1.clear();
        allOrgs.clear();
        
        superSet1.add(sup1);
        
        Org o1 = new Org(1, "First Org", "First Desc", "First Adr", "First Phone", superSet1);
        Org o2 = new Org(2, "Second Org", "Second Desc", "Second Adr", "Second Phone", superSet1);
        Org o3 = new Org(3, "Third Org", "Third Desc", "Third Adr", "Third Phone", superSet2);
        
        orgSet.add(o3);
        
        allOrgs.add(o1);
        allOrgs.add(o2);
        allOrgs.add(o3);
        
    }
    
    public void clearOrgs(){
        allOrgs.clear();
    }
    
}
