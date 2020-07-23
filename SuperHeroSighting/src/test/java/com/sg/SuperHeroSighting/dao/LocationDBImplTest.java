/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.dao;

import com.sg.SuperHeroSighting.TestAppConfig;
import com.sg.SuperHeroSighting.dto.Coord;
import com.sg.SuperHeroSighting.dto.Location;
import com.sg.SuperHeroSighting.exceptions.BadUpdateException;
import com.sg.SuperHeroSighting.exceptions.DuplicateNameException;
import com.sg.SuperHeroSighting.exceptions.InvalidEntityException;
import com.sg.SuperHeroSighting.exceptions.LocationDaoException;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
public class LocationDBImplTest {
    
    @Autowired
    LocationDao dao;
    
    @Autowired
    JdbcTemplate template;
    
    @BeforeEach
    public void setUp() {
        template.update("DELETE FROM Sightings");
        template.update("DELETE FROM Locations");
        template.update("ALTER TABLE Locations auto_increment = 1");
        template.update("INSERT INTO Locations(name, description, address, lat, lon) VALUES"
                + "('First Name', 'First Desc', 'First Address', 90.00000, 180.00000),"
                + "('Second Name', 'Second Desc', 'Second Address', 45.00000, 90.00000),"
                + "('Third Name', 'Third Desc', 'Third Address', -45.00000, -90.00000)");
    }

    /**
     * Test of getLocationById method, of class LocationDBImpl.
     */
    @Test
    public void testGetLocationById() throws LocationDaoException {
        Location toTest = dao.getLocationById(1);
        assertEquals(1, toTest.getId());
        assertEquals("First Name", toTest.getName());
        assertEquals("First Desc", toTest.getDescription());
        assertEquals("First Address", toTest.getAddress());
        assertEquals(new BigDecimal("90.00000"), toTest.getLat());
        assertEquals(new BigDecimal("180.00000"), toTest.getLon());
    }

    /**
     * Test of getLocationByName method, of class LocationDBImpl.
     */
    @Test
    public void testGetLocationByName() throws LocationDaoException {
        Location toTest = dao.getLocationByName("First Name");
        assertEquals(1, toTest.getId());
        assertEquals("First Name", toTest.getName());
        assertEquals("First Desc", toTest.getDescription());
        assertEquals("First Address", toTest.getAddress());
        assertEquals(new BigDecimal("90.00000"), toTest.getLat());
        assertEquals(new BigDecimal("180.00000"), toTest.getLon());
    }

    /**
     * Test of getLocationByAddress method, of class LocationDBImpl.
     */
    @Test
    public void testGetLocationByAddress() throws LocationDaoException {
        Location toTest = dao.getLocationByAddress("First Address");
        assertEquals(1, toTest.getId());
        assertEquals("First Name", toTest.getName());
        assertEquals("First Desc", toTest.getDescription());
        assertEquals("First Address", toTest.getAddress());
        assertEquals(new BigDecimal("90.00000"), toTest.getLat());
        assertEquals(new BigDecimal("180.00000"), toTest.getLon());
    }

    /**
     * Test of getLocationByCoord method, of class LocationDBImpl.
     */
    @Test
    public void testGetLocationByCoord() throws LocationDaoException, InvalidEntityException {
        Coord toSearch = new Coord(new BigDecimal("90.00000"), new BigDecimal("180.00000"));
        Location toTest = dao.getLocationByCoord(toSearch);
        assertEquals(1, toTest.getId());
        assertEquals("First Name", toTest.getName());
        assertEquals("First Desc", toTest.getDescription());
        assertEquals("First Address", toTest.getAddress());
        assertEquals(new BigDecimal("90.00000"), toTest.getLat());
        assertEquals(new BigDecimal("180.00000"), toTest.getLon());
    }

    
    
//    "('First Name', 'First Desc', 'First Address', 90.00000, 180.00000),"
//                + "('Second Name', 'Second Desc', 'Second Address', 45.00000, 90.00000),"
//                + "('Third Name', 'Third Desc', 'Third Address', -45.00000, -90.00000)"
    /**
     * Test of getAllLocations method, of class LocationDBImpl.
     */
    @Test
    public void testGetAllLocations() throws LocationDaoException {
        List<Location> allLocations = dao.getAllLocations();
        assertEquals(3, allLocations.size());
        Location first = allLocations.get(0);
        assertEquals(1, first.getId());
        assertEquals("First Name", first.getName());
        assertEquals("First Desc", first.getDescription());
        assertEquals("First Address", first.getAddress());
        assertEquals(new BigDecimal("90.00000"), first.getLat());
        assertEquals(new BigDecimal("180.00000"), first.getLon());
        Location last = allLocations.get(allLocations.size() - 1);
        assertEquals(3, last.getId());
        assertEquals("Third Name", last.getName());
        assertEquals("Third Desc", last.getDescription());
        assertEquals("Third Address", last.getAddress());
        assertEquals(new BigDecimal("-45.00000"), last.getLat());
        assertEquals(new BigDecimal("-90.00000"), last.getLon());
    }

    /**
     * Test of createLocation method, of class LocationDBImpl.
     */
    @Test
    public void testCreateLocation() throws LocationDaoException, BadUpdateException, InvalidEntityException, DuplicateNameException {
        Location toCreate = new Location("New Name", "New Desc", "New Address",new BigDecimal("90.00000"), new BigDecimal("-180.00000"));
        Location returned = dao.createLocation(toCreate);
        assertEquals(4, returned.getId());
        assertEquals("New Name", returned.getName());
        assertEquals("New Desc", returned.getDescription());
        assertEquals("New Address", returned.getAddress());
        assertEquals(new BigDecimal("90.00000"), returned.getLat());
        assertEquals(new BigDecimal("-180.00000"), returned.getLon());
    }

    /**
     * Test of editLocation method, of class LocationDBImpl.
     */
    @Test
    public void testEditLocation() throws LocationDaoException, BadUpdateException, InvalidEntityException, DuplicateNameException {
        
        Location toEdit = new Location(1, "Edit Name", "Edit Desc", "Edit Address", new BigDecimal("-90.00000"), new BigDecimal("-180.00000"));
        Location preEdit = dao.getLocationById(1);
        assertEquals(1, preEdit.getId());
        assertEquals("First Name", preEdit.getName());
        assertEquals("First Desc", preEdit.getDescription());
        assertEquals("First Address", preEdit.getAddress());
        assertEquals(new BigDecimal("90.00000"), preEdit.getLat());
        assertEquals(new BigDecimal("180.00000"), preEdit.getLon());
        dao.editLocation(toEdit);
        Location postEdit = dao.getLocationById(1);
        assertEquals(1, postEdit.getId());
        assertEquals("Edit Name", postEdit.getName());
        assertEquals("Edit Desc", postEdit.getDescription());
        assertEquals("Edit Address", postEdit.getAddress());
        assertEquals(new BigDecimal("-90.00000"), postEdit.getLat());
        assertEquals(new BigDecimal("-180.00000"), postEdit.getLon());
    }

    /**
     * Test of removeLocation method, of class LocationDBImpl.
     */
    @Test
    public void testRemoveLocation() throws LocationDaoException, BadUpdateException {
        List<Location> preRemove = dao.getAllLocations();
        assertEquals(3, preRemove.size());
        dao.removeLocation(2);
        List<Location> postRemove = dao.getAllLocations();
        assertEquals(2, postRemove.size());
        Location first = postRemove.get(0);
        assertEquals(1, first.getId());
        assertEquals("First Name", first.getName());
        assertEquals("First Desc", first.getDescription());
        assertEquals("First Address", first.getAddress());
        assertEquals(new BigDecimal("90.00000"), first.getLat());
        assertEquals(new BigDecimal("180.00000"), first.getLon());
        Location last = postRemove.get(postRemove.size() - 1);
        assertEquals(3, last.getId());
        assertEquals("Third Name", last.getName());
        assertEquals("Third Desc", last.getDescription());
        assertEquals("Third Address", last.getAddress());
        assertEquals(new BigDecimal("-45.00000"), last.getLat());
        assertEquals(new BigDecimal("-90.00000"), last.getLon());
    }
    
}
