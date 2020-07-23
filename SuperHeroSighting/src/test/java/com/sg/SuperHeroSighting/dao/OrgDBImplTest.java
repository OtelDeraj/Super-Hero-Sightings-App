/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.dao;

import com.sg.SuperHeroSighting.TestAppConfig;
import com.sg.SuperHeroSighting.dto.Org;
import com.sg.SuperHeroSighting.dto.Super;
import com.sg.SuperHeroSighting.exceptions.BadUpdateException;
import com.sg.SuperHeroSighting.exceptions.DuplicateNameException;
import com.sg.SuperHeroSighting.exceptions.InvalidEntityException;
import com.sg.SuperHeroSighting.exceptions.OrgDaoException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
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
    
    @BeforeEach
    public void setUp() {
        template.update("DELETE FROM Sightings");
        template.update("DELETE FROM Affiliations");
        template.update("DELETE FROM Super_Powers");
        template.update("DELETE FROM Orgs");
        template.update("ALTER TABLE Orgs auto_increment = 1");
        template.update("INSERT INTO Orgs(name, description, address, phone) VALUES"
                + "('First Org', 'First OD', 'First Adr', '666-555-4444'),"
                + "('Second Org', 'Second OD', 'Second Adr', '222-111-9999'),"
                + "('Third Org', 'Third OD', 'Third Adr', '444-555-6666')");
        template.update("DELETE FROM Supers");
        template.update("ALTER TABLE Supers auto_increment = 1");
        template.update("INSERT INTO Supers(name, description) VALUES"
                + "('First Hero', 'First HD'),"
                + "('Second Hero', 'Second HD'),"
                + "('Third Hero', 'Third HD')");
        template.update("INSERT INTO Affiliations(orgId, superId) VALUES"
                + "(1, 1),"
                + "(1, 2),"
                + "(2, 1),"
                + "(3, 2)");
                
    }
    
    /**
     * Test of getOrgById method, of class OrgDBImpl.
     */
    @Test
    public void testGetOrgById() throws OrgDaoException {
        Org toTest = dao.getOrgById(1);
        assertEquals(1, toTest.getId());
        assertEquals("First Org", toTest.getName());
        assertEquals("First OD", toTest.getDescription());
        assertEquals("First Adr", toTest.getAddress());
        assertEquals("666-555-4444", toTest.getPhone());
        assertEquals(2, toTest.getSupers().size());
    }

    /**
     * Test of getOrgByName method, of class OrgDBImpl.
     */
    @Test
    public void testGetOrgByName() throws OrgDaoException {
        Org toTest = dao.getOrgByName("First Org");
        assertEquals(1, toTest.getId());
        assertEquals("First Org", toTest.getName());
        assertEquals("First OD", toTest.getDescription());
        assertEquals("First Adr", toTest.getAddress());
        assertEquals("666-555-4444", toTest.getPhone());
        assertEquals(2, toTest.getSupers().size());
    }

    /**
     * Test of getAllOrgs method, of class OrgDBImpl.
     */
    @Test
    public void testGetAllOrgs() throws OrgDaoException {
        List<Org> allOrgs = dao.getAllOrgs();
        assertEquals(3, allOrgs.size());
        Org first = allOrgs.get(0);
        assertEquals(1, first.getId());
        assertEquals("First Org", first.getName());
        assertEquals("First OD", first.getDescription());
        assertEquals("First Adr", first.getAddress());
        assertEquals("666-555-4444", first.getPhone());
        assertEquals(2, first.getSupers().size());
        Org last = allOrgs.get(allOrgs.size() - 1);
        assertEquals(3, last.getId());
        assertEquals("Third Org", last.getName());
        assertEquals("Third OD", last.getDescription());
        assertEquals("Third Adr", last.getAddress());
        assertEquals("444-555-6666", last.getPhone());
        assertEquals(1, last.getSupers().size());
    }

    /**
     * Test of getOrgsForSuperId method, of class OrgDBImpl.
     */
    @Test
    public void testGetOrgsForSuperId() throws OrgDaoException {
        List<Org> forSuper = dao.getOrgsForSuperId(2);
        assertEquals(2, forSuper.size());
        Org first = forSuper.get(0);
        assertEquals(1, first.getId());
        assertEquals("First Org", first.getName());
        assertEquals("First OD", first.getDescription());
        assertEquals("First Adr", first.getAddress());
        assertEquals("666-555-4444", first.getPhone());
        assertEquals(2, first.getSupers().size());
        Org last = forSuper.get(forSuper.size() - 1);
        assertEquals(3, last.getId());
        assertEquals("Third Org", last.getName());
        assertEquals("Third OD", last.getDescription());
        assertEquals("Third Adr", last.getAddress());
        assertEquals("444-555-6666", last.getPhone());
        assertEquals(1, last.getSupers().size());
    }

    /**
     * Test of createOrg method, of class OrgDBImpl.
     */
    @Test
    public void testCreateOrgGoldenPath() throws OrgDaoException, BadUpdateException, InvalidEntityException, DuplicateNameException {
        Super sup1 = new Super(1, "First Hero", "First HD");
        Set<Super> superSet = new HashSet<>();
        superSet.add(sup1);
        Org toCreate = new Org("Fourth Org", "Fourth OD", "Fourth Adr", "888-111-9999", superSet);
        Org returned = dao.createOrg(toCreate);
        assertEquals(4, returned.getId());
        assertEquals(toCreate.getName(), returned.getName());
        assertEquals(toCreate.getDescription(), returned.getDescription());
        assertEquals(toCreate.getAddress(), returned.getAddress());
        assertEquals(toCreate.getPhone(), returned.getPhone());
        assertEquals(toCreate.getSupers(), toCreate.getSupers());
    }
    
    @Test
    public void testCreateOrgNullName() throws OrgDaoException, BadUpdateException, DuplicateNameException {
        try {
            Super sup1 = new Super(1, "First Hero", "First HD");
            Set<Super> superSet = new HashSet<>();
            superSet.add(sup1);
            Org toCreate = new Org(null, "Fourth OD", "Fourth Adr", "888-111-9999", superSet);
            dao.createOrg(toCreate);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testCreateOrgBadName() throws OrgDaoException, BadUpdateException, InvalidEntityException, DuplicateNameException {
        try {
            Super sup1 = new Super(1, "First Hero", "First HD");
            Set<Super> superSet = new HashSet<>();
            superSet.add(sup1);
            Org toCreate = new Org(createBadName(), "Fourth OD", "Fourth Adr", "888-111-9999", superSet);
            dao.createOrg(toCreate);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testCreateOrgDuplicateName() throws OrgDaoException, BadUpdateException, InvalidEntityException, DuplicateNameException {
        try {
            Super sup1 = new Super(1, "First Hero", "First HD");
            Set<Super> superSet = new HashSet<>();
            superSet.add(sup1);
            Org toCreate = new Org("First Org", "Fourth OD", "Fourth Adr", "888-111-9999", superSet);
            dao.createOrg(toCreate);
            fail("Should have hit DuplicateNameException");
        } catch (DuplicateNameException ex) {
        }
    }
    
    @Test
    public void testCreateOrgNullDesc() throws OrgDaoException, BadUpdateException, InvalidEntityException, DuplicateNameException {
        try {
            Super sup1 = new Super(1, "First Hero", "First HD");
            Set<Super> superSet = new HashSet<>();
            superSet.add(sup1);
            Org toCreate = new Org("Fourth Org", null, "Fourth Adr", "888-111-9999", superSet);
            dao.createOrg(toCreate);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testCreateOrgBadDesc() throws OrgDaoException, BadUpdateException, InvalidEntityException, DuplicateNameException {
        try {
            Super sup1 = new Super(1, "First Hero", "First HD");
            Set<Super> superSet = new HashSet<>();
            superSet.add(sup1);
            Org toCreate = new Org("Fourth Org", createBadDescription(), "Fourth Adr", "888-111-9999", superSet);
            dao.createOrg(toCreate);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testCreateOrgNullAddress() throws OrgDaoException, BadUpdateException, InvalidEntityException, DuplicateNameException {
        try {
            Super sup1 = new Super(1, "First Hero", "First HD");
            Set<Super> superSet = new HashSet<>();
            superSet.add(sup1);
            Org toCreate = new Org("Fourth Org", "Fourth OD", null, "888-111-9999", superSet);
            dao.createOrg(toCreate);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testCreateOrgBadAddress() throws OrgDaoException, BadUpdateException, InvalidEntityException, DuplicateNameException {
        try {
            Super sup1 = new Super(1, "First Hero", "First HD");
            Set<Super> superSet = new HashSet<>();
            superSet.add(sup1);
            Org toCreate = new Org("Fourth Org", "Fourth OD", createBadAddress(), "888-111-9999", superSet);
            dao.createOrg(toCreate);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testCreateOrgNullPhone() throws OrgDaoException, BadUpdateException, InvalidEntityException, DuplicateNameException {
        try {
            Super sup1 = new Super(1, "First Hero", "First HD");
            Set<Super> superSet = new HashSet<>();
            superSet.add(sup1);
            Org toCreate = new Org("Fourth Org", "Fourth OD", "Fourth Adr", null, superSet);
            dao.createOrg(toCreate);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testCreateOrgBadPhone() throws OrgDaoException, BadUpdateException, InvalidEntityException, DuplicateNameException {
        try {
            Super sup1 = new Super(1, "First Hero", "First HD");
            Set<Super> superSet = new HashSet<>();
            superSet.add(sup1);
            Org toCreate = new Org("Fourth Org", "Fourth OD", "Fourth Adr", createBadPhone(), superSet);
            dao.createOrg(toCreate);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testCreateOrgNullSuper() throws OrgDaoException, BadUpdateException, InvalidEntityException, DuplicateNameException {
        try {
            Super sup1 = new Super(1, "First Hero", "First HD");
            Set<Super> superSet = new HashSet<>();
            superSet.add(sup1);
            Org toCreate = new Org("Fourth Org", "Fourth OD", "Fourth Adr", "888-111-9999", null);
            dao.createOrg(toCreate);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    /**
     * Test of editOrg method, of class OrgDBImpl.
     */
    @Test
    public void testEditOrgGoldenPath() throws OrgDaoException, BadUpdateException, InvalidEntityException, DuplicateNameException {
        Super sup1 = new Super(1, "First Hero", "First HD");
        Set<Super> superSet = new HashSet<>();
        superSet.add(sup1);
        Org toEdit = new Org(1, "First Org PE", "First OD PE", "First Adr PE", "777-111-9999", superSet);
        Org preEdit = dao.getOrgById(1);
        assertEquals(1, preEdit.getId());
        assertEquals("First Org", preEdit.getName());
        assertEquals("First OD", preEdit.getDescription());
        assertEquals("First Adr", preEdit.getAddress());
        assertEquals("666-555-4444", preEdit.getPhone());
        assertEquals(2, preEdit.getSupers().size());
        dao.editOrg(toEdit);
        Org postEdit = dao.getOrgById(1);
        assertEquals(1, postEdit.getId());
        assertEquals("First Org PE", postEdit.getName());
        assertEquals("First OD PE", postEdit.getDescription());
        assertEquals("First Adr PE", postEdit.getAddress());
        assertEquals("777-111-9999", postEdit.getPhone());
        assertEquals(1, postEdit.getSupers().size());
    }
    
    @Test
    public void testEditOrgNullName() throws OrgDaoException, BadUpdateException, InvalidEntityException, DuplicateNameException {
        try {
            Super sup1 = new Super(1, "First Hero", "First HD");
            Set<Super> superSet = new HashSet<>();
            superSet.add(sup1);
            Org toEdit = new Org(1, null, "First OD PE", "First Adr PE", "777-111-9999", superSet);
            dao.editOrg(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditOrgBadName() throws OrgDaoException, BadUpdateException, DuplicateNameException {
        try {
            Super sup1 = new Super(1, "First Hero", "First HD");
            Set<Super> superSet = new HashSet<>();
            superSet.add(sup1);
            Org toEdit = new Org(1, createBadName(), "First OD PE", "First Adr PE", "777-111-9999", superSet);
            dao.editOrg(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditOrgDuplicateName() throws OrgDaoException, BadUpdateException, DuplicateNameException, InvalidEntityException {
        try {
            Super sup1 = new Super(1, "First Hero", "First HD");
            Set<Super> superSet = new HashSet<>();
            superSet.add(sup1);
            Org toEdit = new Org(1, "Second Org", "First OD PE", "First Adr PE", "777-111-9999", superSet);
            dao.editOrg(toEdit);
            fail("Should have hit DuplicateNameException");
        } catch (DuplicateNameException ex) {
        }
    }
    
    @Test
    public void testEditOrgNullDesc() throws OrgDaoException, BadUpdateException, DuplicateNameException {
        try {
            Super sup1 = new Super(1, "First Hero", "First HD");
            Set<Super> superSet = new HashSet<>();
            superSet.add(sup1);
            Org toEdit = new Org(1, "First Org PE", null, "First Adr PE", "777-111-9999", superSet);
            dao.editOrg(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditOrgBadDesc() throws OrgDaoException, BadUpdateException, DuplicateNameException {
        try {
            Super sup1 = new Super(1, "First Hero", "First HD");
            Set<Super> superSet = new HashSet<>();
            superSet.add(sup1);
            Org toEdit = new Org(1, "First Org PE", createBadDescription(), "First Adr PE", "777-111-9999", superSet);
            dao.editOrg(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditOrgNullAddress() throws OrgDaoException, BadUpdateException, DuplicateNameException {
        try {
            Super sup1 = new Super(1, "First Hero", "First HD");
            Set<Super> superSet = new HashSet<>();
            superSet.add(sup1);
            Org toEdit = new Org(1, "First Org PE", "First OD PE", null, "777-111-9999", superSet);
            dao.editOrg(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditOrgBadAddress() throws OrgDaoException, BadUpdateException, DuplicateNameException {
        try {
            Super sup1 = new Super(1, "First Hero", "First HD");
            Set<Super> superSet = new HashSet<>();
            superSet.add(sup1);
            Org toEdit = new Org(1, "First Org PE", "First OD PE", createBadAddress(), "777-111-9999", superSet);
            dao.editOrg(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditOrgNullPhone() throws OrgDaoException, BadUpdateException, DuplicateNameException {
        try {
            Super sup1 = new Super(1, "First Hero", "First HD");
            Set<Super> superSet = new HashSet<>();
            superSet.add(sup1);
            Org toEdit = new Org(1, "First Org PE", "First OD PE", "First Adr PE", null, superSet);
            dao.editOrg(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditOrgBadPhone() throws OrgDaoException, BadUpdateException, DuplicateNameException {
        try {
            Super sup1 = new Super(1, "First Hero", "First HD");
            Set<Super> superSet = new HashSet<>();
            superSet.add(sup1);
            Org toEdit = new Org(1, "First Org PE", "First OD PE", "First Adr PE", createBadPhone(), superSet);
            dao.editOrg(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditOrgNullSuper() throws OrgDaoException, BadUpdateException, DuplicateNameException {
        try {
            Org toEdit = new Org(1, "First Org PE", "First OD PE", "First Adr PE", "777-111-9999", null);
            dao.editOrg(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    /**
     * Test of removeOrg method, of class OrgDBImpl.
     */
    @Test
    public void testRemoveOrg() throws OrgDaoException, BadUpdateException {
        List<Org> preRemove = dao.getAllOrgs();
        assertEquals(3, preRemove.size());
        dao.removeOrg(2);
        List<Org> allOrgs = dao.getAllOrgs();
        assertEquals(2, allOrgs.size());
        Org first = allOrgs.get(0);
        assertEquals(1, first.getId());
        assertEquals("First Org", first.getName());
        assertEquals("First OD", first.getDescription());
        assertEquals("First Adr", first.getAddress());
        assertEquals("666-555-4444", first.getPhone());
        assertEquals(2, first.getSupers().size());
        Org last = allOrgs.get(allOrgs.size() - 1);
        assertEquals(3, last.getId());
        assertEquals("Third Org", last.getName());
        assertEquals("Third OD", last.getDescription());
        assertEquals("Third Adr", last.getAddress());
        assertEquals("444-555-6666", last.getPhone());
        assertEquals(1, last.getSupers().size());
    }
    
    private String createBadName(){
        char[] chars = new char[51];
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
    
    private String createBadDescription(){
        char[] chars = new char[256];
        Arrays.fill(chars, 'a');
        return new String(chars);
    }
}
