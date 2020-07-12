/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.dao;

import com.sg.SuperHeroSighting.dto.Coord;
import com.sg.SuperHeroSighting.dto.Location;
import com.sg.SuperHeroSighting.exceptions.BadUpdateException;
import com.sg.SuperHeroSighting.exceptions.LocationDaoException;
import java.awt.Point;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
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
    public Location getLocationById(int id) {
        return template.queryForObject("SELECT * FROM Locations WHERE locId = ?", new LocationMapper(), id);
    }

    @Override
    public Location getLocationByName(String name) {
        return template.queryForObject("SELECT * FROM Locations WHERE name = ?", new LocationMapper(), name);
    }

    @Override
    public Location getLocationByAddress(String address) {
        return template.queryForObject("SELECT * FROM Locations WHERE address = ?", new LocationMapper(), address);
    }

    @Override
    public Location getLocationByCoord(Coord coord) {
        return template.queryForObject("SELECT * FROM Locations WHERE lat = ? AND lon = ?",
                new LocationMapper(), coord.getLat(), coord.getLon());
    }

    @Override
    public List<Location> getAllLocations() throws LocationDaoException {
        List<Location> allLocations = template.query("SELECT * FROM Locations", new LocationMapper());
        if(allLocations.size() == 0) throw new LocationDaoException("No locations found");
        return allLocations;
    }

    @Override
    public Location createLocation(Location toAdd) throws LocationDaoException {
        int affectedRows = template.update("INSERT INTO Locations(name, description, address, lat, lon)"
                + "VALUES(?, ?, ?, ?, ?)", toAdd.getName(), toAdd.getDescription(), toAdd.getAddress(),
                toAdd.getCoord().getLat(), toAdd.getCoord().getLon());
        if(affectedRows < 1) throw new LocationDaoException("Failed to add location");
        int newId = template.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        toAdd.setId(newId);
        return toAdd;
    }

    @Override
    public void editLocation(Location toEdit) throws BadUpdateException {
        int affectedRows = template.update("UPDATE Locations SET name = ?, description = ?, address = ?,"
                + " lat = ?, lon = ? WHERE locId = ?", toEdit.getName(), toEdit.getDescription(), toEdit.getAddress(),
                toEdit.getCoord().getLat(), toEdit.getCoord().getLon(), toEdit.getId());
        if(affectedRows < 1) throw new BadUpdateException("No locations updated");
        if(affectedRows > 1) throw new BadUpdateException("More than one location updated");
    }

    @Override
    public void removeLocation(int id) throws BadUpdateException {
        template.update("DELETE FROM Sightings WHERE locId = ?", id);
        int affectedRows = template.update("DELETE FROM Locations WHERE locId = ?", id);
        if(affectedRows < 1) throw new BadUpdateException("No locations removed");
        if(affectedRows > 1) throw new BadUpdateException("More than one location removed");
    }

    private static class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int i) throws SQLException {
            Location toReturn = new Location(
                    rs.getInt("locId"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("address"),
                    new Coord(
                            rs.getBigDecimal("lat"),
                            rs.getBigDecimal("lon")
                    )
            );
            return toReturn;
        }

    }

}
