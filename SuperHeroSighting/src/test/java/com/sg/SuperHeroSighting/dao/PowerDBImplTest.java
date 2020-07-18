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
public class PowerDBImplTest {
    
    @Autowired
    PowerDao dao;
    
    @Autowired
    JdbcTemplate template;
    
    public PowerDBImplTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        
        template.update("DELETE FROM Affiliations");
        template.update("DELETE FROM Super_Powers");
        template.update("DELETE FROM Supers");
        template.update("ALTER TABLE Supers auto_increment = 1");
        template.update("INSERT INTO Supers(name, description) VALUES"
                + "('First Hero', 'First Desc'),"
                + "('Second Hero', 'Second Desc'),"
                + "('Third Hero', 'Third Desc')");
        template.update("DELETE FROM Powers");
        template.update("ALTER TABLE Powers auto_increment = 1");
        template.update("INSERT INTO Powers(name) VALUES"
                + "('First Power'),"
                + "('Second Power'),"
                + "('Third Power')");
        template.update("INSERT INTO Super_Powers(superId, powerId) VALUES"
                + "(1, 1),"
                + "(1, 2),"
                + "(2, 2),"
                + "(3, 3)");
        
        
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getPowerById method, of class PowerDBImpl.
     */
    @Test
    public void testGetPowerById() {
    }

    /**
     * Test of getPowerByName method, of class PowerDBImpl.
     */
    @Test
    public void testGetPowerByName() {
    }

    /**
     * Test of getAllPowers method, of class PowerDBImpl.
     */
    @Test
    public void testGetAllPowers() throws Exception {
    }

    /**
     * Test of getPowersForSuperId method, of class PowerDBImpl.
     */
    @Test
    public void testGetPowersForSuperId() throws Exception {
    }

    /**
     * Test of addPower method, of class PowerDBImpl.
     */
    @Test
    public void testAddPower() throws Exception {
    }

    /**
     * Test of editPower method, of class PowerDBImpl.
     */
    @Test
    public void testEditPower() throws Exception {
    }

    /**
     * Test of removePower method, of class PowerDBImpl.
     */
    @Test
    public void testRemovePower() throws Exception {
    }
    
}
