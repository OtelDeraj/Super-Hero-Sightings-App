/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.service;

import com.sg.SuperHeroSighting.TestAppConfig;
import com.sg.SuperHeroSighting.dto.Power;
import com.sg.SuperHeroSighting.exceptions.DuplicateNameException;
import com.sg.SuperHeroSighting.exceptions.EmptyResultException;
import com.sg.SuperHeroSighting.exceptions.InvalidEntityException;
import com.sg.SuperHeroSighting.exceptions.InvalidIdException;
import com.sg.SuperHeroSighting.exceptions.InvalidNameException;
import com.sg.SuperHeroSighting.exceptions.PowerDaoException;
import com.sg.SuperHeroSighting.inMem.PowerDaoInMem;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
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
    

    @BeforeEach
    public void setUp() {
        dao.setUp();
    }
    
    /**
     * Test of getPowerById method, of class PowerService.
     */
    @Test
    public void testGetPowerById() throws InvalidIdException {
        Power toCheck = service.getPowerById(1);
        assertEquals(1, toCheck.getId());
        assertEquals("Flight", toCheck.getName());
    }
    
    @Test
    public void testGetPowerByBadId() {
        try {
            service.getPowerById(-1);
            fail("Should have hit InvalidIdException");
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of getPowerByName method, of class PowerService.
     */
    @Test
    public void testGetPowerByName() throws InvalidNameException {
        Power toCheck = service.getPowerByName("Flight");
        assertEquals(1, toCheck.getId());
        assertEquals("Flight", toCheck.getName());
    }
    
    @Test
    public void testGetPowerByBadName() {
        try {
            service.getPowerByName("Fake Power Name");
            fail("Should have thrown InvalidNameException");
        } catch (InvalidNameException ex) {
        }
    }

    /**
     * Test of getAllPowers method, of class PowerService.
     */
    @Test
    public void testGetAllPowers() throws EmptyResultException {
        List<Power> allPowers = service.getAllPowers();
        assertEquals(3, allPowers.size());
        Power first = allPowers.get(0);
        assertEquals(1, first.getId());
        assertEquals("Flight", first.getName());
        Power last = allPowers.get(allPowers.size() -1);
        assertEquals(3, last.getId());
        assertEquals("Strength", last.getName());
    }
    
    @Test
    public void testGetAllPowersEmpty() {
        try {
            dao.clearPowers();
            service.getAllPowers();
            fail("Should have thrown EmptyResultException");
        } catch (EmptyResultException ex) {
        }
    }

    /**
     * Test of getPowersBySuperId method, of class PowerService.
     */
    @Test
    public void testGetPowersBySuperId() throws EmptyResultException {
        List<Power> forSup = service.getPowersBySuperId(1);
        assertEquals(2, forSup.size());
        Power first = forSup.get(0);
        assertEquals(1, first.getId());
        assertEquals("Flight", first.getName());
        Power last = forSup.get(forSup.size() -1);
        assertEquals(3, last.getId());
        assertEquals("Strength", last.getName());
    }
    
    @Test
    public void testGetPowersBySuperIdEmpty() {
        try {
            dao.clearPowers();
            service.getPowersBySuperId(1);
            fail("Should have thrown EmptyResultException");
        } catch (EmptyResultException ex) {
        }
    }

    /**
     * Test of createPower method, of class PowerService.
     */
    @Test
    public void testCreatePower() throws InvalidEntityException, DuplicateNameException {
        Power toAdd = new Power("Mind Control");
        Power returned = service.createPower(toAdd);
        assertEquals(4, returned.getId());
        assertEquals("Mind Control", returned.getName());
    }
    
    @Test
    public void testCreatePowerNullObject() throws DuplicateNameException {
        try {
            Power toAdd = null;
            service.createPower(toAdd);
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testCreatePowerNullName() throws DuplicateNameException {
        try {
            Power toAdd = new Power();
            service.createPower(toAdd);
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testCreatePowerBadName() throws DuplicateNameException {
        try {
            Power toAdd = new Power(createBadName());
            service.createPower(toAdd);
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testCreatePowerDuplicateName() throws InvalidEntityException {
        try {
            Power toAdd = new Power("Flight");
            service.createPower(toAdd);
        } catch (DuplicateNameException ex) {
        }
    }

    /**
     * Test of editPower method, of class PowerService.
     */
    @Test
    public void testEditPower() throws InvalidEntityException, DuplicateNameException, PowerDaoException {
        Power toEdit = new Power(1, "Super Flight");
        service.editPower(toEdit);
        Power toCheck = dao.getPowerById(1);
        assertEquals(1, toCheck.getId());
        assertEquals("Super Flight", toCheck.getName());
    }
    
    @Test
    public void testEditPowerNullName() throws DuplicateNameException {
        try {
            Power toEdit = new Power();
            service.editPower(toEdit);
            fail("Should have hit DuplicateNameException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditPowerBadName() throws DuplicateNameException {
        try {
            Power toEdit = new Power(1, createBadName());
            service.editPower(toEdit);
            fail("Should have hit DuplicateNameException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditPowerDuplicateName() throws InvalidEntityException {
        try {
            Power toEdit = new Power(1, "Strength");
            service.editPower(toEdit);
            fail("Should have hit DuplicateNameException");
        } catch (DuplicateNameException ex) {
        }
    }

    /**
     * Test of removePower method, of class PowerService.
     */
    @Test
    public void testRemovePower() {
    }
    
    
    private String createBadName(){
        char[] chars = new char[31];
        Arrays.fill(chars, 'a');
        return new String(chars);
    }
}
