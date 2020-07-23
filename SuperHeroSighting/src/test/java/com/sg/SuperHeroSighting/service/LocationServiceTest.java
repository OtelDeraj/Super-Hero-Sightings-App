/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.service;

import com.sg.SuperHeroSighting.TestAppConfig;
import com.sg.SuperHeroSighting.inMem.LocationDaoInMem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
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
    public void testGetLocationById() throws Exception {
    }

    /**
     * Test of getLocationByName method, of class LocationService.
     */
    @Test
    public void testGetLocationByName() throws Exception {
    }

    /**
     * Test of getLocationByAddress method, of class LocationService.
     */
    @Test
    public void testGetLocationByAddress() throws Exception {
    }

    /**
     * Test of getLocationByCoord method, of class LocationService.
     */
    @Test
    public void testGetLocationByCoord() throws Exception {
    }

    /**
     * Test of getAllLocations method, of class LocationService.
     */
    @Test
    public void testGetAllLocations() throws Exception {
    }

    /**
     * Test of createLocation method, of class LocationService.
     */
    @Test
    public void testCreateLocation() throws Exception {
    }

    /**
     * Test of editLocation method, of class LocationService.
     */
    @Test
    public void testEditLocation() throws Exception {
    }

    /**
     * Test of removeLocation method, of class LocationService.
     */
    @Test
    public void testRemoveLocation() throws Exception {
    }
    
}
