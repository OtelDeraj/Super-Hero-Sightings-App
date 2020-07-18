/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.dao;

import com.sg.SuperHeroSighting.dto.Coord;
import com.sg.SuperHeroSighting.dto.Location;
import com.sg.SuperHeroSighting.dto.Sighting;
import com.sg.SuperHeroSighting.dto.Super;
import com.sg.SuperHeroSighting.exceptions.BadUpdateException;
import com.sg.SuperHeroSighting.exceptions.InvalidEntityException;
import com.sg.SuperHeroSighting.exceptions.SightingDaoException;
import com.sg.SuperHeroSighting.exceptions.SuperDaoException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
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
public class SightingDBImpl implements SightingDao {

    @Autowired
    JdbcTemplate template;

    @Override
    public Sighting getSightingById(int id) throws SightingDaoException {
        Sighting toReturn = template.queryForObject("SELECT * FROM Sightings WHERE sightId = ?", new SightingMapper(), id);
        if(toReturn == null) throw new SightingDaoException("No Sighting found for given ID");
        return toReturn;
    }

    @Override
    public List<Sighting> getSightingsBySuperId(int id) throws SightingDaoException { // TODO add validation for all of these returning lists
        List<Sighting> toReturn = template.query("SELECT * FROM Sightings WHERE superId = ?", new SightingMapper(), id);
        if(toReturn.isEmpty()) throw new SightingDaoException("No sightings found for given Super ID");
        return toReturn;
    }

    @Override
    public List<Sighting> getSightingsByLocId(int id) throws SightingDaoException {
        List<Sighting> toReturn  = template.query("SELECT * FROM Sightings WHERE locId = ?", new SightingMapper(), id);
        if(toReturn.isEmpty()) throw new SightingDaoException("No sightings found for given Location ID");
        return toReturn;
    }

    @Override
    public List<Sighting> getSightingsByDate(Date date) throws SightingDaoException {
        List<Sighting> toReturn  = template.query("SELECT * FROM Sightings WHERE sightDate = ?", new SightingMapper(), date);
        if(toReturn.isEmpty()) throw new SightingDaoException("No sightings found for given Date");
        return toReturn;
    }

    @Override
    public List<Sighting> getAllSightings() throws SightingDaoException {
        List<Sighting> toReturn  = template.query("SELECT * FROM Sightings", new SightingMapper());
        if(toReturn.isEmpty()) throw new SightingDaoException("No sightings found");
        return toReturn;
    }

    @Override
    public Sighting addSighting(Sighting toAdd) throws SightingDaoException, InvalidEntityException {
        validateSightingData(toAdd);
        int affectedRows = template.update("INSERT INTO Sightings(sightDate, superId, locId)"
                + "VALUES(?, ?, ?)", toAdd.getDate(), toAdd.getSpottedSuper().getId(), toAdd.getLocation().getId());
        if(affectedRows < 1) throw new SightingDaoException("Sighting not added");
        int newId = template.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        toAdd.setId(newId);
        return toAdd;
    }

    @Override
    public void updateSighting(Sighting toEdit) throws BadUpdateException, InvalidEntityException {
        validateSightingData(toEdit);
        int affectedRows = template.update("UPDATE Sightings SET sightDate = ?, superId = ?, locId = ? WHERE sightId = ?",
                toEdit.getDate(), toEdit.getSpottedSuper().getId(), toEdit.getLocation().getId(), toEdit.getId());
        if(affectedRows < 1) throw new BadUpdateException("No sightings edited");
        if(affectedRows > 1) throw new BadUpdateException("More than one sighting edited");
    }

    @Override
    public void removeSighting(int id) throws BadUpdateException {
        int affectedRows = template.update("DELETE FROM Sightings WHERE sightId = ?", id);
        if(affectedRows < 1) throw new BadUpdateException("No sightings removed");
        if(affectedRows > 1) throw new BadUpdateException("More than one sighting removed");
    }

    private Super getSuperById(int id) {
        return template.queryForObject("SELECT * FROM Supers WHERE superId = ?", new SuperMapper(), id);
    }

    private Location getLocationById(int id) {
        return template.queryForObject("SELECT * FROM Locations WHERE locId = ?", new LocationMapper(), id);
    }
    
    private void validateSightingData(Sighting s) throws InvalidEntityException{
        if(s == null) throw new InvalidEntityException("Sighting object cannot be null");
        if(s.getDate() == null || s.getLocation() == null || s.getSpottedSuper() == null){
            throw new InvalidEntityException("Sighting fields cannot be null");
        }
    }

    private class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int i) throws SQLException {
            Sighting toReturn = new Sighting(
                    rs.getInt("sightId"),
                    rs.getDate("sightDate"),
                    getSuperById(rs.getInt("superId")),
                    getLocationById(rs.getInt("locId"))
            );
            return toReturn;
        }

    }

    private static class SuperMapper implements RowMapper<Super> {

        @Override
        public Super mapRow(ResultSet rs, int i) throws SQLException {
            return new Super(
                    rs.getInt("superId"),
                    rs.getString("name"),
                    rs.getString("description")
            );
        }
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
