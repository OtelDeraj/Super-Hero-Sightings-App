/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.dao;

import com.sg.SuperHeroSighting.TestAppConfig;
import com.sg.SuperHeroSighting.dto.Sighting;
import com.sg.SuperHeroSighting.exceptions.SightingDaoException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
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
    public void testGetSightingById() throws SightingDaoException {
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

    /**
     * Test of getSightingsBySuperId method, of class SightingDBImpl.
     */
    @Test
    public void testGetSightingsBySuperId() throws SightingDaoException {
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

    /**
     * Test of getSightingsByLocId method, of class SightingDBImpl.
     */
    @Test
    public void testGetSightingsByLocId() throws SightingDaoException {
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

    /**
     * Test of getSightingsByDate method, of class SightingDBImpl.
     */
    @Test
    public void testGetSightingsByDate() throws SightingDaoException {
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

    /**
     * Test of getAllSightings method, of class SightingDBImpl.
     */
    @Test
    public void testGetAllSightings() throws SightingDaoException {
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

    /**
     * Test of addSighting method, of class SightingDBImpl.
     */
    @Test
    public void testAddSighting() {
    }

    /**
     * Test of updateSighting method, of class SightingDBImpl.
     */
    @Test
    public void testUpdateSighting() {
    }

    /**
     * Test of removeSighting method, of class SightingDBImpl.
     */
    @Test
    public void testRemoveSighting() {
    }
    
}
