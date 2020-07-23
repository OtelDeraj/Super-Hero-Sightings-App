/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.service;

import com.sg.SuperHeroSighting.TestAppConfig;
import com.sg.SuperHeroSighting.dto.Sighting;
import com.sg.SuperHeroSighting.exceptions.EmptyResultException;
import com.sg.SuperHeroSighting.exceptions.InvalidEntityException;
import com.sg.SuperHeroSighting.exceptions.InvalidIdException;
import com.sg.SuperHeroSighting.exceptions.SightingDaoException;
import com.sg.SuperHeroSighting.inMem.SightingDaoInMem;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
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
public class SightingServiceTest {

    @Autowired
    SightingService serv;

    @Autowired
    SightingDaoInMem dao;

    @BeforeEach
    public void setUp() {
        dao.setUp();
    }

    // sups
//    Super super1 = new Super(1, "First Hero", "First Desc", null, null);
//    Super super2 = new Super(2, "Second Hero", "Second Desc", null, null);
    // locs
//    Location loc1 = new Location(1, "First Name", "First Desc", "First Adr", null, null);
//    Location loc2 = new Location(2, "Second Name", "Second Desc", "Second Adr", null, null);
    // dates
//    LocalDate date1 = LocalDate.of(2020, 12, 8);
//    LocalDate date2 = LocalDate.of(2020, 12, 15);
//    LocalDate date3 = LocalDate.of(2020, 12, 25);
    // sights
//    Sighting s1 = new Sighting(date1, super1, loc1);
//    Sighting s2 = new Sighting(date2, super2, loc2);
//    Sighting s3 = new Sighting(date3, super1, loc2);
    /**
     * Test of addSighting method, of class SightingService.
     */
    @Test
    public void testAddSighting() throws SightingDaoException, InvalidEntityException {
        Sighting forExtras = dao.getSightingById(1);
        Sighting toAdd = new Sighting(LocalDate.now().minusDays(1), forExtras.getSpottedSuper(), forExtras.getLocation());
        Sighting returned = serv.addSighting(toAdd);
        assertEquals(4, returned.getId());
        assertEquals(LocalDate.now().minusDays(1), returned.getDate());
        assertEquals(forExtras.getSpottedSuper(), returned.getSpottedSuper());
        assertEquals(forExtras.getLocation(), returned.getLocation());

    }

    @Test
    public void testAddSightingNullObject() throws SightingDaoException {
        try {
            Sighting toAdd = null;
            serv.addSighting(toAdd);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddSightingBadDate() throws SightingDaoException {
        try {
            Sighting forExtras = dao.getSightingById(1);
            Sighting toAdd = new Sighting(LocalDate.now().plusDays(1), forExtras.getSpottedSuper(), forExtras.getLocation());
            serv.addSighting(toAdd);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }

    }

    @Test
    public void testAddSightingNullDate() throws SightingDaoException {
        try {
            Sighting forExtras = dao.getSightingById(1);
            Sighting toAdd = new Sighting(null, forExtras.getSpottedSuper(), forExtras.getLocation());
            serv.addSighting(toAdd);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }

    }

    @Test
    public void testAddSightingNullSuper() throws SightingDaoException {
        try {
            Sighting forExtras = dao.getSightingById(1);
            Sighting toAdd = new Sighting(LocalDate.now().minusDays(1), null, forExtras.getLocation());
            serv.addSighting(toAdd);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }

    }

    @Test
    public void testAddSightingNullLocation() throws SightingDaoException {
        try {
            Sighting forExtras = dao.getSightingById(1);
            Sighting toAdd = new Sighting(LocalDate.now().minusDays(1), forExtras.getSpottedSuper(), null);
            serv.addSighting(toAdd);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }

    }

    /**
     * Test of getSightingById method, of class SightingService.
     */
    @Test
    public void testGetSightingById() throws InvalidIdException {
        Sighting toCheck = serv.getSightingById(1);
        assertEquals(LocalDate.of(2020, 12, 8), toCheck.getDate());
        assertEquals(1, toCheck.getSpottedSuper().getId());
        assertEquals(1, toCheck.getLocation().getId());
    }
    
    @Test
    public void testGetSightingByBadId() {
        try {
            serv.getSightingById(-1);
            fail("Should have hit InvalidIdException");
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of getAllSightings method, of class SightingService.
     */
    @Test
    public void testGetAllSightings() throws EmptyResultException {
        List<Sighting> allSightings = serv.getAllSightings();
        assertEquals(3, allSightings.size());
        Sighting first = allSightings.get(0);
        assertEquals(LocalDate.of(2020, 12, 8), first.getDate());
        assertEquals(1, first.getSpottedSuper().getId());
        assertEquals(1, first.getLocation().getId());
        Sighting last = allSightings.get(allSightings.size() - 1);
        assertEquals(LocalDate.of(2020, 12, 25), last.getDate());
        assertEquals(1, last.getSpottedSuper().getId());
        assertEquals(2, last.getLocation().getId());
    }
    
    @Test
    public void testGetAllSightingsEmpty() {
        try {
            dao.clearSightings();
            serv.getAllSightings();
            fail("Should have hit EmptyResultException");
        } catch (EmptyResultException ex) {
        }
    }

    @Test
    public void testGetSightingsBySuper() throws EmptyResultException {
        List<Sighting> sightForSup = serv.getSightingsBySuper(1);
        assertEquals(2, sightForSup.size());
        Sighting first = sightForSup.get(0);
        assertEquals(LocalDate.of(2020, 12, 8), first.getDate());
        assertEquals(1, first.getSpottedSuper().getId());
        assertEquals(1, first.getLocation().getId());
    }
    
    @Test
    public void testGetSightingsBySuperBadId() {
        try {
            serv.getSightingsBySuper(-1);
            fail("Should have hit EmptyResultException");
        } catch (EmptyResultException ex) {
        }
    }

    /**
     * Test of getSightingsByLoc method, of class SightingService.
     */
    @Test
    public void testGetSightingsByLoc() throws EmptyResultException {
        List<Sighting> sightForLoc = serv.getSightingsByLoc(1);
        assertEquals(1, sightForLoc.size());
        Sighting first = sightForLoc.get(0);
        assertEquals(LocalDate.of(2020, 12, 8), first.getDate());
        assertEquals(1, first.getSpottedSuper().getId());
        assertEquals(1, first.getLocation().getId());
        
    }
    
    @Test
    public void testGetSightingsByLocBadId() {
        try {
            serv.getSightingsByLoc(-1);
            fail("Should have hit EmptyResultException");
        } catch (EmptyResultException ex) {
        }
    }

    /**
     * Test of getSightingsByDate method, of class SightingService.
     */
    @Test
    public void testGetSightingsByDate() throws EmptyResultException {
        List<Sighting> sightForDate = serv.getSightingsByDate(LocalDate.of(2020, 12, 8));
        assertEquals(1, sightForDate.size());
        Sighting first = sightForDate.get(0);
        assertEquals(LocalDate.of(2020, 12, 8), first.getDate());
        assertEquals(1, first.getSpottedSuper().getId());
        assertEquals(1, first.getLocation().getId());
    }
    
    @Test
    public void testGetSightingsByDateBad() {
        try {
            serv.getSightingsByDate(LocalDate.MIN);
            fail("Should have hit EmptyResultException");
        } catch (EmptyResultException ex) {
        }
    }

    /**
     * Test of updateSighting method, of class SightingService.
     */
    @Test
    public void testUpdateSighting() throws InvalidEntityException, SightingDaoException {

        Sighting forExtras = dao.getSightingById(2);
        Sighting preEdit = new Sighting(1, LocalDate.now().minusDays(1), forExtras.getSpottedSuper(), forExtras.getLocation());
        serv.updateSighting(preEdit);
        Sighting postEdit = dao.getSightingById(1);
        assertEquals(preEdit.getId(), postEdit.getId());
        assertEquals(preEdit.getDate(), postEdit.getDate());
        assertEquals(preEdit.getSpottedSuper(), postEdit.getSpottedSuper());
        assertEquals(preEdit.getLocation(), postEdit.getLocation());

    }

    @Test
    public void testUpdateSightingBadDate() throws SightingDaoException {
        try {
            Sighting forExtras = dao.getSightingById(2);
            Sighting preEdit = new Sighting(1, LocalDate.now().plusDays(1), forExtras.getSpottedSuper(), forExtras.getLocation());
            serv.updateSighting(preEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateSightingNullDate() throws SightingDaoException {
        try {
            Sighting forExtras = dao.getSightingById(2);
            Sighting preEdit = new Sighting(1, null, forExtras.getSpottedSuper(), forExtras.getLocation());
            serv.updateSighting(preEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateSightingNullSuper() throws SightingDaoException {
        try {
            Sighting forExtras = dao.getSightingById(2);
            Sighting preEdit = new Sighting(1, LocalDate.of(2020, 06, 3), null, forExtras.getLocation());
            serv.updateSighting(preEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateSightingNullLocation() throws SightingDaoException {
        try {
            Sighting forExtras = dao.getSightingById(2);
            Sighting preEdit = new Sighting(1, LocalDate.of(2020, 06, 3), forExtras.getSpottedSuper(), null);
            serv.updateSighting(preEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    /**
     * Test of removeSighting method, of class SightingService.
     */
    @Test
    public void testRemoveSighting() {
    }

}
