/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.service;

import com.sg.SuperHeroSighting.TestAppConfig;
import com.sg.SuperHeroSighting.dto.Org;
import com.sg.SuperHeroSighting.dto.Super;
import com.sg.SuperHeroSighting.exceptions.DuplicateNameException;
import com.sg.SuperHeroSighting.exceptions.EmptyResultException;
import com.sg.SuperHeroSighting.exceptions.InvalidEntityException;
import com.sg.SuperHeroSighting.exceptions.InvalidIdException;
import com.sg.SuperHeroSighting.exceptions.InvalidNameException;
import com.sg.SuperHeroSighting.exceptions.OrgDaoException;
import com.sg.SuperHeroSighting.inMem.OrgDaoInMem;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    public void testGetOrgById() throws InvalidIdException {
        Org toCheck = service.getOrgById(1);
        assertEquals(1, toCheck.getId());
        assertEquals("First Org", toCheck.getName());
        assertEquals("First Desc", toCheck.getDescription());
        assertEquals("First Adr", toCheck.getAddress());
        assertEquals("First Phone", toCheck.getPhone());
        assertEquals(1, toCheck.getSupers().size());
    }
    
    @Test
    public void testGetOrgByBadId() {
        try {
            service.getOrgById(-1);
            fail("Should have hit InvalidIdException");
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of getOrgByName method, of class OrgService.
     */
    @Test
    public void testGetOrgByName() throws InvalidNameException {
        Org toCheck = service.getOrgByName("First Org");
        assertEquals(1, toCheck.getId());
        assertEquals("First Org", toCheck.getName());
        assertEquals("First Desc", toCheck.getDescription());
        assertEquals("First Adr", toCheck.getAddress());
        assertEquals("First Phone", toCheck.getPhone());
        assertEquals(1, toCheck.getSupers().size());
    }
    
    @Test
    public void testGetOrgByBadName() {
        try {
            service.getOrgByName("Fake Org");
            fail("Should have hit InvalidNameException");
        } catch (InvalidNameException ex) {
        }
    }

    /**
     * Test of getAllOrgs method, of class OrgService.
     */
    @Test
    public void testGetAllOrgs() throws EmptyResultException {
        List<Org> allOrgs = service.getAllOrgs();
        assertEquals(3, allOrgs.size());
        Org first = allOrgs.get(0);
        assertEquals(1, first.getId());
        assertEquals("First Org", first.getName());
        assertEquals("First Desc", first.getDescription());
        assertEquals("First Adr", first.getAddress());
        assertEquals("First Phone", first.getPhone());
        assertEquals(1, first.getSupers().size());
        Org last = allOrgs.get(allOrgs.size() - 1);
        assertEquals(3, last.getId());
        assertEquals("Third Org", last.getName());
        assertEquals("Third Desc", last.getDescription());
        assertEquals("Third Adr", last.getAddress());
        assertEquals("Third Phone", last.getPhone());
        assertEquals(0, last.getSupers().size());
    }
    
    @Test
    public void testGetAllOrgsEmptyReturn() {
        try {
            dao.clearOrgs();
            service.getAllOrgs();
            fail("Should have hit EmptyResultException");
        } catch (EmptyResultException ex) {
        }
    }

    /**
     * Test of getOrgsForSuperId method, of class OrgService.
     */
    @Test
    public void testGetOrgsForSuperId() throws InvalidIdException {
        List<Org> forSuper = service.getOrgsForSuperId(1);
        assertEquals(1, forSuper.size());
        Org first = forSuper.get(0);
        assertEquals(3, first.getId());
        assertEquals("Third Org", first.getName());
        assertEquals("Third Desc", first.getDescription());
        assertEquals("Third Adr", first.getAddress());
        assertEquals("Third Phone", first.getPhone());
        assertEquals(0, first.getSupers().size());
        Org last = forSuper.get(forSuper.size() - 1);
        assertEquals(last, first);
    }
    
    @Test
    public void testGetOrgsForBadSuperId() {
        try {
            service.getOrgsForSuperId(-1);
            fail("Should have hit InvalidIdException");
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of addNewOrg method, of class OrgService.
     */
    @Test
    public void testAddNewOrg() throws OrgDaoException, InvalidEntityException, DuplicateNameException {
        Org forSuperSet = dao.getOrgById(1);
        Org toAdd = new Org("Fourth Org", "Fourth Desc", "Fourth Adr", "Fourth Phone", forSuperSet.getSupers());
        Org returned = service.addNewOrg(toAdd);
        assertEquals(4, returned.getId());
        assertEquals("Fourth Org", returned.getName());
        assertEquals("Fourth Desc", returned.getDescription());
        assertEquals("Fourth Adr", returned.getAddress());
        assertEquals("Fourth Phone", returned.getPhone());
        assertEquals(1, returned.getSupers().size());
    }
    
    @Test
    public void testAddNewOrgNullObject() throws OrgDaoException, DuplicateNameException {
        try {
            Org toAdd = null;
            service.addNewOrg(toAdd);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testAddNewOrgDuplicateName() throws InvalidEntityException, OrgDaoException {
        try {
            Org forSuperSet = dao.getOrgById(1);
            Org toAdd = new Org("First Org", "Fourth Desc", "Fourth Adr", "Fourth Phone", forSuperSet.getSupers());
            service.addNewOrg(toAdd);
            fail("Should have hit DuplicateNameException");
        } catch(DuplicateNameException ex){
        }
    }
    
    @Test
    public void testAddNewOrgNullName() throws OrgDaoException, DuplicateNameException {
        try {
            Org forSuperSet = dao.getOrgById(1);
            Org toAdd = new Org(null, "Fourth Desc", "Fourth Adr", "Fourth Phone", forSuperSet.getSupers());
            service.addNewOrg(toAdd);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testAddNewOrgEmptyName() throws OrgDaoException, DuplicateNameException {
        try {
            Org forSuperSet = dao.getOrgById(1);
            Org toAdd = new Org("", "Fourth Desc", "Fourth Adr", "Fourth Phone", forSuperSet.getSupers());
            service.addNewOrg(toAdd);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testAddNewOrgBadName() throws OrgDaoException, DuplicateNameException {
        try {
            Org forSuperSet = dao.getOrgById(1);
            Org toAdd = new Org(createBadName(), "Fourth Desc", "Fourth Adr", "Fourth Phone", forSuperSet.getSupers());
            service.addNewOrg(toAdd);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testAddNewOrgNullDescription() throws OrgDaoException, DuplicateNameException {
        try {
            Org forSuperSet = dao.getOrgById(1);
            Org toAdd = new Org("Fourth Org", null, "Fourth Adr", "Fourth Phone", forSuperSet.getSupers());
            service.addNewOrg(toAdd);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testAddNewOrgEmptyDescription() throws OrgDaoException, DuplicateNameException {
        try {
            Org forSuperSet = dao.getOrgById(1);
            Org toAdd = new Org("Fourth Org", "", "Fourth Adr", "Fourth Phone", forSuperSet.getSupers());
            service.addNewOrg(toAdd);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testAddNewOrgBadDescription() throws OrgDaoException, DuplicateNameException {
        try {
            Org forSuperSet = dao.getOrgById(1);
            Org toAdd = new Org("Fourth Org", createBadDescription(), "Fourth Adr", "Fourth Phone", forSuperSet.getSupers());
            service.addNewOrg(toAdd);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testAddNewOrgNullAddress() throws OrgDaoException, DuplicateNameException {
        try {
            Org forSuperSet = dao.getOrgById(1);
            Org toAdd = new Org("Fourth Org", "Fourth Desc", null, "Fourth Phone", forSuperSet.getSupers());
            service.addNewOrg(toAdd);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testAddNewOrgEmptyAddress() throws OrgDaoException, DuplicateNameException {
        try {
            Org forSuperSet = dao.getOrgById(1);
            Org toAdd = new Org("Fourth Org", "Fourth Desc", "", "Fourth Phone", forSuperSet.getSupers());
            service.addNewOrg(toAdd);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testAddNewOrgBadAddress() throws OrgDaoException, DuplicateNameException {
        try {
            Org forSuperSet = dao.getOrgById(1);
            Org toAdd = new Org("Fourth Org", "Fourth Desc", createBadAddress(), "Fourth Phone", forSuperSet.getSupers());
            service.addNewOrg(toAdd);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testAddNewOrgNullPhone() throws OrgDaoException, DuplicateNameException {
        try {
            Org forSuperSet = dao.getOrgById(1);
            Org toAdd = new Org("Fourth Org", "Fourth Desc", "Fourth Adr", null, forSuperSet.getSupers());
            service.addNewOrg(toAdd);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testAddNewOrgEmptyPhone() throws OrgDaoException, DuplicateNameException {
        try {
            Org forSuperSet = dao.getOrgById(1);
            Org toAdd = new Org("Fourth Org", "Fourth Desc", "Fourth Adr", "", forSuperSet.getSupers());
            service.addNewOrg(toAdd);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testAddNewOrgBadPhone() throws DuplicateNameException, OrgDaoException {
        try {
            Org forSuperSet = dao.getOrgById(1);
            Org toAdd = new Org("Fourth Org", "Fourth Desc", "Fourth Adr", createBadPhone(), forSuperSet.getSupers());
            service.addNewOrg(toAdd);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testAddNewOrgEmptySupers() throws DuplicateNameException {
        try {
            Set<Super> empty = new HashSet<>();
            Org toAdd = new Org("Fourth Org", "Fourth Desc", "Fourth Adr", "Fourth Phone", empty);
            service.addNewOrg(toAdd);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    /**
     * Test of editOrg method, of class OrgService.
     */
    @Test
    public void testEditOrg() throws OrgDaoException, InvalidEntityException, DuplicateNameException {
        Org forSuperSet = dao.getOrgById(1);
        Org toEdit = new Org(1, "First Org PE", "First Desc PE", "First Adr PE", "First Phone PE", forSuperSet.getSupers());
        service.editOrg(toEdit);
        Org postEdit = dao.getOrgById(1);
        assertEquals(1, postEdit.getId());
        assertEquals("First Org PE", postEdit.getName());
        assertEquals("First Desc PE", postEdit.getDescription());
        assertEquals("First Adr PE", postEdit.getAddress());
        assertEquals("First Phone PE", postEdit.getPhone());
        assertEquals(1, postEdit.getSupers().size());
    }
    
    @Test
    public void testEditOrgNullObject() throws OrgDaoException, DuplicateNameException {
        try {
            Org toEdit = null;
            service.editOrg(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditOrgDuplicateName() throws OrgDaoException, InvalidEntityException {
        try {
            Org forSuperSet = dao.getOrgById(1);
            Org toEdit = new Org(1, "Second Org", "First Desc PE", "First Adr PE", "First Phone PE", forSuperSet.getSupers());
            service.editOrg(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (DuplicateNameException ex) {
        }
    }
    
    @Test
    public void testEditOrgNullName() throws OrgDaoException, DuplicateNameException {
        try {
            Org forSuperSet = dao.getOrgById(1);
            Org toEdit = new Org(1, null, "First Desc PE", "First Adr PE", "First Phone PE", forSuperSet.getSupers());
            service.editOrg(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditOrgEmptyName() throws OrgDaoException, DuplicateNameException {
        try {
            Org forSuperSet = dao.getOrgById(1);
            Org toEdit = new Org(1, "", "First Desc PE", "First Adr PE", "First Phone PE", forSuperSet.getSupers());
            service.editOrg(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditOrgBadName() throws OrgDaoException, DuplicateNameException {
        try {
            Org forSuperSet = dao.getOrgById(1);
            Org toEdit = new Org(1, createBadName(), "First Desc PE", "First Adr PE", "First Phone PE", forSuperSet.getSupers());
            service.editOrg(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditOrgNullDesc() throws OrgDaoException, DuplicateNameException {
        try {
            Org forSuperSet = dao.getOrgById(1);
            Org toEdit = new Org(1, "First Org PE", null, "First Adr PE", "First Phone PE", forSuperSet.getSupers());
            service.editOrg(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditOrgEmptyDesc() throws OrgDaoException, DuplicateNameException {
        try {
            Org forSuperSet = dao.getOrgById(1);
            Org toEdit = new Org(1, "First Org PE", "", "First Adr PE", "First Phone PE", forSuperSet.getSupers());
            service.editOrg(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditOrgBadDesc() throws OrgDaoException, DuplicateNameException {
        try {
            Org forSuperSet = dao.getOrgById(1);
            Org toEdit = new Org(1, "First Org PE", createBadDescription(), "First Adr PE", "First Phone PE", forSuperSet.getSupers());
            service.editOrg(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditOrgNullAdr() throws OrgDaoException, DuplicateNameException {
        try {
            Org forSuperSet = dao.getOrgById(1);
            Org toEdit = new Org(1, "First Org PE", "First Desc PE", null, "First Phone PE", forSuperSet.getSupers());
            service.editOrg(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditOrgEmptyAdr() throws OrgDaoException, DuplicateNameException {
        try {
            Org forSuperSet = dao.getOrgById(1);
            Org toEdit = new Org(1, "First Org PE", "First Desc PE", "", "First Phone PE", forSuperSet.getSupers());
            service.editOrg(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditOrgBadAdr() throws OrgDaoException, DuplicateNameException {
        try {
            Org forSuperSet = dao.getOrgById(1);
            Org toEdit = new Org(1, "First Org PE", "First Desc PE", createBadAddress(), "First Phone PE", forSuperSet.getSupers());
            service.editOrg(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditOrgNullPhone() throws OrgDaoException, DuplicateNameException {
        try {
            Org forSuperSet = dao.getOrgById(1);
            Org toEdit = new Org(1, "First Org PE", "First Desc PE", "First Adr PE", null, forSuperSet.getSupers());
            service.editOrg(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditOrgEmptyPhone() throws OrgDaoException, DuplicateNameException {
        try {
            Org forSuperSet = dao.getOrgById(1);
            Org toEdit = new Org(1, "First Org PE", "First Desc PE", "First Adr PE", "", forSuperSet.getSupers());
            service.editOrg(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditOrgBadPhone() throws OrgDaoException, DuplicateNameException {
        try {
            Org forSuperSet = dao.getOrgById(1);
            Org toEdit = new Org(1, "First Org PE", "First Desc PE", "First Adr PE", createBadPhone(), forSuperSet.getSupers());
            service.editOrg(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditOrgEmptySupers() throws OrgDaoException, DuplicateNameException {
        try {
            Set<Super> empty = new HashSet<>();
            Org toEdit = new Org(1, "First Org PE", "First Desc PE", "First Adr PE", "First Phone PE", empty);
            service.editOrg(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    /**
     * Test of removeOrg method, of class OrgService.
     */
    @Test
    public void testRemoveOrg() throws EmptyResultException, InvalidIdException {
        List<Org> preRemove = service.getAllOrgs();
        assertEquals(3, preRemove.size());
        service.removeOrg(2);
        List<Org> postRemove = service.getAllOrgs();
        Org first = postRemove.get(0);
        assertEquals(1, first.getId());
        assertEquals("First Org", first.getName());
        assertEquals("First Desc", first.getDescription());
        assertEquals("First Adr", first.getAddress());
        assertEquals("First Phone", first.getPhone());
        assertEquals(1, first.getSupers().size());
        Org last = postRemove.get(postRemove.size() - 1);
        assertEquals(3, last.getId());
        assertEquals("Third Org", last.getName());
        assertEquals("Third Desc", last.getDescription());
        assertEquals("Third Adr", last.getAddress());
        assertEquals("Third Phone", last.getPhone());
        assertEquals(0, last.getSupers().size());
        
    }
    
    @Test
    public void testRemoveOrgBadId() {
        try {
            service.removeOrg(-1);
            fail("Should have hit InvalidIdException");
        } catch (InvalidIdException ex) {
        }
    }
    
    private String createBadName(){
        char[] chars = new char[51];
        Arrays.fill(chars, 'a');
        return new String(chars);
    }
    
    private String createBadDescription(){
        char[] chars = new char[256];
        Arrays.fill(chars, 'a');
        return new String(chars);
    }
    
    private String createBadAddress(){
        char[] chars = new char[61];
        Arrays.fill(chars, 'a');
        return new String(chars);
    }
    
    private String createBadPhone(){
        char[] chars = new char[16];
        Arrays.fill(chars, 'a');
        return new String(chars);
    }
}
