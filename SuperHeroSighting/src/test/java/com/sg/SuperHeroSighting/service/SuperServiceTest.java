/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.service;

import com.sg.SuperHeroSighting.TestAppConfig;
import com.sg.SuperHeroSighting.dto.Org;
import com.sg.SuperHeroSighting.dto.Power;
import com.sg.SuperHeroSighting.dto.Super;
import com.sg.SuperHeroSighting.exceptions.DuplicateNameException;
import com.sg.SuperHeroSighting.exceptions.EmptyResultException;
import com.sg.SuperHeroSighting.exceptions.InvalidEntityException;
import com.sg.SuperHeroSighting.exceptions.InvalidIdException;
import com.sg.SuperHeroSighting.exceptions.InvalidNameException;
import com.sg.SuperHeroSighting.exceptions.SuperDaoException;
import com.sg.SuperHeroSighting.inMem.SuperDaoInMem;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
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
    SuperService serv;

    @Autowired
    SuperDaoInMem dao;

    @BeforeEach
    public void setUp() {
        dao.setUp();
    }

    /**
     * Test of getSuperById method, of class SuperService.
     */
    @Test
    public void testGetSuperById() throws InvalidIdException {
        Super toCheck = serv.getSuperById(1);
        assertEquals(1, toCheck.getId());
        assertEquals("First Hero", toCheck.getName());
        assertEquals("First Desc", toCheck.getDescription());
        assertEquals(1, toCheck.getPowers().size());
        assertEquals(1, toCheck.getOrgs().size());
    }

    @Test
    public void testGetSuperByBadId() {
        try {
            serv.getSuperById(-1);
            fail("Should have hit InvalidIdException");
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of getSuperByName method, of class SuperService.
     */
    @Test
    public void testGetSuperByName() throws InvalidNameException {
        Super toCheck = serv.getSuperByName("Second Hero");
        assertEquals(2, toCheck.getId());
        assertEquals("Second Hero", toCheck.getName());
        assertEquals("Second Desc", toCheck.getDescription());
        assertEquals(2, toCheck.getPowers().size());
        assertEquals(2, toCheck.getOrgs().size());
    }

    @Test
    public void testGetSuperByBadName() {
        try {
            serv.getSuperByName("Fake Name");
            fail("Should have hit InvalidNameException");
        } catch (InvalidNameException ex) {
        }
    }

    /**
     * Test of getAllSupers method, of class SuperService.
     */
    @Test
    public void testGetAllSupers() throws EmptyResultException {
        List<Super> allSupers = serv.getAllSupers();
        assertEquals(3, allSupers.size());
        Super first = allSupers.get(0);
        assertEquals(1, first.getId());
        assertEquals("First Hero", first.getName());
        assertEquals("First Desc", first.getDescription());
        assertEquals(1, first.getPowers().size());
        assertEquals(1, first.getOrgs().size());
        Super last = allSupers.get(allSupers.size() - 1);
        assertEquals(3, last.getId());
        assertEquals("Third Hero", last.getName());
        assertEquals("Third Desc", last.getDescription());
        assertEquals(1, last.getPowers().size());
        assertEquals(2, last.getOrgs().size());
    }

    @Test
    public void testGetAllSupersEmptyResult() {
        try {
            dao.clearSupers();
            serv.getAllSupers();
            fail("Should have hit EmptyResultException");
        } catch (EmptyResultException ex) {
        }
    }

    /**
     * Test of getSupersByPowerId method, of class SuperService.
     */
    @Test
    public void testGetSupersByPowerId() throws InvalidIdException {
        List<Super> supByPower = serv.getSupersByPowerId(1);
        assertEquals(2, supByPower.size());
        Super first = supByPower.get(0);
        assertEquals(1, first.getId());
        assertEquals("First Hero", first.getName());
        assertEquals("First Desc", first.getDescription());
        assertEquals(1, first.getPowers().size());
        assertEquals(1, first.getOrgs().size());
        Super last = supByPower.get(supByPower.size() - 1);
        assertEquals(3, last.getId());
        assertEquals("Third Hero", last.getName());
        assertEquals("Third Desc", last.getDescription());
        assertEquals(1, last.getPowers().size());
        assertEquals(2, last.getOrgs().size());
    }

    @Test
    public void testGetSupersByPowerBadId() {
        try {
            serv.getSupersByPowerId(-1);
            fail("Should have hit InvalidIdException");
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of getSupersByOrgId method, of class SuperService.
     */
    @Test
    public void testGetSupersByOrgId() throws InvalidIdException {
        List<Super> supByOrg = serv.getSupersByOrgId(1);
        assertEquals(1, supByOrg.size());
        Super first = supByOrg.get(0);
        assertEquals(1, first.getId());
        assertEquals("First Hero", first.getName());
        assertEquals("First Desc", first.getDescription());
        assertEquals(1, first.getPowers().size());
        assertEquals(1, first.getOrgs().size());
        Super last = supByOrg.get(supByOrg.size() - 1);
        assertEquals(first, last);
    }

    @Test
    public void testGetSupersByBadOrgId() {
        try {
            serv.getSupersByOrgId(-1);
            fail("Should have hit InvalidIdException");
        } catch (InvalidIdException ex) {
        }
    }

    // orgs
//    Org org1 = new Org(1, "First Org", "First Description", "First Address", "111-333-5555"); orgSet1
//    Org org2 = new Org(2, "Second Org", "Second Description", "Second Address", "222-444-6666"); orgSet2
//    Org org3 = new Org(3, "Third Org", "Third Description", "Third Address", "333-555-7777"); orgSet2
    // powers
//    Power power1 = new Power(1, "Flight"); powerSet1
//    Power power2 = new Power(2, "Speed"); powerSet2
//    Power power3 = new Power(3, "Strength"); powerSet2
    // supers
//    Super super1 = new Super(1, "First Hero", "First Desc", powerSet1, orgSet1);
//    Super super2 = new Super(2, "Second Hero", "Second Desc", powerSet2, orgSet2);
//    Super super3 = new Super(3, "Third Hero", "Third Desc", powerSet1, orgSet2);
    /**
     * Test of createSuper method, of class SuperService.
     */
    @Test
    public void testCreateSuper() throws SuperDaoException, InvalidEntityException, DuplicateNameException {
        Super forSets = dao.getSuperById(1);
        Super toAdd = new Super("Fourth Hero", "Fourth Desc", forSets.getPowers(), forSets.getOrgs());
        Super returned = serv.createSuper(toAdd);
        assertEquals(4, returned.getId());
        assertEquals("Fourth Hero", returned.getName());
        assertEquals("Fourth Desc", returned.getDescription());
        assertEquals(forSets.getOrgs(), returned.getOrgs());
        assertEquals(forSets.getPowers(), returned.getPowers());
    }

    @Test
    public void testCreateSuperNullObject() throws DuplicateNameException, SuperDaoException {
        try {
            Super toAdd = null;
            serv.createSuper(toAdd);
            fail("Should have thrown InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testCreateSuperEmptyName() throws DuplicateNameException, SuperDaoException {
        try {
            Super forSets = dao.getSuperById(1);
            Super toAdd = new Super("", "Fourth Desc", forSets.getPowers(), forSets.getOrgs());
            serv.createSuper(toAdd);
            fail("Should have thrown InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testCreateSuperBadName() throws DuplicateNameException, SuperDaoException {
        try {
            Super forSets = dao.getSuperById(1);
            Super toAdd = new Super(createBadName(), "Fourth Desc", forSets.getPowers(), forSets.getOrgs());
            serv.createSuper(toAdd);
            fail("Should have thrown InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testCreateSuperEmptyDesc() throws DuplicateNameException, SuperDaoException {
        try {
            Super forSets = dao.getSuperById(1);
            Super toAdd = new Super("Fourth Hero", null, forSets.getPowers(), forSets.getOrgs());
            serv.createSuper(toAdd);
            fail("Should have thrown InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testCreateSuperBadDesc() throws DuplicateNameException, SuperDaoException {
        try {
            Super forSets = dao.getSuperById(1);
            Super toAdd = new Super("Fourth Hero", createBadDescription(), forSets.getPowers(), forSets.getOrgs());
            serv.createSuper(toAdd);
            fail("Should have thrown InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testCreateSuperEmptyOrgs() throws DuplicateNameException, SuperDaoException {
        try {
            Set<Org> empty = new HashSet<>();
            Super forSets = dao.getSuperById(1);
            Super toAdd = new Super("Fourth Hero", "Fourth Desc", forSets.getPowers(), empty);
            serv.createSuper(toAdd);
            fail("Should have thrown InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testCreateSuperEmptyPowers() throws DuplicateNameException, SuperDaoException {
        try {
            Set<Power> empty = new HashSet<>();
            Super forSets = dao.getSuperById(1);
            Super toAdd = new Super("Fourth Hero", "Fourth Desc", empty, forSets.getOrgs());
            serv.createSuper(toAdd);
            fail("Should have thrown InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    /**
     * Test of editSuper method, of class SuperService.
     */
    @Test
    public void testEditSuper() throws SuperDaoException, InvalidEntityException, DuplicateNameException {
        Super forSets = dao.getSuperById(2);
        Super toEdit = new Super(1, "First Hero PE", "First Desc PE", forSets.getPowers(), forSets.getOrgs());
        serv.editSuper(toEdit);
        Super postEdit = dao.getSuperById(1);
        assertEquals(1, postEdit.getId());
        assertEquals("First Hero PE", postEdit.getName());
        assertEquals("First Desc PE", postEdit.getDescription());
        assertEquals(forSets.getPowers(), postEdit.getPowers());
        assertEquals(forSets.getOrgs(), postEdit.getOrgs());
    }

    @Test
    public void testEditSuperNullObject() throws SuperDaoException, DuplicateNameException {
        try {
            Super toEdit = null;
            serv.editSuper(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testEditSuperNullName() throws SuperDaoException, DuplicateNameException {
        try {
            Super forSets = dao.getSuperById(2);
            Super toEdit = new Super(1, null, "First Desc PE", forSets.getPowers(), forSets.getOrgs());
            serv.editSuper(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testEditSuperBadName() throws SuperDaoException, DuplicateNameException {
        try {
            Super forSets = dao.getSuperById(2);
            Super toEdit = new Super(1, createBadName(), "First Desc PE", forSets.getPowers(), forSets.getOrgs());
            serv.editSuper(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testEditSuperNullDesc() throws SuperDaoException, DuplicateNameException {
        try {
            Super forSets = dao.getSuperById(2);
            Super toEdit = new Super(1, "First Hero PE", null, forSets.getPowers(), forSets.getOrgs());
            serv.editSuper(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testEditSuperBadDesc() throws SuperDaoException, DuplicateNameException {
        try {
            Super forSets = dao.getSuperById(2);
            Super toEdit = new Super(1, "First Hero PE", createBadDescription(), forSets.getPowers(), forSets.getOrgs());
            serv.editSuper(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testEditSuperNullOrgs() throws SuperDaoException, DuplicateNameException {
        try {
            Super forSets = dao.getSuperById(2);
            Super toEdit = new Super(1, "First Hero PE", "First Desc PE", forSets.getPowers(), null);
            serv.editSuper(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testEditSuperNullPowers() throws SuperDaoException, DuplicateNameException {
        try {
            Super forSets = dao.getSuperById(2);
            Super toEdit = new Super(1, "First Hero PE", "First Desc PE", null, forSets.getOrgs());
            serv.editSuper(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    /**
     * Test of removeSuper method, of class SuperService.
     */
    @Test
    public void testRemoveSuper() throws EmptyResultException, InvalidIdException {
        List<Super> preRemove = serv.getAllSupers();
        assertEquals(3, preRemove.size());
        serv.removeSuper(2);
        List<Super> postRemove = serv.getAllSupers();
        Super first = postRemove.get(0);
        assertEquals(1, first.getId());
        assertEquals("First Hero", first.getName());
        assertEquals("First Desc", first.getDescription());
        assertEquals(1, first.getPowers().size());
        assertEquals(1, first.getOrgs().size());
        Super last = postRemove.get(postRemove.size() - 1);
        assertEquals(3, last.getId());
        assertEquals("Third Hero", last.getName());
        assertEquals("Third Desc", last.getDescription());
        assertEquals(1, last.getPowers().size());
        assertEquals(2, last.getOrgs().size());
    }

    @Test
    public void testRemoveSuperBadId() {
        try {
            serv.removeSuper(-1);
            fail("Should have hit InvalidIdException");
        } catch (InvalidIdException ex) {
        }
    }
    
    private String createBadName(){
        char[] chars = new char[31];
        Arrays.fill(chars, 'a');
        return new String(chars);
    }
    
    private String createBadDescription(){
        char[] chars = new char[256];
        Arrays.fill(chars, 'a');
        return new String(chars);
    }
}
