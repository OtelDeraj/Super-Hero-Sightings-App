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
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
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
    
    @Test
    public void testGetLocationByBadId() {
        try {
            dao.getLocationById(-1);
            fail("Should have hit LocationDaoException");
        } catch (LocationDaoException ex) {
        }
        
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
    
    @Test
    public void testGetLocationByBadName()  {
        try {
            dao.getLocationByName("Fake Name");
            fail("Should have hit LocationDaoException");
        } catch (LocationDaoException ex) {
        }
        
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
    
    @Test
    public void testGetLocationByBadAddress() {
        try {
            dao.getLocationByAddress("Fake Address");
            fail("Should have hit LocationDaoException");
        } catch (LocationDaoException ex) {
        }
        
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
    
    @Test
    public void testGetLocationByNullCoord() throws LocationDaoException {
        try {
            Coord toSearch = null;
            dao.getLocationByCoord(toSearch);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testGetLocationByCoordNullLat() throws LocationDaoException {
        try {
            Coord toSearch = new Coord(null, new BigDecimal("180.00000"));
            dao.getLocationByCoord(toSearch);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
        
    }
    
    @Test
    public void testGetLocationByCoordNullLon() throws LocationDaoException {
        try {
            Coord toSearch = new Coord(new BigDecimal("90.00000"), null);
            dao.getLocationByCoord(toSearch);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testGetLocationByBadCoord() throws InvalidEntityException {
        try {
            Coord toSearch = new Coord(new BigDecimal("0.00000"), new BigDecimal("0.00000"));
            dao.getLocationByCoord(toSearch);
            fail("Should have hit LocationDaoException");
        } catch (LocationDaoException ex) {
        }
        
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
    
    @Test
    public void testGetAllLocationsEmptyResult() {
        try {
            template.update("DELETE FROM Sightings");
            template.update("DELETE FROM Locations");
            dao.getAllLocations();
            fail("Should have hit LocationDaoException"); 
        } catch (LocationDaoException ex) {
        }
    }

    /**
     * Test of createLocation method, of class LocationDBImpl.
     */
    @Test
    public void testCreateLocationGoldenPath() throws LocationDaoException, BadUpdateException, InvalidEntityException, DuplicateNameException {
        Location toCreate = new Location("New Name", "New Desc", "New Address",new BigDecimal("90.00000"), new BigDecimal("-180.00000"));
        Location returned = dao.createLocation(toCreate);
        assertEquals(4, returned.getId());
        assertEquals("New Name", returned.getName());
        assertEquals("New Desc", returned.getDescription());
        assertEquals("New Address", returned.getAddress());
        assertEquals(new BigDecimal("90.00000"), returned.getLat());
        assertEquals(new BigDecimal("-180.00000"), returned.getLon());
    }
    
    @Test
    public void testCreateLocationNullName() throws LocationDaoException, BadUpdateException, DuplicateNameException {
        try {
            Location toCreate = new Location(null, "New Desc", "New Address",new BigDecimal("90.00000"), new BigDecimal("-180.00000"));
            dao.createLocation(toCreate);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
        
    }
    
    @Test
    public void testCreateLocationBadName() throws LocationDaoException, BadUpdateException, DuplicateNameException {
        try {
            Location toCreate = new Location(createBadName(), "New Desc", "New Address",new BigDecimal("90.00000"), new BigDecimal("-180.00000"));
            dao.createLocation(toCreate);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
        
    }
    
    @Test
    public void testCreateLocationDuplicateName() throws LocationDaoException, BadUpdateException, InvalidEntityException {
        try {
            Location toCreate = new Location("First Name", "New Desc", "New Address",new BigDecimal("90.00000"), new BigDecimal("-180.00000"));
            dao.createLocation(toCreate);
            fail("Should have hit DuplicateNameException");
        } catch (DuplicateNameException ex) {
        }
        
    }
    
    @Test
    public void testCreateLocationNullDescription() throws LocationDaoException, BadUpdateException, DuplicateNameException {
        try {
            Location toCreate = new Location("New Name", null, "New Address",new BigDecimal("90.00000"), new BigDecimal("-180.00000"));
            dao.createLocation(toCreate);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
        
    }
    
    @Test
    public void testCreateLocationBadDescription() throws LocationDaoException, BadUpdateException, DuplicateNameException {
        try {
            Location toCreate = new Location("New Name", createBadDescription(), "New Address",new BigDecimal("90.00000"), new BigDecimal("-180.00000"));
            dao.createLocation(toCreate);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
        
    }
    
    @Test
    public void testCreateLocationNullAddress() throws LocationDaoException, BadUpdateException, DuplicateNameException {
        try {
            Location toCreate = new Location("New Name", "New Desc", null,new BigDecimal("90.00000"), new BigDecimal("-180.00000"));
            dao.createLocation(toCreate);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
        
    }
    
    @Test
    public void testCreateLocationBadAddress() throws LocationDaoException, BadUpdateException, DuplicateNameException {
        try {
            Location toCreate = new Location("New Name", "New Desc", createBadAddress(),new BigDecimal("90.00000"), new BigDecimal("-180.00000"));
            dao.createLocation(toCreate);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
       
    }
    
    @Test
    public void testCreateLocationNullLat() throws LocationDaoException, BadUpdateException, DuplicateNameException {
        try {
            Location toCreate = new Location("New Name", "New Desc", "New Address",null, new BigDecimal("-180.00000"));
            dao.createLocation(toCreate);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
        
    }
    
    @Test
    public void testCreateLocationHighLat() throws LocationDaoException, BadUpdateException, DuplicateNameException {
        try {
            Location toCreate = new Location("New Name", "New Desc", "New Address",new BigDecimal("91.00000"), new BigDecimal("-180.00000"));
            dao.createLocation(toCreate);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
        
    }
    
    @Test
    public void testCreateLocationLowLat() throws LocationDaoException, BadUpdateException, DuplicateNameException {
        try {
            Location toCreate = new Location("New Name", "New Desc", "New Address",new BigDecimal("-91.00000"), new BigDecimal("-180.00000"));
            dao.createLocation(toCreate);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
        
    }
    
    @Test
    public void testCreateLocationNullLon() throws LocationDaoException, BadUpdateException, DuplicateNameException {
        try {
            Location toCreate = new Location("New Name", "New Desc", "New Address",new BigDecimal("90.00000"), null);
            dao.createLocation(toCreate);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
        
    }
    
    @Test
    public void testCreateLocationHighLon() throws LocationDaoException, BadUpdateException, DuplicateNameException {
        try {
            Location toCreate = new Location("New Name", "New Desc", "New Address",new BigDecimal("90.00000"), new BigDecimal("181.00000"));
            dao.createLocation(toCreate);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
        
    }
    
    @Test
    public void testCreateLocationLowLon() throws LocationDaoException, BadUpdateException, DuplicateNameException {
        try {
            Location toCreate = new Location("New Name", "New Desc", "New Address",new BigDecimal("90.00000"), new BigDecimal("-181.00000"));
            dao.createLocation(toCreate);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
        
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
    
    @Test
    public void testEditLocationBadId() throws LocationDaoException, InvalidEntityException, DuplicateNameException {      
        try {
            Location toEdit = new Location(-1, "Edit Name", "Edit Desc", "Edit Address", new BigDecimal("-90.00000"), new BigDecimal("-180.00000"));
            dao.editLocation(toEdit);
            fail("Should have hit BadUpdateException");
        } catch (BadUpdateException ex) {
        }
        
    }
    
    @Test
    public void testEditLocationNullName() throws LocationDaoException, BadUpdateException, DuplicateNameException {      
        try {
            Location toEdit = new Location(1, null, "Edit Desc", "Edit Address", new BigDecimal("-90.00000"), new BigDecimal("-180.00000"));
            dao.editLocation(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
        
    }
    
    @Test
    public void testEditLocationBadName() throws LocationDaoException, BadUpdateException, DuplicateNameException {      
        try {
            Location toEdit = new Location(1, createBadName(), "Edit Desc", "Edit Address", new BigDecimal("-90.00000"), new BigDecimal("-180.00000"));
            dao.editLocation(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditLocationDuplicateName() throws LocationDaoException, BadUpdateException, InvalidEntityException {
        try {
            Location toEdit = new Location(1, "Third Name", "Edit Desc", "Edit Address", new BigDecimal("-90.00000"), new BigDecimal("-180.00000"));
            dao.editLocation(toEdit);
            fail("Should have hit DuplicateNameException");
        } catch (DuplicateNameException ex) {
        }
    }
    
    @Test
    public void testEditLocationNullDesc() throws LocationDaoException, BadUpdateException, DuplicateNameException {
        try {
            Location toEdit = new Location(1, "Edit Name", null, "Edit Address", new BigDecimal("-90.00000"), new BigDecimal("-180.00000"));
            dao.editLocation(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditLocationBadDesc() throws LocationDaoException, BadUpdateException, DuplicateNameException {       
        try {
            Location toEdit = new Location(1, "Edit Name", createBadDescription(), "Edit Address", new BigDecimal("-90.00000"), new BigDecimal("-180.00000"));
            dao.editLocation(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditLocationNullAddress() throws LocationDaoException, BadUpdateException, DuplicateNameException {
        try {
            Location toEdit = new Location(1, "Edit Name", "Edit Desc", null, new BigDecimal("-90.00000"), new BigDecimal("-180.00000"));
            dao.editLocation(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditLocationBadAddress() throws LocationDaoException, BadUpdateException, DuplicateNameException {
        try {
            Location toEdit = new Location(1, "Edit Name", "Edit Desc", createBadAddress(), new BigDecimal("-90.00000"), new BigDecimal("-180.00000"));
            dao.editLocation(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditLocationNullLat() throws LocationDaoException, BadUpdateException, DuplicateNameException {
        try {
            Location toEdit = new Location(1, "Edit Name", "Edit Desc", "Edit Address", null, new BigDecimal("-180.00000"));
            dao.editLocation(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditLocationHighLat() throws LocationDaoException, BadUpdateException, DuplicateNameException {
        try {
            Location toEdit = new Location(1, "Edit Name", "Edit Desc", "Edit Address", new BigDecimal("91.00000"), new BigDecimal("-180.00000"));;
            dao.editLocation(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditLocationLowLat() throws LocationDaoException, BadUpdateException, DuplicateNameException {
        try {
            Location toEdit = new Location(1, "Edit Name", "Edit Desc", "Edit Address", new BigDecimal("-91.00000"), new BigDecimal("-180.00000"));
            dao.editLocation(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditLocationNullLon() throws LocationDaoException, BadUpdateException, DuplicateNameException {
        try {
            Location toEdit = new Location(1, "Edit Name", "Edit Desc", "Edit Address", new BigDecimal("-90.00000"), null);
            dao.editLocation(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditLocationHighLon() throws LocationDaoException, BadUpdateException, DuplicateNameException {
        try {
            Location toEdit = new Location(1, "Edit Name", "Edit Desc", "Edit Address", new BigDecimal("-90.00000"), new BigDecimal("181.00000"));
            dao.editLocation(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditLocationLowLon() throws LocationDaoException, BadUpdateException, DuplicateNameException {
        try {
            Location toEdit = new Location(1, "Edit Name", "Edit Desc", "Edit Address", new BigDecimal("-90.00000"), new BigDecimal("-181.00000"));
            dao.editLocation(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    /**
     * Test of removeLocation method, of class LocationDBImpl.
     */
    @Test
    public void testRemoveLocationGoldenPath() throws LocationDaoException, BadUpdateException {
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
    
    @Test
    public void testRemoveLocationBadId() throws LocationDaoException {
        try {
            dao.removeLocation(-1);
            fail("Should have hit BadUpdateException");
        } catch (BadUpdateException ex) {
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
    
}
