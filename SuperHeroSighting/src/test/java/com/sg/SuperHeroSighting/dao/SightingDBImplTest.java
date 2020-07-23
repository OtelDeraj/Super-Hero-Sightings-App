/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.dao;

import com.sg.SuperHeroSighting.TestAppConfig;
import com.sg.SuperHeroSighting.dto.Sighting;
import com.sg.SuperHeroSighting.exceptions.BadUpdateException;
import com.sg.SuperHeroSighting.exceptions.InvalidEntityException;
import com.sg.SuperHeroSighting.exceptions.SightingDaoException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author Isaia
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestAppConfig.class)
@ActiveProfiles("database")
public class SightingDBImplTest {

    @Autowired
    SightingDao dao;

    @Autowired
    JdbcTemplate template;

    @BeforeEach
    public void setUp() {
        template.update("DELETE FROM Affiliations");
        template.update("DELETE FROM Super_Powers");
        template.update("DELETE FROM Sightings");
        template.update("ALTER TABLE Sightings auto_increment = 1");
        template.update("DELETE FROM Locations");
        template.update("ALTER TABLE Locations auto_increment = 1");
        template.update("INSERT INTO Locations(name, description, address, lat, lon) VALUES"
                + "('First Loc', 'First Desc', 'First Address', 90.00000, 180.00000),"
                + "('Second Loc', 'Second Desc', 'Second Address', 45.00000, 90.00000),"
                + "('Third Loc', 'Third Desc', 'Third Address', -45.00000, -90.00000)");
        template.update("DELETE FROM Supers");
        template.update("ALTER TABLE Supers auto_increment = 1");
        template.update("INSERT INTO Supers(name, description) VALUES"
                + "('First Hero', 'First Desc'),"
                + "('Second Hero', 'Second Desc'),"
                + "('Third Hero', 'Third Desc')");
        template.update("INSERT INTO Sightings(sightDate, superId, locId) VALUES"
                + "('2020-10-20', 1, 3),"
                + "('2020-10-18', 2, 2),"
                + "('2020-10-15', 3, 1)");
    }

    /**
     * Test of getSightingById method, of class SightingDBImpl.
     */
    @Test
    public void testGetSightingByIdGoldenPath() throws SightingDaoException {
        Sighting toTest = dao.getSightingById(1);
        assertEquals(1, toTest.getId());
        assertEquals("2020-10-20", toTest.getDate().toString());
        assertEquals(1, toTest.getSpottedSuper().getId());
        assertEquals("First Hero", toTest.getSpottedSuper().getName());
        assertEquals("First Desc", toTest.getSpottedSuper().getDescription());
        assertEquals(3, toTest.getLocation().getId());
        assertEquals("Third Loc", toTest.getLocation().getName());
        assertEquals("Third Desc", toTest.getLocation().getDescription());
        assertEquals("Third Address", toTest.getLocation().getAddress());
        assertEquals(new BigDecimal("-45.00000"), toTest.getLocation().getLat());
        assertEquals(new BigDecimal("-90.00000"), toTest.getLocation().getLon());
    }

    @Test
    public void testGetSightingByBadId() {
        try {
            dao.getSightingById(-1);
            fail("Should have hit SightingDaoException");
        } catch (SightingDaoException ex) {
        }
    }

    /**
     * Test of getSightingsBySuperId method, of class SightingDBImpl.
     */
    @Test
    public void testGetSightingsBySuperIdGoldenPath() throws SightingDaoException {
        List<Sighting> forSuper = dao.getSightingsBySuperId(1);
        assertEquals(1, forSuper.size());
        Sighting toTest = forSuper.get(0);
        assertEquals(1, toTest.getId());
        assertEquals("2020-10-20", toTest.getDate().toString());
        assertEquals(1, toTest.getSpottedSuper().getId());
        assertEquals("First Hero", toTest.getSpottedSuper().getName());
        assertEquals("First Desc", toTest.getSpottedSuper().getDescription());
        assertEquals(3, toTest.getLocation().getId());
        assertEquals("Third Loc", toTest.getLocation().getName());
        assertEquals("Third Desc", toTest.getLocation().getDescription());
        assertEquals("Third Address", toTest.getLocation().getAddress());
        assertEquals(new BigDecimal("-45.00000"), toTest.getLocation().getLat());
        assertEquals(new BigDecimal("-90.00000"), toTest.getLocation().getLon());
    }
    
    @Test
    public void testGetSightingsByBadSuperId() {
        try {
            dao.getSightingsBySuperId(-1);
            fail("Should have hit SightingDaoException");
        } catch (SightingDaoException ex) {
        }
    }

    /**
     * Test of getSightingsByLocId method, of class SightingDBImpl.
     */
    @Test
    public void testGetSightingsByLocIdGoldenPath() throws SightingDaoException {
        List<Sighting> forLoc = dao.getSightingsByLocId(3);
        assertEquals(1, forLoc.size());
        Sighting toTest = forLoc.get(0);
        assertEquals(1, toTest.getId());
        assertEquals("2020-10-20", toTest.getDate().toString());
        assertEquals(1, toTest.getSpottedSuper().getId());
        assertEquals("First Hero", toTest.getSpottedSuper().getName());
        assertEquals("First Desc", toTest.getSpottedSuper().getDescription());
        assertEquals(3, toTest.getLocation().getId());
        assertEquals("Third Loc", toTest.getLocation().getName());
        assertEquals("Third Desc", toTest.getLocation().getDescription());
        assertEquals("Third Address", toTest.getLocation().getAddress());
        assertEquals(new BigDecimal("-45.00000"), toTest.getLocation().getLat());
        assertEquals(new BigDecimal("-90.00000"), toTest.getLocation().getLon());
    }
    
    @Test
    public void testGetSightingsByBadLocId() {
        try {
            dao.getSightingsByLocId(-1);
            fail("Should have hit SightingDaoException");
        } catch (SightingDaoException ex) {
        }
    }

    /**
     * Test of getSightingsByDate method, of class SightingDBImpl.
     */
    @Test
    public void testGetSightingsByDateGoldenPath() throws SightingDaoException {
        Sighting toFind = dao.getSightingById(1);
        LocalDate toSearch = toFind.getDate();
        List<Sighting> forDate = dao.getSightingsByDate(toSearch);
        assertEquals(1, forDate.size());
        Sighting toTest = forDate.get(0);
        assertEquals(1, toTest.getId());
        assertEquals("2020-10-20", toTest.getDate().toString());
        assertEquals(1, toTest.getSpottedSuper().getId());
        assertEquals("First Hero", toTest.getSpottedSuper().getName());
        assertEquals("First Desc", toTest.getSpottedSuper().getDescription());
        assertEquals(3, toTest.getLocation().getId());
        assertEquals("Third Loc", toTest.getLocation().getName());
        assertEquals("Third Desc", toTest.getLocation().getDescription());
        assertEquals("Third Address", toTest.getLocation().getAddress());
        assertEquals(new BigDecimal("-45.00000"), toTest.getLocation().getLat());
        assertEquals(new BigDecimal("-90.00000"), toTest.getLocation().getLon());
    }
    
    public void testGetSightingsByNullDate() {
        try {
            dao.getSightingsByDate(null);
            fail("Should have hit SightingDaoException");
        } catch (SightingDaoException ex) {
        }
        
    }

    /**
     * Test of getAllSightings method, of class SightingDBImpl.
     */
    @Test
    public void testGetAllSightingsGoldenPath() throws SightingDaoException {
        List<Sighting> allSightings = dao.getAllSightings();
        assertEquals(3, allSightings.size());
        Sighting first = allSightings.get(0);
        assertEquals(1, first.getId());
        assertEquals("2020-10-20", first.getDate().toString());
        assertEquals(1, first.getSpottedSuper().getId());
        assertEquals("First Hero", first.getSpottedSuper().getName());
        assertEquals("First Desc", first.getSpottedSuper().getDescription());
        assertEquals(3, first.getLocation().getId());
        assertEquals("Third Loc", first.getLocation().getName());
        assertEquals("Third Desc", first.getLocation().getDescription());
        assertEquals("Third Address", first.getLocation().getAddress());
        assertEquals(new BigDecimal("-45.00000"), first.getLocation().getLat());
        assertEquals(new BigDecimal("-90.00000"), first.getLocation().getLon());
        Sighting last = allSightings.get(allSightings.size() - 1);
        assertEquals(3, last.getId());
        assertEquals("2020-10-15", last.getDate().toString());
        assertEquals(3, last.getSpottedSuper().getId());
        assertEquals("Third Hero", last.getSpottedSuper().getName());
        assertEquals("Third Desc", last.getSpottedSuper().getDescription());
        assertEquals(1, last.getLocation().getId());
        assertEquals("First Loc", last.getLocation().getName());
        assertEquals("First Desc", last.getLocation().getDescription());
        assertEquals("First Address", last.getLocation().getAddress());
        assertEquals(new BigDecimal("90.00000"), last.getLocation().getLat());
        assertEquals(new BigDecimal("180.00000"), last.getLocation().getLon());

    }
    
    @Test
    public void testGetAllSightingsEmptyReturn() {
        try {
            template.update("DELETE FROM Sightings");
            dao.getAllSightings();
            fail("Should have hit SightingDaoException");
        } catch (SightingDaoException ex) {
        }
        

    }

    /**
     * Test of addSighting method, of class SightingDBImpl.
     */
    @Test
    public void testAddSightingGoldenPath() throws SightingDaoException, BadUpdateException, InvalidEntityException {
        Sighting toAdd = dao.getSightingById(1);
        toAdd.setDate(LocalDate.of(2020, 12, 13));
        Sighting returned = dao.addSighting(toAdd);
        assertEquals(4, returned.getId());
        assertEquals("2020-12-13", returned.getDate().toString());
        assertEquals(1, returned.getSpottedSuper().getId());
        assertEquals("First Hero", returned.getSpottedSuper().getName());
        assertEquals("First Desc", returned.getSpottedSuper().getDescription());
        assertEquals(3, returned.getLocation().getId());
        assertEquals("Third Loc", returned.getLocation().getName());
        assertEquals("Third Desc", returned.getLocation().getDescription());
        assertEquals("Third Address", returned.getLocation().getAddress());
        assertEquals(new BigDecimal("-45.00000"), returned.getLocation().getLat());
        assertEquals(new BigDecimal("-90.00000"), returned.getLocation().getLon());
    }
    
    @Test
    public void testAddSightingNullDate() throws SightingDaoException, BadUpdateException {
        try {
            Sighting toAdd = dao.getSightingById(1);
            toAdd.setDate(null);
            dao.addSighting(toAdd);
            fail("Should have hit SightingDaoException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testAddSightingNullSuper() throws SightingDaoException, BadUpdateException {
        try {
            Sighting toAdd = dao.getSightingById(1);
            toAdd.setSpottedSuper(null);
            dao.addSighting(toAdd);
            fail("Should have hit SightingDaoException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testAddSightingNullLoc() throws SightingDaoException, BadUpdateException {
        try {
            Sighting toAdd = dao.getSightingById(1);
            toAdd.setLocation(null);
            dao.addSighting(toAdd);
            fail("Should have hit SightingDaoException");
        } catch (InvalidEntityException ex) {
        }
    }

    /**
     * Test of updateSighting method, of class SightingDBImpl.
     */
    @Test
    public void testUpdateSightingGoldenPath() throws SightingDaoException, BadUpdateException, InvalidEntityException {
        Sighting preEdit = dao.getSightingById(1);
        preEdit.setDate(LocalDate.of(2020, 12, 13));
        preEdit.getLocation().setId(1);
        preEdit.getSpottedSuper().setId(3);
        dao.updateSighting(preEdit);
        Sighting postEdit = dao.getSightingById(1);
        assertEquals(1, postEdit.getId());
        assertEquals("2020-12-13", postEdit.getDate().toString());
        assertEquals(3, postEdit.getSpottedSuper().getId());
        assertEquals("Third Hero", postEdit.getSpottedSuper().getName());
        assertEquals("Third Desc", postEdit.getSpottedSuper().getDescription());
        assertEquals(1, postEdit.getLocation().getId());
        assertEquals("First Loc", postEdit.getLocation().getName());
        assertEquals("First Desc", postEdit.getLocation().getDescription());
        assertEquals("First Address", postEdit.getLocation().getAddress());
        assertEquals(new BigDecimal("90.00000"), postEdit.getLocation().getLat());
        assertEquals(new BigDecimal("180.00000"), postEdit.getLocation().getLon());
    }
    
    @Test
    public void testUpdateSightingNullDate() throws SightingDaoException, BadUpdateException {
        try {
            Sighting toEdit = dao.getSightingById(1);
            toEdit.setDate(null);
            dao.updateSighting(toEdit);
            fail("Should have hit SightingDaoException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testUpdateSightingNullSuper() throws SightingDaoException, BadUpdateException {
        try {
            Sighting toEdit = dao.getSightingById(1);
            toEdit.setSpottedSuper(null);
            dao.updateSighting(toEdit);
            fail("Should have hit SightingDaoException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testUpdateSightingNullLoc() throws SightingDaoException, BadUpdateException {
        try {
            Sighting toEdit = dao.getSightingById(1);
            toEdit.setLocation(null);
            dao.updateSighting(toEdit);
            fail("Should have hit SightingDaoException");
        } catch (InvalidEntityException ex) {
        }
    }

    /**
     * Test of removeSighting method, of class SightingDBImpl.
     */
    @Test
    public void testRemoveSighting() throws SightingDaoException, BadUpdateException {
        List<Sighting> preRemove = dao.getAllSightings();
        assertEquals(3, preRemove.size());
        dao.removeSighting(2);
        List<Sighting> postRemove = dao.getAllSightings();
        assertEquals(2, postRemove.size());
        Sighting first = postRemove.get(0);
        assertEquals(1, first.getId());
        assertEquals("2020-10-20", first.getDate().toString());
        assertEquals(1, first.getSpottedSuper().getId());
        assertEquals("First Hero", first.getSpottedSuper().getName());
        assertEquals("First Desc", first.getSpottedSuper().getDescription());
        assertEquals(3, first.getLocation().getId());
        assertEquals("Third Loc", first.getLocation().getName());
        assertEquals("Third Desc", first.getLocation().getDescription());
        assertEquals("Third Address", first.getLocation().getAddress());
        assertEquals(new BigDecimal("-45.00000"), first.getLocation().getLat());
        assertEquals(new BigDecimal("-90.00000"), first.getLocation().getLon());
        Sighting last = postRemove.get(postRemove.size() - 1);
        assertEquals(3, last.getId());
        assertEquals("2020-10-15", last.getDate().toString());
        assertEquals(3, last.getSpottedSuper().getId());
        assertEquals("Third Hero", last.getSpottedSuper().getName());
        assertEquals("Third Desc", last.getSpottedSuper().getDescription());
        assertEquals(1, last.getLocation().getId());
        assertEquals("First Loc", last.getLocation().getName());
        assertEquals("First Desc", last.getLocation().getDescription());
        assertEquals("First Address", last.getLocation().getAddress());
        assertEquals(new BigDecimal("90.00000"), last.getLocation().getLat());
        assertEquals(new BigDecimal("180.00000"), last.getLocation().getLon());

    }
    
    @Test
    public void testRemoveSightingBadId() throws SightingDaoException {
        try {
            dao.removeSighting(-1);
            fail("Should have hit BadUpdateException");
        } catch (BadUpdateException ex) {
        }
        

    }

}
