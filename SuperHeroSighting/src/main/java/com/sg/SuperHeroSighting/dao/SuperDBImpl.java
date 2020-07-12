/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.dao;

import com.sg.SuperHeroSighting.dto.Org;
import com.sg.SuperHeroSighting.dto.Power;
import com.sg.SuperHeroSighting.dto.Super;
import com.sg.SuperHeroSighting.exceptions.BadUpdateException;
import com.sg.SuperHeroSighting.exceptions.SuperDaoException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
public class SuperDBImpl implements SuperDao {

    @Autowired
    JdbcTemplate template;

    @Override
    public Super getSuperById(int id) {
        Super toReturn = template.queryForObject("SELECT * FROM Supers WHERE superId = ?", new SuperMapper(), id);
        toReturn.setPowers(getPowersBySuperId(id));
        toReturn.setOrgs(getOrgsBySuperId(id));
        return toReturn;
    }

    @Override
    public Super getSuperByName(String name) {
        Super toReturn = template.queryForObject("SELECT * FROM Supers WHERE name = ?", new SuperMapper(), name);
        toReturn.setPowers(getPowersBySuperId(toReturn.getId()));
        toReturn.setOrgs(getOrgsBySuperId(toReturn.getId()));
        return toReturn;
    }

    @Override
    public List<Super> getAllSupers() throws SuperDaoException {
        List<Super> allSupers = template.query("SELECT * FROM Supers", new SuperMapper());
        if (allSupers.size() == 0) {
            throw new SuperDaoException("No Supers found in database");
        }
        associatePowersToSuper(allSupers);
        associateOrgsToSuper(allSupers);
        return allSupers;
    }

    @Override
    public List<Super> getSupersByPowerId(int id) throws SuperDaoException {
        List<Super> supersByPower = template.query("SELECT * FROM Super_Powers WHERE powerId = ?", new SuperMapper(), id);
        if (supersByPower.size() == 0) {
            throw new SuperDaoException("No supers found for given power id");
        }
        associatePowersToSuper(supersByPower);
        associateOrgsToSuper(supersByPower);
        return supersByPower;
    }

    @Override
    public List<Super> getSupersByOrgId(int id) throws SuperDaoException {
        List<Super> supersByOrg = template.query("SELECT * FROM Affiliations WHERE orgId", new SuperMapper(), id);
        if (supersByOrg.size() == 0) {
            throw new SuperDaoException("No supers found for given org id");
        }
        associatePowersToSuper(supersByOrg);
        associateOrgsToSuper(supersByOrg);
        return supersByOrg;
    }

    @Override
    public Super createSuper(Super toAdd) throws BadUpdateException {
        int affectedRows = template.update("INSERT INTO Supers(name, description) VALUES(?, ?)",
                toAdd.getName(), toAdd.getDescription());
        if (affectedRows == 0) {
            throw new BadUpdateException("Failed to create new row in databse");
        }
        int newId = template.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        toAdd.setId(newId);
        return toAdd;
    }

    @Override
    public void editSuper(Super toEdit) throws BadUpdateException {
        int affectedRows = template.update("UPDATE Supers SET name = ?, description = ?, WHERE superId = ?",
                toEdit.getName(), toEdit.getDescription(), toEdit.getId());
        if (affectedRows < 1) {
            throw new BadUpdateException("No rows updated");
        }
    }

    @Override
    public void removeSuper(int id) throws BadUpdateException {
        template.update("DELETE FROM Affiliations WHERE superId = ?", id);
        template.update("DELETE FROM Super_Powers WHERE superId = ?", id);
        int affectedRows = template.update("DELETE FROM Supers WHERE superId = ?", id);
        if (affectedRows < 1) {
            throw new BadUpdateException("No rows removed");
        }
        if (affectedRows > 1) {
            throw new BadUpdateException("More than one row removed. Oops");
        }
    }

    private Set<Power> getPowersBySuperId(int id){
        return new HashSet<>(template.query("SELECT * FROM Super_Powers WHERE superId = ?", new PowerMapper(), id));
    }
    
    private Set<Org> getOrgsBySuperId(int id){
        return new HashSet<>(template.query("SELECT * FROM Affiliations WHERE superId = ?", new OrgMapper(), id));
    }
    
    private void associatePowersToSuper(List<Super> supers){
        for(Super s: supers){
            s.setPowers(getPowersBySuperId(s.getId()));
        }
    }
    
    private void associateOrgsToSuper(List<Super> supers){
        for(Super s: supers){
            s.setOrgs(getOrgsBySuperId(s.getId()));
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
    
    private static class PowerMapper implements RowMapper<Power> {

        @Override
        public Power mapRow(ResultSet rs, int i) throws SQLException {
            return new Power(rs.getInt("powerId"), rs.getString("name"));
        }

    }

    private static class OrgMapper implements RowMapper<Org> {

        @Override
        public Org mapRow(ResultSet rs, int i) throws SQLException {
            return new Org(
                    rs.getInt("orgId"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("address"),
                    rs.getString("phone")
            );
        }

        

    }
}
