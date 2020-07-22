/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.service;

import com.sg.SuperHeroSighting.TestAppConfig;
import com.sg.SuperHeroSighting.inMem.OrgDaoInMem;
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
public class OrgServiceTest {
    
    @Autowired
    OrgService service;
    
    @Autowired
    OrgDaoInMem dao;
    
    public OrgServiceTest() {
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
     * Test of getOrgById method, of class OrgService.
     */
    @Test
    public void testGetOrgById() throws Exception {
    }

    /**
     * Test of getOrgByName method, of class OrgService.
     */
    @Test
    public void testGetOrgByName() throws Exception {
    }

    /**
     * Test of getAllOrgs method, of class OrgService.
     */
    @Test
    public void testGetAllOrgs() throws Exception {
    }

    /**
     * Test of getOrgsForSuperId method, of class OrgService.
     */
    @Test
    public void testGetOrgsForSuperId() throws Exception {
    }

    /**
     * Test of addNewOrg method, of class OrgService.
     */
    @Test
    public void testAddNewOrg() throws Exception {
    }

    /**
     * Test of editOrg method, of class OrgService.
     */
    @Test
    public void testEditOrg() throws Exception {
    }

    /**
     * Test of removeOrg method, of class OrgService.
     */
    @Test
    public void testRemoveOrg() throws Exception {
    }
    
}
