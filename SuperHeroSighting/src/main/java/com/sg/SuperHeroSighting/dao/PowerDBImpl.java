/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.dao;

import com.sg.SuperHeroSighting.dto.Org;
import com.sg.SuperHeroSighting.dto.Power;
import com.sg.SuperHeroSighting.exceptions.BadUpdateException;
import com.sg.SuperHeroSighting.exceptions.DuplicateNameException;
import com.sg.SuperHeroSighting.exceptions.InvalidEntityException;
import com.sg.SuperHeroSighting.exceptions.PowerDaoException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
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
public class PowerDBImpl implements PowerDao {

    @Autowired
    JdbcTemplate template;

    @Override
    public Power getPowerById(int id) throws PowerDaoException {
        try{
        return template.queryForObject("SELECT * FROM Powers WHERE powerId = ?", new PowerMapper(), id);
        } catch(EmptyResultDataAccessException ex){
            throw new PowerDaoException("Get Power by id failed");
        }
    }

    @Override
    public Power getPowerByName(String name) throws PowerDaoException {
        try {
            return template.queryForObject("SELECT * FROM Powers WHERE name = ?", new PowerMapper(), name);
        } catch (EmptyResultDataAccessException ex) {
            throw new PowerDaoException("Get Power by name failed");
        }
    }

    @Override
    public List<Power> getAllPowers() throws PowerDaoException {
        List<Power> allPowers = template.query("SELECT * FROM Powers", new PowerMapper());
        if (allPowers.isEmpty()) {
            throw new PowerDaoException("No powers found");
        }
        return allPowers;
    }

    @Override
    public List<Power> getPowersForSuperId(int id) throws PowerDaoException {
        List<Power> powers = template.query("SELECT * FROM Powers pw INNER JOIN Super_Powers sp ON pw.powerId = sp.powerId WHERE sp.superId = ?", new PowerMapper(), id);
        if (powers.isEmpty()) {
            throw new PowerDaoException("No powers found for that super id");
        }
        return powers;
    }

    @Override
    public Power addPower(Power toAdd) throws PowerDaoException, BadUpdateException, InvalidEntityException, DuplicateNameException {
        validatePowerData(toAdd);
        try {
            int affectedRows = template.update("INSERT INTO Powers(name) VALUES(?)", toAdd.getName());
            if (affectedRows < 1) {
                throw new BadUpdateException("Failed to add power to database");
            }
        } catch (DuplicateKeyException ex) {
            throw new DuplicateNameException("Given name already exists.");
        }
        int newId = template.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        toAdd.setId(newId);
        return toAdd;
    }

    @Override
    public void editPower(Power toEdit) throws BadUpdateException, InvalidEntityException, DuplicateNameException {
        validatePowerData(toEdit);
        try {
            int affectedRows = template.update("UPDATE Powers SET name = ? WHERE powerId = ?", toEdit.getName(), toEdit.getId());
            if (affectedRows < 1) {
                throw new BadUpdateException("No rows updated");
            }
        } catch (DuplicateKeyException ex) {
            throw new DuplicateNameException("Given name already exists.");
        }
    }

    @Override
    public void removePower(int id) throws BadUpdateException {
        template.update("DELETE From Super_Powers WHERE powerId = ?", id);
        int affectedRows = template.update("DELETE From Powers WHERE powerId = ?", id);
        if (affectedRows < 1) {
            throw new BadUpdateException("No powers deleted");
        }
        if (affectedRows > 1) {
            throw new BadUpdateException("More than one power deleted. This is a problem.");
        }
    }

    private void validatePowerData(Power p) throws InvalidEntityException {
        if (p == null) {
            throw new InvalidEntityException("Power object cannot be null");
        }
        if (p.getName() == null) {
            throw new InvalidEntityException("Power fields cannot be null");
        }
    }

    private static class PowerMapper implements RowMapper<Power> {

        @Override
        public Power mapRow(ResultSet rs, int i) throws SQLException {
            return new Power(rs.getInt("powerId"), rs.getString("name"));
        }

    }

}
