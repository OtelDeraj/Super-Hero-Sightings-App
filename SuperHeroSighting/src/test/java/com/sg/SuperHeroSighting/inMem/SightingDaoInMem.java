/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.inMem;

import com.sg.SuperHeroSighting.dao.SightingDao;
import com.sg.SuperHeroSighting.dto.Location;
import com.sg.SuperHeroSighting.dto.Sighting;
import com.sg.SuperHeroSighting.dto.Super;
import com.sg.SuperHeroSighting.exceptions.BadUpdateException;
import com.sg.SuperHeroSighting.exceptions.SightingDaoException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Isaia
 */
@Repository
@Profile("memory")
public class SightingDaoInMem implements SightingDao{
    
    
    List<Sighting> allSightings = new ArrayList<>();
    Super super1 = new Super(1, "First Hero", "First Desc", null, null);
    Super super2 = new Super(2, "Second Hero", "Second Desc", null, null);
    Location loc1 = new Location(1, "First Name", "First Desc", "First Adr", null, null);
    Location loc2 = new Location(1, "Second Name", "Second Desc", "Second Adr", null, null);
    LocalDate date1 = LocalDate.of(2020, 12, 8);
    LocalDate date2 = LocalDate.of(2020, 12, 15);
    LocalDate date3 = LocalDate.of(2020, 12, 25);

    @Override
    public Sighting getSightingById(int id) throws SightingDaoException {
        List<Sighting> toCheck = allSightings.stream().filter(s -> s.getId() == id).collect(Collectors.toList());
        if(toCheck.isEmpty()){
            throw new SightingDaoException("Invalid ID");
        }
        return toCheck.get(0);
    }

    @Override
    public List<Sighting> getSightingsBySuperId(int id) throws SightingDaoException {
        List<Sighting> toCheck = allSightings.stream().filter(s -> s.getSpottedSuper().getId() == id).collect(Collectors.toList());
        if(toCheck.isEmpty()){
            throw new SightingDaoException("Invalid ID");
        }
        return toCheck;
    }

    @Override
    public List<Sighting> getSightingsByLocId(int id) throws SightingDaoException {
        List<Sighting> toCheck = allSightings.stream().filter(s -> s.getLocation().getId() == id).collect(Collectors.toList());
        if(toCheck.isEmpty()){
            throw new SightingDaoException("Invalid ID");
        }
        return toCheck;
    }

    @Override
    public List<Sighting> getSightingsByDate(LocalDate date) throws SightingDaoException {
        List<Sighting> toCheck = allSightings.stream().filter(s -> s.getDate().equals(date)).collect(Collectors.toList());
        if(toCheck.isEmpty()){
            throw new SightingDaoException("Invalid ID");
        }
        return toCheck;
    }

    @Override
    public List<Sighting> getAllSightings() throws SightingDaoException {
        List<Sighting> toCheck = allSightings;
        if(toCheck.isEmpty()){
            throw new SightingDaoException("Invalid ID");
        }
        return toCheck;
    }

    @Override
    public Sighting addSighting(Sighting toAdd) throws SightingDaoException, BadUpdateException {
        int nextId = allSightings.stream().mapToInt(s -> s.getId()).max().orElse(0) + 1;
        toAdd.setId(nextId);
        allSightings.add(toAdd);
        return toAdd;
    }

    @Override
    public void updateSighting(Sighting toEdit) throws SightingDaoException, BadUpdateException {
        boolean validId = false;
        for(Sighting s : allSightings){
            if(s.getId() == toEdit.getId()){
                s.setDate(toEdit.getDate());
                s.setLocation(toEdit.getLocation());
                s.setSpottedSuper(toEdit.getSpottedSuper());
                validId = true;
            }
        }
        if(!validId){
            throw new BadUpdateException("Invalid Id");
        }
    }

    @Override
    public void removeSighting(int id) throws SightingDaoException, BadUpdateException {
        boolean validId = false;
        Sighting toRemove = null;
        for(Sighting s : allSightings){
            if(s.getId() == id){
                toRemove = s;
                validId = true;
                break;
            }
        }
        if(!validId){
            throw new BadUpdateException("Invalid Id");
        } else {
            allSightings.remove(toRemove);
        }
    }

    @Override
    public List<Sighting> getLastTenSightings() throws SightingDaoException {
        List<Sighting> toReturn = new ArrayList<>();
        
        for(int i = 0; i < 10; i++){
            toReturn.add(allSightings.get(allSightings.size() - 1 - i));
        }
        if(toReturn.isEmpty()){
            throw new SightingDaoException("Invalid ID");
        }
        return toReturn;
            
    }
    
    
    public void setUp(){
        allSightings.clear();
        Sighting s1 = new Sighting(date1, super1, loc1);
        Sighting s2 = new Sighting(date2, super2, loc2);
        Sighting s3 = new Sighting(date3, super1, loc2);
        allSightings.add(s1);
        allSightings.add(s2);
        allSightings.add(s3);
        
    }
    
    public void clearSightings(){
        allSightings.clear();
    }
}
