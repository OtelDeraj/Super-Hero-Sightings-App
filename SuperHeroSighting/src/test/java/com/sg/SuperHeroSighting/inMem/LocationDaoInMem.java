/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.inMem;

import com.sg.SuperHeroSighting.dao.LocationDao;
import com.sg.SuperHeroSighting.dto.Coord;
import com.sg.SuperHeroSighting.dto.Location;
import com.sg.SuperHeroSighting.exceptions.BadUpdateException;
import com.sg.SuperHeroSighting.exceptions.DuplicateNameException;
import com.sg.SuperHeroSighting.exceptions.LocationDaoException;
import java.math.BigDecimal;
import java.util.ArrayList;
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
public class LocationDaoInMem implements LocationDao {

    List<Location> allLocations = new ArrayList<>();

    @Override
    public Location getLocationById(int id) throws LocationDaoException {
        List<Location> toCheck = allLocations.stream().filter(l -> l.getId() == id).collect(Collectors.toList());
        if (toCheck.isEmpty()) {
            throw new LocationDaoException("Invalid ID");
        }
        return toCheck.get(0);
    }

    @Override
    public Location getLocationByName(String name) throws LocationDaoException {
        List<Location> toCheck = allLocations.stream().filter(l -> l.getName().equalsIgnoreCase(name)).collect(Collectors.toList());
        if (toCheck.isEmpty()) {
            throw new LocationDaoException("Invalid name");
        }
        return toCheck.get(0);
    }

    @Override
    public Location getLocationByAddress(String address) throws LocationDaoException {
        List<Location> toCheck = allLocations.stream().filter(l -> l.getAddress().equalsIgnoreCase(address)).collect(Collectors.toList());
        if (toCheck.isEmpty()) {
            throw new LocationDaoException("Invalid address");
        }
        return toCheck.get(0);
    }

    @Override
    public Location getLocationByCoord(Coord coord) throws LocationDaoException {
        Location toReturn = null;
        for(Location l : allLocations){
            if(l.getLat().equals(coord.getLat()) && l.getLon().equals(coord.getLon())){
                toReturn = l;
                break;
            }
        }
        if(toReturn == null){
            throw new LocationDaoException("No Location found for coord");
        }
        return toReturn;
    }

    @Override
    public List<Location> getAllLocations() throws LocationDaoException {
        List<Location> toCheck = allLocations;
        if (toCheck.isEmpty()) {
            throw new LocationDaoException("Empty Result");
        }
        return toCheck;
    }

    @Override
    public Location createLocation(Location toAdd) throws LocationDaoException, BadUpdateException, DuplicateNameException {
        for (Location l : allLocations) {
            if (l.getName().equalsIgnoreCase(toAdd.getName())) {
                throw new DuplicateNameException("Name already exists");
            }
        }
        int nextId = allLocations.stream().mapToInt(l -> l.getId()).max().orElse(0) + 1;
        toAdd.setId(nextId);
        allLocations.add(toAdd);
        return toAdd;
    }

    @Override
    public void editLocation(Location toEdit) throws LocationDaoException, BadUpdateException, DuplicateNameException {
        for (Location l : allLocations) {
            if (l.getName().equalsIgnoreCase(toEdit.getName())) {
                if (l.getId() != toEdit.getId()) {
                    throw new DuplicateNameException("Name already exists");
                } else {
                }
            }
        }
        boolean isValid = false;
        for (Location l : allLocations) {
            if (l.getId() == toEdit.getId()) {
                l.setName(toEdit.getName());
                l.setDescription(toEdit.getDescription());
                l.setAddress(toEdit.getAddress());
                l.setLat(toEdit.getLat());
                l.setLon(toEdit.getLon());
                isValid = true;
                break;
            }
        }
        if (!isValid) {
            throw new BadUpdateException("Update Failed");
        }
    }

    @Override
    public void removeLocation(int id) throws LocationDaoException, BadUpdateException {
        boolean isValid = false;
        Location toRemove = null;
        for (Location l : allLocations) {
            if (l.getId() == id) {
                toRemove = l;
                isValid = true;
                break;
            }
        }
        if (!isValid) {
            throw new BadUpdateException("Update Failed");
        } else {
            allLocations.remove(toRemove);
        }
    }

    public void setUp() {
        allLocations.clear();

        Location l1 = new Location(1, "Loc1", "LD1", "LA1", new BigDecimal("90.00000"), new BigDecimal("180.00000"));
        Location l2 = new Location(2, "Loc2", "LD2", "LA2", new BigDecimal("-90.00000"), new BigDecimal("-180.00000"));
        Location l3 = new Location(3, "Loc3", "LD3", "LA3", new BigDecimal("45.00000"), new BigDecimal("90.00000"));

        allLocations.add(l1);
        allLocations.add(l2);
        allLocations.add(l3);
    }
    
    public void clearLocations(){
        allLocations.clear();
    }

}
