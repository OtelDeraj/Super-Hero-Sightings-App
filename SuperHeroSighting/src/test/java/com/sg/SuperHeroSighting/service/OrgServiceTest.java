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
    
    @BeforeEach
    public void setUp() {
        dao.setUp();
    }

    /**
     * Test of getOrgById method, of class OrgService.
     */
    @Test
    public void testGetOrgById() {
    }

    /**
     * Test of getOrgByName method, of class OrgService.
     */
    @Test
    public void testGetOrgByName() {
    }

    /**
     * Test of getAllOrgs method, of class OrgService.
     */
    @Test
    public void testGetAllOrgs() {
    }

    /**
     * Test of getOrgsForSuperId method, of class OrgService.
     */
    @Test
    public void testGetOrgsForSuperId() {
    }

    /**
     * Test of addNewOrg method, of class OrgService.
     */
    @Test
    public void testAddNewOrg() {
    }

    /**
     * Test of editOrg method, of class OrgService.
     */
    @Test
    public void testEditOrg() {
    }

    /**
     * Test of removeOrg method, of class OrgService.
     */
    @Test
    public void testRemoveOrg() {
    }
    
}
