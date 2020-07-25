/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.service;

import com.sg.SuperHeroSighting.TestAppConfig;
import com.sg.SuperHeroSighting.dto.Coord;
import com.sg.SuperHeroSighting.dto.Location;
import com.sg.SuperHeroSighting.exceptions.DuplicateNameException;
import com.sg.SuperHeroSighting.exceptions.EmptyResultException;
import com.sg.SuperHeroSighting.exceptions.InvalidCoordException;
import com.sg.SuperHeroSighting.exceptions.InvalidEntityException;
import com.sg.SuperHeroSighting.exceptions.InvalidIdException;
import com.sg.SuperHeroSighting.exceptions.InvalidNameException;
import com.sg.SuperHeroSighting.exceptions.LocationDaoException;
import com.sg.SuperHeroSighting.inMem.LocationDaoInMem;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author Isaia
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestAppConfig.class)
public class LocationServiceTest {
    
    @Autowired
    LocationService service;
    
    @Autowired
    LocationDaoInMem dao;
    
    
    @BeforeEach
    public void setUp() {
        dao.setUp();
    }

    /**
     * Test of getLocationById method, of class LocationService.
     */
    @Test
    public void testGetLocationById() throws InvalidIdException {
        Location toCheck = service.getLocationById(1);
        assertEquals(1, toCheck.getId());
        assertEquals("Loc1", toCheck.getName());
        assertEquals("LD1", toCheck.getDescription());
        assertEquals("LA1", toCheck.getAddress());
        assertEquals(new BigDecimal("90.00000"), toCheck.getLat());
        assertEquals(new BigDecimal("180.00000"), toCheck.getLon());
    }
    
    @Test
    public void testGetLocationByBadId() {
        try {
            service.getLocationById(-1);
            fail("Should have hit InvalidIdException");
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of getLocationByName method, of class LocationService.
     */
    @Test
    public void testGetLocationByName() throws InvalidNameException {
        Location toCheck = service.getLocationByName("Loc1");
        assertEquals(1, toCheck.getId());
        assertEquals("Loc1", toCheck.getName());
        assertEquals("LD1", toCheck.getDescription());
        assertEquals("LA1", toCheck.getAddress());
        assertEquals(new BigDecimal("90.00000"), toCheck.getLat());
        assertEquals(new BigDecimal("180.00000"), toCheck.getLon());
    }
    
    @Test
    public void testGetLocationByBadName() {
        try {
            service.getLocationByName("Fake Name");
            fail("Should have hit InvalidNameException");
        } catch (InvalidNameException ex) {
        }
    }

    /**
     * Test of getLocationByAddress method, of class LocationService.
     */
    @Test
    public void testGetLocationByAddress() throws InvalidNameException {
        Location toCheck = service.getLocationByAddress("LA1");
        assertEquals(1, toCheck.getId());
        assertEquals("Loc1", toCheck.getName());
        assertEquals("LD1", toCheck.getDescription());
        assertEquals("LA1", toCheck.getAddress());
        assertEquals(new BigDecimal("90.00000"), toCheck.getLat());
        assertEquals(new BigDecimal("180.00000"), toCheck.getLon());
    }
    
    @Test
    public void testGetLocationByBadAddress() {
        try {
            service.getLocationByAddress("Fake Address");
            fail("Should have hit InvalidNameException");
        } catch (InvalidNameException ex) {
        }
    }

    /**
     * Test of getLocationByCoord method, of class LocationService.
     */
    @Test
    public void testGetLocationByCoord() throws InvalidEntityException, InvalidCoordException { // TODO: figure out what is wrong with this test. Probably something in the mem dao
        Coord toSearch = new Coord(new BigDecimal("90.00000"), new BigDecimal("180.00000"));
        Location toCheck = service.getLocationByCoord(toSearch);
        assertEquals(1, toCheck.getId());
        assertEquals("Loc1", toCheck.getName());
        assertEquals("LD1", toCheck.getDescription());
        assertEquals("LA1", toCheck.getAddress());
        assertEquals(toSearch.getLat(), toCheck.getLat());
        assertEquals(toSearch.getLon(), toCheck.getLon());
    }
    
    @Test
    public void testGetLocationByBadLatCoord() throws InvalidCoordException {
        try {
            Coord toSearch = new Coord(new BigDecimal("91.00000"), new BigDecimal("180.00000"));
            service.getLocationByCoord(toSearch);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testGetLocationByBadLonCoord() throws InvalidCoordException {
        try {
            Coord toSearch = new Coord(new BigDecimal("90.00000"), new BigDecimal("181.00000"));
            service.getLocationByCoord(toSearch);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testGetLocationByBadCoord() throws InvalidEntityException {
        try {
            Coord toSearch = new Coord(new BigDecimal("0.00000"), new BigDecimal("0.00000"));
            service.getLocationByCoord(toSearch);
            fail("Should have hit InvalidCoordException");
        } catch (InvalidCoordException ex) {
        }
    }

    /**
     * Test of getAllLocations method, of class LocationService.
     */
    @Test
    public void testGetAllLocations() throws EmptyResultException {
        List<Location> allLocations = service.getAllLocations();
        assertEquals(3, allLocations.size());
        Location first = allLocations.get(0);
        assertEquals(1, first.getId());
        assertEquals("Loc1", first.getName());
        assertEquals("LD1", first.getDescription());
        assertEquals("LA1", first.getAddress());
        assertEquals(new BigDecimal("90.00000"), first.getLat());
        assertEquals(new BigDecimal("180.00000"), first.getLon());
        Location last = allLocations.get(allLocations.size() - 1);
        assertEquals(3, last.getId());
        assertEquals("Loc3", last.getName());
        assertEquals("LD3", last.getDescription());
        assertEquals("LA3", last.getAddress());
        assertEquals(new BigDecimal("45.00000"), last.getLat());
        assertEquals(new BigDecimal("90.00000"), last.getLon());
    }
    
    @Test
    public void testGetAllLocationsEmptyReturn() {
        try {
            dao.clearLocations();
            service.getAllLocations();
            fail("Should have hit EmptyResultException");
        } catch (EmptyResultException ex) {
        }
    }

    /**
     * Test of createLocation method, of class LocationService.
     */
    @Test
    public void testCreateLocation() throws InvalidEntityException, DuplicateNameException {
        Location toAdd = new Location("Loc4", "LD4", "LA4", new BigDecimal("0.00000"), new BigDecimal("0.00000"));
        Location returned = service.createLocation(toAdd);
        assertEquals(4, returned.getId());
        assertEquals(toAdd.getName(), returned.getName());
        assertEquals(toAdd.getDescription(), returned.getDescription());
        assertEquals(toAdd.getAddress(), returned.getAddress());
        assertEquals(toAdd.getLat(), returned.getLat());
        assertEquals(toAdd.getLon(), returned.getLon());
    }
    
    @Test
    public void testCreateLocationNullObject() throws DuplicateNameException {
        try {
            Location toAdd = null;
            service.createLocation(toAdd);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testCreateLocationDuplicateName() throws InvalidEntityException {
        try {
            Location toAdd = new Location("Loc1", "LD4", "LA4", new BigDecimal("0.00000"), new BigDecimal("0.00000"));
            service.createLocation(toAdd);
            fail("Should have hit DuplicateNameException");
        } catch (DuplicateNameException ex) {
        }
    }
    
    @Test
    public void testCreateLocationEmptyName() throws DuplicateNameException {
        try {
            Location toAdd = new Location("", "LD4", "LA4", new BigDecimal("0.00000"), new BigDecimal("0.00000"));
            service.createLocation(toAdd);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testCreateLocationBadName() throws DuplicateNameException {
        try {
            Location toAdd = new Location(createBadName(), "LD4", "LA4", new BigDecimal("0.00000"), new BigDecimal("0.00000"));
            service.createLocation(toAdd);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testCreateLocationEmptyDesc() throws DuplicateNameException {
        try {
            Location toAdd = new Location("Loc4", "", "LA4", new BigDecimal("0.00000"), new BigDecimal("0.00000"));
            service.createLocation(toAdd);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testCreateLocationBadDesc() throws DuplicateNameException {
        try {
            Location toAdd = new Location("Loc4", createBadDescription(), "LA4", new BigDecimal("0.00000"), new BigDecimal("0.00000"));
            service.createLocation(toAdd);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testCreateLocationEmptyAdr() throws DuplicateNameException {
        try {
            Location toAdd = new Location("Loc4", "LD4", "", new BigDecimal("0.00000"), new BigDecimal("0.00000"));
            service.createLocation(toAdd);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testCreateLocationBadAdr() throws DuplicateNameException {
        try {
            Location toAdd = new Location("Loc4", "LD4", createBadAddress(), new BigDecimal("0.00000"), new BigDecimal("0.00000"));
            service.createLocation(toAdd);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testCreateLocationBadLat() throws DuplicateNameException {
        try {
            Location toAdd = new Location("Loc4", "LD4", "LA4", new BigDecimal("-91.00000"), new BigDecimal("0.00000"));
            service.createLocation(toAdd);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testCreateLocationBadLon() throws DuplicateNameException {
        try {
            Location toAdd = new Location("Loc4", "LD4", "LA4", new BigDecimal("0.00000"), new BigDecimal("181.00000"));
            service.createLocation(toAdd);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    /**
     * Test of editLocation method, of class LocationService.
     */
    @Test
    public void testEditLocation() throws InvalidEntityException, DuplicateNameException, LocationDaoException {
        Location toEdit = new Location(1, "Loc1 PE", "LD1 PE", "LA! PE", new BigDecimal("89.00000"), new BigDecimal("179.00000"));
        service.editLocation(toEdit);
        Location postEdit = dao.getLocationById(1);
        assertEquals(1, postEdit.getId());
        assertEquals(toEdit.getName(), postEdit.getName());
        assertEquals(toEdit.getDescription(), postEdit.getDescription());
        assertEquals(toEdit.getAddress(), postEdit.getAddress());
        assertEquals(toEdit.getLat(), postEdit.getLat());
        assertEquals(toEdit.getLon(), postEdit.getLon());
    }
    
    @Test
    public void testEditLocationNullObject() throws DuplicateNameException {
        try {
            Location toEdit = null;
            service.editLocation(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditLocationDuplicateName() throws InvalidEntityException {
        try {
            Location toEdit = new Location(1, "Loc2", "LD1 PE", "LA! PE", new BigDecimal("89.00000"), new BigDecimal("179.00000"));
            service.editLocation(toEdit);
            fail("Should have hit DuplicateNameException");
        } catch (DuplicateNameException ex) {
        }
    }
    
    @Test
    public void testEditLocationEmptyName() throws DuplicateNameException {
        try {
            Location toEdit = new Location(1, "", "LD1 PE", "LA! PE", new BigDecimal("89.00000"), new BigDecimal("179.00000"));
            service.editLocation(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditLocationBadName() throws DuplicateNameException {
        try {
            Location toEdit = new Location(1, createBadName(), "LD1 PE", "LA! PE", new BigDecimal("89.00000"), new BigDecimal("179.00000"));
            service.editLocation(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditLocationEmptyDesc() throws DuplicateNameException {
        try {
            Location toEdit = new Location(1, "Loc1 PE", "", "LA! PE", new BigDecimal("89.00000"), new BigDecimal("179.00000"));
            service.editLocation(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditLocationBadDesc() throws DuplicateNameException {
        try {
            Location toEdit = new Location(1, "Loc1 PE", createBadDescription(), "LA! PE", new BigDecimal("89.00000"), new BigDecimal("179.00000"));
            service.editLocation(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditLocationEmptyAdr() throws DuplicateNameException {
        try {
            Location toEdit = new Location(1, "Loc1 PE", "LD1 PE", "", new BigDecimal("89.00000"), new BigDecimal("179.00000"));
            service.editLocation(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditLocationBadAdr() throws DuplicateNameException {
        try {
            Location toEdit = new Location(1, "Loc1 PE", "LD1 PE", createBadAddress(), new BigDecimal("89.00000"), new BigDecimal("179.00000"));
            service.editLocation(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditLocationBadLat() throws DuplicateNameException {
        try {
            Location toEdit = new Location(1, "Loc1 PE", "LD1 PE", "LA! PE", new BigDecimal("91.00000"), new BigDecimal("179.00000"));
            service.editLocation(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditLocationBadLon() throws DuplicateNameException {
        try {
            Location toEdit = new Location(1, "Loc1 PE", "LD1 PE", "LA! PE", new BigDecimal("89.00000"), new BigDecimal("181.00000"));
            service.editLocation(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    /**
     * Test of removeLocation method, of class LocationService.
     */
    @Test
    public void testRemoveLocation() throws InvalidIdException, EmptyResultException {
        List<Location> preRemove = service.getAllLocations();
        assertEquals(3, preRemove.size());
        service.removeLocation(2);
        List<Location> postRemove = service.getAllLocations();
        Location first = postRemove.get(0);
        assertEquals(1, first.getId());
        assertEquals("Loc1", first.getName());
        assertEquals("LD1", first.getDescription());
        assertEquals("LA1", first.getAddress());
        assertEquals(new BigDecimal("90.00000"), first.getLat());
        assertEquals(new BigDecimal("180.00000"), first.getLon());
        Location last = postRemove.get(postRemove.size() - 1);
        assertEquals(3, last.getId());
        assertEquals("Loc3", last.getName());
        assertEquals("LD3", last.getDescription());
        assertEquals("LA3", last.getAddress());
        assertEquals(new BigDecimal("45.00000"), last.getLat());
        assertEquals(new BigDecimal("90.00000"), last.getLon()); 
    }
    
    @Test
    public void testRemoveLocationBadId() {
        try {
            service.removeLocation(-1);
            fail("Should have hit InvalidIdException");
        } catch (InvalidIdException ex) {
        }
    }
    
    private String createBadName(){
        char[] chars = new char[51];
        Arrays.fill(chars, 'a');
        return new String(chars);
    }
    
    private String createBadDescription(){
        char[] chars = new char[256];
        Arrays.fill(chars, 'a');
        return new String(chars);
    }
    
    private String createBadAddress(){
        char[] chars = new char[61];
        Arrays.fill(chars, 'a');
        return new String(chars);
    }
    
}
