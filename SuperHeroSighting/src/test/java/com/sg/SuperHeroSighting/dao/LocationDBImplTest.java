/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.dao;

import com.sg.SuperHeroSighting.TestAppConfig;
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
public class LocationDBImplTest {
    
    @Autowired
    LocationDao dao;
    
    @Autowired
    JdbcTemplate template;
    
    public LocationDBImplTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        template.update("DELETE FROM Sightings");
        template.update("DELETE FROM Locations");
        template.update("ALTER TABLE Locations auto_increment = 1");
        template.update("INSERT INTO Locations(name, description, address, lat, lon) VALUES"
                + "('First Name', 'First Desc', 'First Address', 90.00000, 180.00000),"
                + "('Second Name', 'Second Desc', 'Second Address', 45.00000, 90.00000),"
                + "('Third Name', 'Third Desc', 'Third Address', -45.00000, -90.00000)");
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getLocationById method, of class LocationDBImpl.
     */
    @Test
    public void testGetLocationById() {
        
    }

    /**
     * Test of getLocationByName method, of class LocationDBImpl.
     */
    @Test
    public void testGetLocationByName() {
    }

    /**
     * Test of getLocationByAddress method, of class LocationDBImpl.
     */
    @Test
    public void testGetLocationByAddress() {
    }

    /**
     * Test of getLocationByCoord method, of class LocationDBImpl.
     */
    @Test
    public void testGetLocationByCoord() {
    }

    /**
     * Test of getAllLocations method, of class LocationDBImpl.
     */
    @Test
    public void testGetAllLocations() throws Exception {
    }

    /**
     * Test of createLocation method, of class LocationDBImpl.
     */
    @Test
    public void testCreateLocation() throws Exception {
    }

    /**
     * Test of editLocation method, of class LocationDBImpl.
     */
    @Test
    public void testEditLocation() throws Exception {
    }

    /**
     * Test of removeLocation method, of class LocationDBImpl.
     */
    @Test
    public void testRemoveLocation() throws Exception {
    }
    
}
