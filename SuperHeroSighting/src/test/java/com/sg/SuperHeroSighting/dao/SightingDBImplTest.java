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
public class SightingDBImplTest {
    
    @Autowired
    SightingDao dao;
    
    @Autowired
    JdbcTemplate template;
    
    public SightingDBImplTest() {
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
     * Test of getSightingById method, of class SightingDBImpl.
     */
    @Test
    public void testGetSightingById() {
    }

    /**
     * Test of getSightingsBySuperId method, of class SightingDBImpl.
     */
    @Test
    public void testGetSightingsBySuperId() throws Exception {
    }

    /**
     * Test of getSightingsByLocId method, of class SightingDBImpl.
     */
    @Test
    public void testGetSightingsByLocId() throws Exception {
    }

    /**
     * Test of getSightingsByDate method, of class SightingDBImpl.
     */
    @Test
    public void testGetSightingsByDate() throws Exception {
    }

    /**
     * Test of getAllSightings method, of class SightingDBImpl.
     */
    @Test
    public void testGetAllSightings() throws Exception {
    }

    /**
     * Test of addSighting method, of class SightingDBImpl.
     */
    @Test
    public void testAddSighting() throws Exception {
    }

    /**
     * Test of updateSighting method, of class SightingDBImpl.
     */
    @Test
    public void testUpdateSighting() throws Exception {
    }

    /**
     * Test of removeSighting method, of class SightingDBImpl.
     */
    @Test
    public void testRemoveSighting() throws Exception {
    }
    
}
