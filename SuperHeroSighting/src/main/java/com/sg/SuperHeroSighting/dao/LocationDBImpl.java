/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.dao;

import com.sg.SuperHeroSighting.dto.Coord;
import com.sg.SuperHeroSighting.dto.Location;
import com.sg.SuperHeroSighting.exceptions.BadUpdateException;
import com.sg.SuperHeroSighting.exceptions.DuplicateNameException;
import com.sg.SuperHeroSighting.exceptions.InvalidEntityException;
import com.sg.SuperHeroSighting.exceptions.LocationDaoException;
import java.awt.Point;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Isaia
 */
@Repository
@Profile("database")
public class LocationDBImpl implements LocationDao {

    @Autowired
    JdbcTemplate template;

    @Override
    public Location getLocationById(int id) throws LocationDaoException {
        try {
            return template.queryForObject("SELECT * FROM Locations WHERE locId = ?", new LocationMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
            throw new LocationDaoException("get location by name failed");
        }
    }

    @Override
    public Location getLocationByName(String name) throws LocationDaoException {
        try {
            return template.queryForObject("SELECT * FROM Locations WHERE name = ?", new LocationMapper(), name);
        } catch (EmptyResultDataAccessException ex) {
            throw new LocationDaoException("get location by name failed");
        }
    }

    @Override
    public Location getLocationByAddress(String address) throws LocationDaoException {
        try{
        return template.queryForObject("SELECT * FROM Locations WHERE address = ?", new LocationMapper(), address);
        } catch (EmptyResultDataAccessException ex){
            throw new LocationDaoException("Get location by address failed");
        }
    }

    @Override
    public Location getLocationByCoord(Coord toSearch) throws InvalidEntityException, LocationDaoException {
        if (toSearch == null) {
            throw new InvalidEntityException("Coord toSearch cannot be null");
        }
        if (toSearch.getLat() == null || toSearch.getLon() == null) {
            throw new InvalidEntityException("Coord toSearch cannot have null fields");
        }
        try{
        return template.queryForObject("SELECT * FROM Locations WHERE lat = ? AND lon = ?",
                new LocationMapper(), toSearch.getLat(), toSearch.getLon());
        } catch (EmptyResultDataAccessException ex){
            throw new LocationDaoException("Get location by coord failed");
        }
    }

    @Override
    public List<Location> getAllLocations() throws LocationDaoException {
        List<Location> allLocations = template.query("SELECT * FROM Locations", new LocationMapper());
        if (allLocations.isEmpty()) {
            throw new LocationDaoException("No locations found");
        }
        return allLocations;
    }

    @Override
    public Location createLocation(Location toAdd) throws LocationDaoException, InvalidEntityException, DuplicateNameException {
        validateLocationData(toAdd);
        try {
            int affectedRows = template.update("INSERT INTO Locations(name, description, address, lat, lon)"
                    + "VALUES(?, ?, ?, ?, ?)", toAdd.getName(), toAdd.getDescription(), toAdd.getAddress(),
                    toAdd.getLat(), toAdd.getLon());
            if (affectedRows < 1) {
                throw new LocationDaoException("Failed to add location");
            }
        } catch (DuplicateKeyException ex) {
            throw new DuplicateNameException("Given name already exists.");
        }
        int newId = template.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        toAdd.setId(newId);
        return toAdd;
    }

    @Override
    public void editLocation(Location toEdit) throws BadUpdateException, InvalidEntityException, DuplicateNameException {
        validateLocationData(toEdit);
        try {
            int affectedRows = template.update("UPDATE Locations SET name = ?, description = ?, address = ?,"
                    + " lat = ?, lon = ? WHERE locId = ?", toEdit.getName(), toEdit.getDescription(), toEdit.getAddress(),
                    toEdit.getLat(), toEdit.getLon(), toEdit.getId());
            if (affectedRows < 1) {
                throw new BadUpdateException("No locations updated");
            }
            if (affectedRows > 1) {
                throw new BadUpdateException("More than one location updated");
            }
        } catch (DuplicateKeyException ex) {
            throw new DuplicateNameException("Given name already exists.");
        }
    }

    @Override
    public void removeLocation(int id) throws BadUpdateException {
        template.update("DELETE FROM Sightings WHERE locId = ?", id);
        int affectedRows = template.update("DELETE FROM Locations WHERE locId = ?", id);
        if (affectedRows < 1) {
            throw new BadUpdateException("No locations removed");
        }
        if (affectedRows > 1) {
            throw new BadUpdateException("More than one location removed");
        }
    }

    private void validateLocationData(Location l) throws InvalidEntityException {
        if (l == null) {
            throw new InvalidEntityException("Location object cannot be null");
        }
        if (l.getName() == null || l.getDescription() == null || l.getAddress() == null
                || l.getLat() == null || l.getLon() == null) {
            throw new InvalidEntityException("Location fields cannot be null");
        }
        if(l.getName().trim().length() > 50) throw new InvalidEntityException("Location name must be 50 characters or less");
        if(l.getDescription().trim().length() > 255) throw new InvalidEntityException("Location description must be 255 characters or less");
        if(l.getAddress().trim().length() > 60) throw new InvalidEntityException("Locatoin address must be 60 characters or less");
        if(l.getLat().compareTo(new BigDecimal("90.00000")) == 1 || l.getLat().compareTo(new BigDecimal("-90.00000")) == -1) throw new InvalidEntityException("Latitude must be between -90 and 90");
        if(l.getLon().compareTo(new BigDecimal("180.00000")) == 1 || l.getLon().compareTo(new BigDecimal("-180.00000")) == -1) throw new InvalidEntityException("Longitude must be between -180 and 180");
    }

    private static class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int i) throws SQLException {
            Location toReturn = new Location(
                    rs.getInt("locId"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("address"),
                    rs.getBigDecimal("lat"),
                    rs.getBigDecimal("lon")
            );
            return toReturn;
        }

    }

}
