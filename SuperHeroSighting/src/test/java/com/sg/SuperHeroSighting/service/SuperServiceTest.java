/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.service;

import com.sg.SuperHeroSighting.TestAppConfig;
import com.sg.SuperHeroSighting.inMem.SuperDaoInMem;
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
public class SuperServiceTest {
    
    @Autowired
    SuperService service;
    
    @Autowired
    SuperDaoInMem dao;
    
    public SuperServiceTest() {
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
     * Test of getSuperById method, of class SuperService.
     */
    @Test
    public void testGetSuperById() throws Exception {
    }

    /**
     * Test of getSuperByName method, of class SuperService.
     */
    @Test
    public void testGetSuperByName() throws Exception {
    }

    /**
     * Test of getAllSupers method, of class SuperService.
     */
    @Test
    public void testGetAllSupers() throws Exception {
    }

    /**
     * Test of getSupersByPowerId method, of class SuperService.
     */
    @Test
    public void testGetSupersByPowerId() throws Exception {
    }

    /**
     * Test of getSupersByOrgId method, of class SuperService.
     */
    @Test
    public void testGetSupersByOrgId() throws Exception {
    }

    /**
     * Test of createSuper method, of class SuperService.
     */
    @Test
    public void testCreateSuper() throws Exception {
    }

    /**
     * Test of editSuper method, of class SuperService.
     */
    @Test
    public void testEditSuper() throws Exception {
    }

    /**
     * Test of removeSuper method, of class SuperService.
     */
    @Test
    public void testRemoveSuper() throws Exception {
    }
    
}
