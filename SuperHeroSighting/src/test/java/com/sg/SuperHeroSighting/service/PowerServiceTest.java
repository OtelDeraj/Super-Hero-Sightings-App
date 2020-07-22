/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.service;

import com.sg.SuperHeroSighting.TestAppConfig;
import com.sg.SuperHeroSighting.inMem.PowerDaoInMem;
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
public class PowerServiceTest {
    
    @Autowired
    PowerService service;
    
    @Autowired
    PowerDaoInMem dao;
    
    public PowerServiceTest() {
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
     * Test of getPowerById method, of class PowerService.
     */
    @Test
    public void testGetPowerById() throws Exception {
    }

    /**
     * Test of getPowerByName method, of class PowerService.
     */
    @Test
    public void testGetPowerByName() throws Exception {
    }

    /**
     * Test of getAllPowers method, of class PowerService.
     */
    @Test
    public void testGetAllPowers() throws Exception {
    }

    /**
     * Test of getPowersBySuperId method, of class PowerService.
     */
    @Test
    public void testGetPowersBySuperId() throws Exception {
    }

    /**
     * Test of createPower method, of class PowerService.
     */
    @Test
    public void testCreatePower() throws Exception {
    }

    /**
     * Test of editPower method, of class PowerService.
     */
    @Test
    public void testEditPower() throws Exception {
    }

    /**
     * Test of removePower method, of class PowerService.
     */
    @Test
    public void testRemovePower() throws Exception {
    }
    
}
