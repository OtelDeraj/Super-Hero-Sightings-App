/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.dao;

import com.sg.SuperHeroSighting.TestAppConfig;
import com.sg.SuperHeroSighting.dto.Power;
import com.sg.SuperHeroSighting.exceptions.BadUpdateException;
import com.sg.SuperHeroSighting.exceptions.DuplicateNameException;
import com.sg.SuperHeroSighting.exceptions.InvalidEntityException;
import com.sg.SuperHeroSighting.exceptions.PowerDaoException;
import java.util.List;
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
    
    @BeforeEach
    public void setUp() {
        template.update("DELETE FROM Sightings");
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

    /**
     * Test of getPowerById method, of class PowerDBImpl.
     */
    @Test
    public void testGetPowerById() throws PowerDaoException {
        Power toTest = dao.getPowerById(1);
        assertEquals(1, toTest.getId());
        assertEquals("First Power", toTest.getName());
    }

    /**
     * Test of getPowerByName method, of class PowerDBImpl.
     */
    @Test
    public void testGetPowerByName() throws PowerDaoException {
        Power toTest = dao.getPowerByName("Second Power");
        assertEquals(2, toTest.getId());
        assertEquals("Second Power", toTest.getName());
    }

    /**
     * Test of getAllPowers method, of class PowerDBImpl.
     */
    @Test
    public void testGetAllPowers() throws PowerDaoException {
        List<Power> allPowers = dao.getAllPowers();
        assertEquals(3, allPowers.size());
        Power first = allPowers.get(0);
        assertEquals(1, first.getId());
        assertEquals("First Power", first.getName());
        Power last = allPowers.get(allPowers.size() - 1);
        assertEquals(3, last.getId());
        assertEquals("Third Power", last.getName());
    }

    /**
     * Test of getPowersForSuperId method, of class PowerDBImpl.
     */
    @Test
    public void testGetPowersForSuperId() throws PowerDaoException {
        List<Power> forSuper = dao.getPowersForSuperId(1);
        assertEquals(2, forSuper.size());
        Power first = forSuper.get(0);
        assertEquals(1, first.getId());
        assertEquals("First Power", first.getName());
        Power last = forSuper.get(forSuper.size() - 1);
        assertEquals(2, last.getId());
        assertEquals("Second Power", last.getName());
    }

    /**
     * Test of addPower method, of class PowerDBImpl.
     */
    @Test
    public void testAddPower() throws PowerDaoException, BadUpdateException, InvalidEntityException, DuplicateNameException {
        Power toAdd = new Power("Fourth Power");
        Power returned = dao.addPower(toAdd);
        assertEquals(4, returned.getId());
        assertEquals("Fourth Power", returned.getName());
    }

    /**
     * Test of editPower method, of class PowerDBImpl.
     */
    @Test
    public void testEditPower() throws PowerDaoException, BadUpdateException, InvalidEntityException, DuplicateNameException {
        Power toEdit = dao.getPowerById(1);
        toEdit.setName("First Power PE");
        dao.editPower(toEdit);
        Power postEdit = dao.getPowerById(1);
        assertEquals(1, postEdit.getId());
        assertEquals("First Power PE", postEdit.getName());
    }

    /**
     * Test of removePower method, of class PowerDBImpl.
     */
    @Test
    public void testRemovePower() throws PowerDaoException, BadUpdateException {
        List<Power> allPowers = dao.getAllPowers();
        assertEquals(3, allPowers.size());
        dao.removePower(2);
        List<Power> postRemove = dao.getAllPowers();
        assertEquals(2, postRemove.size());
        Power first = postRemove.get(0);
        assertEquals(1, first.getId());
        assertEquals("First Power", first.getName());
        Power last = postRemove.get(postRemove.size() - 1);
        assertEquals(3, last.getId());
        assertEquals("Third Power", last.getName());
    }
    
}
