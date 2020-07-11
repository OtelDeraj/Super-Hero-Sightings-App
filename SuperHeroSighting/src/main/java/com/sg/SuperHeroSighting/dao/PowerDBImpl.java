/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.dao;

import com.sg.SuperHeroSighting.dto.Power;
import com.sg.SuperHeroSighting.exceptions.BadUpdateException;
import com.sg.SuperHeroSighting.exceptions.PowerDaoException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
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
    public Power getPowerById(int id) {
        return template.queryForObject("SELECT * FROM Powers WHERE powerId = ?", new PowerMapper(), id);
    }

    @Override
    public Power getPowerByName(String name) {
        return template.queryForObject("SELECT * FROM Powers WHERE name = ?", new PowerMapper(), name);
    }

    @Override
    public List<Power> getAllPowers() throws PowerDaoException {
        List<Power> allPowers = template.query("SELECT * FROM Powers", new PowerMapper());
        if (allPowers.size() == 0) {
            throw new PowerDaoException("No powers found");
        }
        return allPowers;
    }

    @Override
    public List<Power> getPowersForSuperId(int id) throws PowerDaoException {
        List<Power> powers = template.query("SELECT * FROM Super_Powers WHERE superId = ?", new PowerMapper(), id);
        if (powers.size() == 0) {
            throw new PowerDaoException("No powers found for that super id");
        }
        return powers;
    }

    @Override
    public Power addPower(Power toAdd) throws PowerDaoException, BadUpdateException {
        int affectedRows = template.update("INSERT INTO Powers(name) VALUES(?)", toAdd.getName());
        if(affectedRows < 1) throw new BadUpdateException("Failed to add power to database");
        int newId = template.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        toAdd.setId(newId);
        return toAdd;
    }

    @Override
    public void editPower(Power toEdit) throws BadUpdateException {
        int affectedRows = template.update("UPDATE Powers SET name = ? WHERE powerId = ?", toEdit.getName(), toEdit.getId());
        if(affectedRows < 1) throw new BadUpdateException("No rows updated");
    }

    @Override
    public void removePower(int id) throws BadUpdateException {
        template.update("DELETE From Super_Powers WHERE powerId = ?", id);
        int affectedRows = template.update("DELETE From Powers WHERE powerId = ?", id);
        if(affectedRows < 1) throw new BadUpdateException("No powers deleted");
        if(affectedRows > 1) throw new BadUpdateException("More than one power deleted. This is a problem.");
    }

    private static class PowerMapper implements RowMapper<Power> {

        @Override
        public Power mapRow(ResultSet rs, int i) throws SQLException {
            return new Power(rs.getString("name"));
        }

    }

}