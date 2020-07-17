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
public class OrgDBImplTest {
    
    @Autowired
    OrgDao dao;
    
    @Autowired
    JdbcTemplate template;
    
    public OrgDBImplTest() {
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
     * Test of getOrgById method, of class OrgDBImpl.
     */
    @Test
    public void testGetOrgById() {
    }

    /**
     * Test of getOrgByName method, of class OrgDBImpl.
     */
    @Test
    public void testGetOrgByName() {
    }

    /**
     * Test of getAllOrgs method, of class OrgDBImpl.
     */
    @Test
    public void testGetAllOrgs() throws Exception {
    }

    /**
     * Test of getOrgsForSuperId method, of class OrgDBImpl.
     */
    @Test
    public void testGetOrgsForSuperId() throws Exception {
    }

    /**
     * Test of createOrg method, of class OrgDBImpl.
     */
    @Test
    public void testCreateOrg() throws Exception {
    }

    /**
     * Test of editOrg method, of class OrgDBImpl.
     */
    @Test
    public void testEditOrg() throws Exception {
    }

    /**
     * Test of removeOrg method, of class OrgDBImpl.
     */
    @Test
    public void testRemoveOrg() throws Exception {
    }
    
}
