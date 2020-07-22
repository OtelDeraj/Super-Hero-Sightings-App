/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.service;

import com.sg.SuperHeroSighting.TestAppConfig;
import com.sg.SuperHeroSighting.inMem.SightingDaoInMem;
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
public class SightingServiceTest {
    
    @Autowired
    SightingService service;
    
    @Autowired
    SightingDaoInMem dao;
    
    public SightingServiceTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of addSighting method, of class SightingService.
     */
    @Test
    public void testAddSighting() throws Exception {
    }

    /**
     * Test of getSightingById method, of class SightingService.
     */
    @Test
    public void testGetSightingById() throws Exception {
    }

    /**
     * Test of getAllSightings method, of class SightingService.
     */
    @Test
    public void testGetAllSightings() throws Exception {
    }

    /**
     * Test of getLastTenSightings method, of class SightingService.
     */
    @Test
    public void testGetLastTenSightings() throws Exception {
    }

    /**
     * Test of getSightingsBySuper method, of class SightingService.
     */
    @Test
    public void testGetSightingsBySuper() throws Exception {
    }

    /**
     * Test of getSightingsByLoc method, of class SightingService.
     */
    @Test
    public void testGetSightingsByLoc() throws Exception {
    }

    /**
     * Test of getSightingsByDate method, of class SightingService.
     */
    @Test
    public void testGetSightingsByDate() throws Exception {
    }

    /**
     * Test of updateSighting method, of class SightingService.
     */
    @Test
    public void testUpdateSighting() throws Exception {
    }

    /**
     * Test of removeSighting method, of class SightingService.
     */
    @Test
    public void testRemoveSighting() throws Exception {
    }
    
}
