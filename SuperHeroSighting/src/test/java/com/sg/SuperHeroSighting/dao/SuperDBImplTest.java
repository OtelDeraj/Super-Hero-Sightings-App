/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.dao;

import com.sg.SuperHeroSighting.TestAppConfig;
import com.sg.SuperHeroSighting.dto.Super;
import com.sg.SuperHeroSighting.exceptions.BadUpdateException;
import com.sg.SuperHeroSighting.exceptions.DuplicateNameException;
import com.sg.SuperHeroSighting.exceptions.InvalidEntityException;
import com.sg.SuperHeroSighting.exceptions.SuperDaoException;
import java.util.Arrays;
import java.util.List;
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
public class SuperDBImplTest {

    @Autowired
    SuperDao dao;

    @Autowired
    JdbcTemplate template;


    @BeforeEach
    public void setUp() {
        template.update("DELETE FROM Affiliations");
        template.update("DELETE FROM Sightings");
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
        template.update("DELETE FROM Orgs");
        template.update("ALTER TABLE Orgs auto_increment = 1");
        template.update("INSERT INTO Orgs(name, description, address, phone) VALUES"
                + "('GOOD', 'Good Guys', 'Good Address', 'Good Phone'),"
                + "('EVIL', 'Evil Guys', 'Evile Address', 'Evil Phone')");
        template.update("INSERT INTO Super_Powers(superId, powerId) VALUES"
                + "(1, 1),"
                + "(1, 3),"
                + "(2, 2),"
                + "(2, 3),"
                + "(3, 3)");
        template.update("INSERT INTO Affiliations(superId, orgId) VALUES"
                + "(1, 1),"
                + "(2, 1),"
                + "(2, 2),"
                + "(3, 2)");
    }

    /**
     * Test of getAllSupers method, of class SuperDBImpl.
     */
    @Test
    public void testGetAllSupers() throws SuperDaoException {
        List<Super> allSupers = dao.getAllSupers();
        Super first = allSupers.get(0);
        Super last = allSupers.get(allSupers.size() - 1);
        assertEquals(3, allSupers.size());
        assertEquals(1, first.getId());
        assertEquals("First Hero", first.getName());
        assertEquals("First Desc", first.getDescription());
        assertEquals(2, first.getPowers().size());
        assertEquals(1, first.getOrgs().size());
        assertEquals(3, last.getId());
        assertEquals("Third Hero", last.getName());
        assertEquals("Third Desc", last.getDescription());
        assertEquals(1, last.getPowers().size());
        assertEquals(1, last.getOrgs().size());
    }
    
    @Test
    public void testGetAllSupersEmptyReturn() {
        try {
            template.update("DELETE FROM Sightings");
            template.update("DELETE FROM Affiliations");
            template.update("DELETE FROM Super_Powers");
            template.update("DELETE FROM Supers");
            dao.getAllSupers();
            fail("Should have hit SuperDaoException");
        } catch (SuperDaoException ex) {
        }
        
    }

    /**
     * Test of getSupersByPowerId method, of class SuperDBImpl.
     */
    @Test
    public void testGetSupersByPowerId() throws SuperDaoException {
        List<Super> supByPower = dao.getSupersByPowerId(3);
        Super first = supByPower.get(0);
        Super last = supByPower.get(supByPower.size() - 1);
        assertEquals(3, supByPower.size());
        assertEquals(1, first.getId());
        assertEquals("First Hero", first.getName());
        assertEquals("First Desc", first.getDescription());
        assertEquals(2, first.getPowers().size());
        assertEquals(1, first.getOrgs().size());
        assertEquals(3, last.getId());
        assertEquals("Third Hero", last.getName());
        assertEquals("Third Desc", last.getDescription());
        assertEquals(1, last.getPowers().size());
        assertEquals(1, last.getOrgs().size());
    }
    
    @Test
    public void testGetSupersByBadPowerId() {
        try {
            dao.getSupersByPowerId(-1);
            fail("Should have hit SuperDaoException");
        } catch (SuperDaoException ex) {
        }
        
    }

    /**
     * Test of getSupersByOrgId method, of class SuperDBImpl.
     */
    @Test
    public void testGetSupersByOrgId() throws SuperDaoException {
        List<Super> supByOrg = dao.getSupersByOrgId(2);
        Super first = supByOrg.get(0);
        Super last = supByOrg.get(supByOrg.size() - 1);
        assertEquals(2, supByOrg.size());
        assertEquals(2, first.getId());
        assertEquals("Second Hero", first.getName());
        assertEquals("Second Desc", first.getDescription());
        assertEquals(2, first.getPowers().size());
        assertEquals(2, first.getOrgs().size());
        assertEquals(3, last.getId());
        assertEquals("Third Hero", last.getName());
        assertEquals("Third Desc", last.getDescription());
        assertEquals(1, last.getPowers().size());
        assertEquals(1, last.getOrgs().size());
    }
    
    @Test
    public void testGetSupersByBadOrgId() {
        try {
            dao.getSupersByOrgId(-1);
            fail("Should have hit SuperDaoException");
        } catch (SuperDaoException ex) {
        }
        
    }

    /**
     * Test of createSuper method, of class SuperDBImpl.
     */
    @Test  // Super( name, description, powers, orgs )
    public void testCreateSuper() throws SuperDaoException, InvalidEntityException, BadUpdateException, DuplicateNameException {
        Super toCreate = dao.getSuperById(1);
        toCreate.setName("Fourth Hero");
        toCreate.setDescription("Fourth Desc");
        Super returned = dao.createSuper(toCreate);
        assertEquals(4, returned.getId());
        assertEquals("Fourth Hero", returned.getName());
        assertEquals("Fourth Desc", returned.getDescription());
        assertEquals(2, returned.getPowers().size());
        assertEquals(1, returned.getOrgs().size());
    }
    
    @Test  // Super( name, description, powers, orgs )
    public void testCreateSuperNullName() throws BadUpdateException, DuplicateNameException, SuperDaoException {
        try {
            Super toCreate = dao.getSuperById(1);
            toCreate.setName(null);
            dao.createSuper(toCreate);
            fail("Should have thrown InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }   
    }
    
    @Test  // Super( name, description, powers, orgs )
    public void testCreateSuperBadName() throws BadUpdateException, DuplicateNameException, SuperDaoException {
        try {
            Super toCreate = dao.getSuperById(1);
            toCreate.setName(createBadName());
            dao.createSuper(toCreate);
            fail("Should have thrown InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }   
    }
    
    @Test  // Super( name, description, powers, orgs )
    public void testCreateSuperNullDesc() throws SuperDaoException, InvalidEntityException, BadUpdateException, DuplicateNameException {
        try {
            Super toCreate = dao.getSuperById(1);
            toCreate.setDescription(null);
            dao.createSuper(toCreate);
            fail("Should have thrown InvalidEntityException");
        } catch (InvalidEntityException ex) {
        } 
    }
    
    @Test  // Super( name, description, powers, orgs )
    public void testCreateSuperBadDesc() throws BadUpdateException, DuplicateNameException, SuperDaoException {
        try {
            Super toCreate = dao.getSuperById(1);
            toCreate.setName(createBadDescription());
            dao.createSuper(toCreate);
            fail("Should have thrown InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }   
    }
    
    @Test  // Super( name, description, powers, orgs )
    public void testCreateSuperNullPowers() throws SuperDaoException, InvalidEntityException, BadUpdateException, DuplicateNameException {
        try {
            Super toCreate = dao.getSuperById(1);
            toCreate.setPowers(null);
            dao.createSuper(toCreate);
            fail("Should have thrown InvalidEntityException");
        } catch (InvalidEntityException ex) {
        } 
    }
    
    @Test  // Super( name, description, powers, orgs )
    public void testCreateSuperNullOrgs() throws SuperDaoException, InvalidEntityException, BadUpdateException, DuplicateNameException {
        try {
            Super toCreate = dao.getSuperById(1);
            toCreate.setOrgs(null);
            dao.createSuper(toCreate);
            fail("Should have thrown InvalidEntityException");
        } catch (InvalidEntityException ex) {
        } 
    }

    /**
     * Test of editSuper method, of class SuperDBImpl.
     */
    @Test
    public void testEditSuperGoldenPath() throws SuperDaoException, BadUpdateException, InvalidEntityException, DuplicateNameException {
        Super toEdit = dao.getSuperById(1);
        toEdit.setName("First Hero PE");
        toEdit.setDescription("First Description PE");
        dao.editSuper(toEdit);
        Super postEdit = dao.getSuperById(1);
        assertEquals(1, postEdit.getId());
        assertEquals(toEdit.getName(), postEdit.getName());
        assertEquals(toEdit.getDescription(), postEdit.getDescription());
        assertEquals(toEdit.getPowers(), postEdit.getPowers());
        assertEquals(toEdit.getOrgs(), postEdit.getOrgs());
    }
    
    @Test
    public void testEditSuperNullName() throws SuperDaoException, BadUpdateException, DuplicateNameException {
        try {
            Super toEdit = dao.getSuperById(1);
            toEdit.setName(null);
            dao.editSuper(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditSuperBadName() throws SuperDaoException, BadUpdateException, InvalidEntityException, DuplicateNameException {
        try {
            Super toEdit = dao.getSuperById(1);
            toEdit.setName(createBadName());
            dao.editSuper(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditSuperNullDesc() throws SuperDaoException, BadUpdateException, InvalidEntityException, DuplicateNameException {
        try {
            Super toEdit = dao.getSuperById(1);
            toEdit.setDescription(null);
            dao.editSuper(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditSuperBadDesc() throws SuperDaoException, BadUpdateException, InvalidEntityException, DuplicateNameException {
        try {
            Super toEdit = dao.getSuperById(1);
            toEdit.setDescription(createBadDescription());
            dao.editSuper(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditSuperNullPowers() throws SuperDaoException, BadUpdateException, InvalidEntityException, DuplicateNameException {
        try {
            Super toEdit = dao.getSuperById(1);
            toEdit.setName(null);
            dao.editSuper(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditSuperNullOrgs() throws SuperDaoException, BadUpdateException, InvalidEntityException, DuplicateNameException {
        try {
            Super toEdit = dao.getSuperById(1);
            toEdit.setName(null);
            dao.editSuper(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    /**
     * Test of removeSuper method, of class SuperDBImpl.
     */
    @Test
    public void testRemoveSuper() throws SuperDaoException, BadUpdateException {
        List<Super> allSupers = dao.getAllSupers();
        assertEquals(3, allSupers.size());
        dao.removeSuper(2);
        List<Super> postRemove = dao.getAllSupers();
        Super first = postRemove.get(0);
        Super last = postRemove.get(postRemove.size() - 1);
        assertEquals(2, postRemove.size());
        assertEquals(1, first.getId());
        assertEquals("First Hero", first.getName());
        assertEquals("First Desc", first.getDescription());
        assertEquals(2, first.getPowers().size());
        assertEquals(1, first.getOrgs().size());
        assertEquals(3, last.getId());
        assertEquals("Third Hero", last.getName());
        assertEquals("Third Desc", last.getDescription());
        assertEquals(1, last.getPowers().size());
        assertEquals(1, last.getOrgs().size());
    }
    
    @Test
    public void testRemoveSuperBadId() throws SuperDaoException {
        try {
            dao.removeSuper(-1);
            fail("Should have hit BadUpdateException");
        } catch (BadUpdateException ex) {
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
