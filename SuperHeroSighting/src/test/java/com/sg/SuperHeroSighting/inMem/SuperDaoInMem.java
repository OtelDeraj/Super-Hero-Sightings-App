/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.inMem;

import com.sg.SuperHeroSighting.dao.SuperDao;
import com.sg.SuperHeroSighting.dto.Org;
import com.sg.SuperHeroSighting.dto.Power;
import com.sg.SuperHeroSighting.dto.Super;
import com.sg.SuperHeroSighting.exceptions.BadUpdateException;
import com.sg.SuperHeroSighting.exceptions.SuperDaoException;
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
public class SuperDaoInMem implements SuperDao {

    List<Super> allSupers = new ArrayList<>();
    Set<Power> powerSet1 = new HashSet<>();
    Set<Power> powerSet2 = new HashSet<>();
    Power power1 = new Power(1, "Flight");
    Power power2 = new Power(2, "Speed");
    Power power3 = new Power(3, "Strength");
    Set<Org> orgSet1 = new HashSet<>();
    Set<Org> orgSet2 = new HashSet<>();
    Org org1 = new Org(1, "First Org", "First Description", "First Address", "111-333-5555");
    Org org2 = new Org(2, "Second Org", "Second Description", "Second Address", "222-444-6666");
    Org org3 = new Org(3, "Third Org", "Third Description", "Third Address", "333-555-7777");

    @Override
    public Super getSuperById(int id) throws SuperDaoException {
        List<Super> toCheck = allSupers.stream().filter(s -> s.getId() == id).collect(Collectors.toList());
        if (toCheck.isEmpty()) {
            throw new SuperDaoException("EmptyResult");
        }
        return toCheck.get(0);
    }

    @Override
    public Super getSuperByName(String name) throws SuperDaoException {
        List<Super> toCheck = allSupers.stream().filter(s -> s.getName().equalsIgnoreCase(name)).collect(Collectors.toList());
        if (toCheck.isEmpty()) {
            throw new SuperDaoException("EmptyResult");
        }
        return toCheck.get(0);
    }

    @Override
    public List<Super> getAllSupers() throws SuperDaoException {
        if (allSupers.isEmpty()) {
            throw new SuperDaoException("EmptyResult");
        }
        return allSupers;
    }

    @Override
    public List<Super> getSupersByPowerId(int id) throws SuperDaoException {
        Power currentPower = null;
        List<Super> toReturn = new ArrayList<>();

        switch (id) {
            case 1:
                currentPower = power1;
                break;
            case 2:
                currentPower = power2;
                break;
            case 3:
                currentPower = power3;
                break;
            default:
                throw new SuperDaoException("No power available");
        }

        for (Super s : allSupers) {
            if (s.getPowers().contains(currentPower)) {
                toReturn.add(s);
            }
        }

        return toReturn;
    }

    @Override
    public List<Super> getSupersByOrgId(int id) throws SuperDaoException {
        Org currentOrg = null;
        List<Super> toReturn = new ArrayList<>();

        switch (id) {
            case 1:
                currentOrg = org1;
                break;
            case 2:
                currentOrg = org2;
                break;
            case 3:
                currentOrg = org3;
                break;
            default:
                throw new SuperDaoException("No power available");
        }

        for (Super s : allSupers) {
            if(s.getOrgs().contains(currentOrg)){
                toReturn.add(s);
            }
        }
        
        return toReturn;
    }

    @Override
    public Super createSuper(Super toAdd) throws SuperDaoException, BadUpdateException {
        int nextId = allSupers.stream().mapToInt(s -> s.getId()).max().orElse(0) + 1;
        
        toAdd.setId(nextId);
        allSupers.add(toAdd);
        return toAdd;
    }

    @Override
    public void editSuper(Super toEdit) throws SuperDaoException, BadUpdateException {
        
        boolean isValid = false;
        for(Super s : allSupers){
            if(s.getId() == toEdit.getId()){
                s.setName(toEdit.getName());
                s.setDescription(toEdit.getDescription());
                s.setPowers(toEdit.getPowers());
                s.setOrgs(toEdit.getOrgs());
                isValid = true;
            }
        }
        if(!isValid){
            throw new BadUpdateException("Invalid Id");
        }
    }

    @Override
    public void removeSuper(int id) throws SuperDaoException, BadUpdateException {
        
        boolean validId = false;
        Super toRemove = null;
        
        for(Super s : allSupers){
            if(s.getId() == id){
                toRemove = s;
                validId = true;
                break;
            }
        }
        if(validId){
            allSupers.remove(toRemove);
        } else {
            throw new BadUpdateException("Invalid ID");
        }
    }

    public void setUp() {
        powerSet1.clear();
        powerSet1.add(power1);
        powerSet2.clear();
        powerSet2.add(power2);
        powerSet2.add(power3);
        orgSet1.clear();
        orgSet1.add(org1);
        orgSet2.clear();
        orgSet2.add(org2);
        orgSet2.add(org3);

        allSupers.clear();
        Super super1 = new Super(1, "First Hero", "First Desc", powerSet1, orgSet1);
        Super super2 = new Super(2, "Second Hero", "Second Desc", powerSet2, orgSet2);
        Super super3 = new Super(3, "Third Hero", "Third Desc", powerSet1, orgSet2);

        allSupers.add(super1);
        allSupers.add(super2);
        allSupers.add(super3);

    }

    public void clearSupers() {
        allSupers.clear();
    }
}
