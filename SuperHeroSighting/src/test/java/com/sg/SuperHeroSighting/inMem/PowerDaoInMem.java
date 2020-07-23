/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.inMem;

import com.sg.SuperHeroSighting.dao.PowerDao;
import com.sg.SuperHeroSighting.dto.Power;
import com.sg.SuperHeroSighting.dto.Super;
import com.sg.SuperHeroSighting.exceptions.BadUpdateException;
import com.sg.SuperHeroSighting.exceptions.PowerDaoException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
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
public class PowerDaoInMem implements PowerDao {

    List<Power> allPowers = new ArrayList<>();
    Set<Power> powerSet1 = new HashSet<>();
    Super sup1 = new Super(1, "Test", "Test Desc", powerSet1, null);
    Power p1 = new Power(1, "Flight");
    Power p2 = new Power(2, "Speed");
    Power p3 = new Power(3, "Strength");

    @Override
    public Power getPowerById(int id) throws PowerDaoException {
        List<Power> toCheck = allPowers.stream().filter(p -> p.getId() == id).collect(Collectors.toList());
        if (toCheck.isEmpty()) {
            throw new PowerDaoException("Invalid id");
        }
        return toCheck.get(0);
    }

    @Override
    public Power getPowerByName(String name) throws PowerDaoException {
        List<Power> toCheck = allPowers.stream().filter(p -> p.getName() == name).collect(Collectors.toList());
        if (toCheck.isEmpty()) {
            throw new PowerDaoException("Invalid id");
        }
        return toCheck.get(0);
    }

    @Override
    public List<Power> getAllPowers() throws PowerDaoException {
        List<Power> toReturn = allPowers;
        if (toReturn.isEmpty()) {
            throw new PowerDaoException("Invalid id");
        }
        return toReturn;
    }

    @Override
    public List<Power> getPowersForSuperId(int id) throws PowerDaoException {
        List<Power> toReturn = new ArrayList<>();
        Super currentSuper = null;
        switch(id){
            case 1:
                currentSuper = sup1;
                break;
            default:
                throw new PowerDaoException("No super found for id");
        }
        for(Power p: allPowers){
            if(currentSuper.getPowers().contains(p)){
                toReturn.add(p);
            }
        }
        if (toReturn.isEmpty()) {
            throw new PowerDaoException("Super has no powers");
        }
        return toReturn;
    }

    @Override
    public Power addPower(Power toAdd) throws PowerDaoException, BadUpdateException {
        int nextId = allPowers.stream().mapToInt(p -> p.getId()).max().orElse(0) + 1;
        toAdd.setId(nextId);
        allPowers.add(toAdd);
        return toAdd;
    }

    @Override
    public void editPower(Power toEdit) throws PowerDaoException, BadUpdateException {
        boolean validId = false;
        for(Power p : allPowers){
            if(p.getId() == toEdit.getId()){
                p.setName(toEdit.getName());
                validId = true;
                break;
            }
        }
        if(!validId){
            throw new BadUpdateException("Update Failed");
        }
    }

    @Override
    public void removePower(int id) throws PowerDaoException, BadUpdateException {
        boolean validId = false;
        Power toRemove = null;
        for(Power p : allPowers){
            if(p.getId() == id){
                toRemove = p;
                validId = true;
                break;
            }
        }
        if(!validId){
            throw new BadUpdateException("Remove failed");
        } else {
            allPowers.remove(toRemove);
        }
    }

    public void setUp() {

        powerSet1.clear();
        allPowers.clear();
        powerSet1.add(p3);
        powerSet1.add(p1);
        allPowers.add(p1);
        allPowers.add(p2);
        allPowers.add(p3);

    }

    public void clearPowers() {

    }
}
