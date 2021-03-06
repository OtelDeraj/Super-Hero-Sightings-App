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
import com.sg.SuperHeroSighting.exceptions.DuplicateNameException;
import com.sg.SuperHeroSighting.exceptions.InvalidEntityException;
import com.sg.SuperHeroSighting.exceptions.SuperDaoException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
public class SuperDBImpl implements SuperDao {

    @Autowired
    JdbcTemplate template;

    @Override
    public Super getSuperById(int id) throws SuperDaoException {
        Super toReturn = null;
        try{
        toReturn = template.queryForObject("SELECT * FROM Supers WHERE superId = ?", new SuperMapper(), id);
        } catch(EmptyResultDataAccessException ex){
            throw new SuperDaoException("Get Super by id failed");
        }
        if(toReturn == null) throw new SuperDaoException("No Super found for given ID");
        toReturn.setPowers(getPowersBySuperId(id));
        toReturn.setOrgs(getOrgsBySuperId(id));
        return toReturn;
    }

    @Override
    public Super getSuperByName(String name) throws SuperDaoException {
        Super toReturn = null;
        try{
        toReturn = template.queryForObject("SELECT * FROM Supers WHERE name = ?", new SuperMapper(), name);
        } catch(EmptyResultDataAccessException ex){
            throw new SuperDaoException("Get Super by name failed");
        }
        if(toReturn == null) throw new SuperDaoException("No Super found for given Name");
        toReturn.setPowers(getPowersBySuperId(toReturn.getId()));
        toReturn.setOrgs(getOrgsBySuperId(toReturn.getId()));
        return toReturn;
    }

    @Override
    public List<Super> getAllSupers() throws SuperDaoException {
        List<Super> allSupers = template.query("SELECT * FROM Supers", new SuperMapper());
        if (allSupers.isEmpty()) {
            throw new SuperDaoException("No Supers found in database");
        }
        associatePowersToSuper(allSupers);
        associateOrgsToSuper(allSupers);
        return allSupers;
    }

    @Override
    public List<Super> getSupersByPowerId(int id) throws SuperDaoException {
        List<Super> supersByPower = template.query("SELECT * FROM Supers su INNER JOIN Super_Powers sp ON su.superId = sp.superId WHERE sp.powerId = ?", new SuperMapper(), id);
        if (supersByPower.isEmpty()) {
            throw new SuperDaoException("No supers found for given power id");
        }
        associatePowersToSuper(supersByPower);
        associateOrgsToSuper(supersByPower);
        return supersByPower;
    }

    @Override
    public List<Super> getSupersByOrgId(int id) throws SuperDaoException {
        List<Super> supersByOrg = template.query("SELECT * FROM Supers su INNER JOIN Affiliations af ON su.superId = af.superId WHERE af.orgId = ?", new SuperMapper(), id);
        if (supersByOrg.isEmpty()) {
            throw new SuperDaoException("No supers found for given org id");
        }
        associatePowersToSuper(supersByOrg);
        associateOrgsToSuper(supersByOrg);
        return supersByOrg;
    }

    @Override
    public Super createSuper(Super toAdd) throws BadUpdateException, InvalidEntityException, DuplicateNameException {
        validateSuperData(toAdd);
        try{
        int affectedRows = template.update("INSERT INTO Supers(name, description) VALUES(?, ?)",
                toAdd.getName(), toAdd.getDescription());
        if (affectedRows == 0) {
            throw new BadUpdateException("Failed to create new row in databse");
        }
        } catch(DuplicateKeyException ex){
            throw new DuplicateNameException("Given name already exists.");
        }
        int newId = template.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        toAdd.setId(newId);
        for(Org o : toAdd.getOrgs()){
            template.update("INSERT INTO Affiliations(orgId, superId) VALUES(?, ?)", o.getId(), toAdd.getId());
        }
        for(Power p: toAdd.getPowers()){
            template.update("INSERT INTO Super_Powers(powerId, superId) VALUES(?, ?)", p.getId(), toAdd.getId());
        }
        return toAdd;
    }

    @Override
    public void editSuper(Super toEdit) throws BadUpdateException, InvalidEntityException, DuplicateNameException {
        validateSuperData(toEdit);
        try{
        int affectedRows = template.update("UPDATE Supers SET name = ?, description = ? WHERE superId = ?",
                toEdit.getName(), toEdit.getDescription(), toEdit.getId());
        if (affectedRows < 1) {
            throw new BadUpdateException("No rows updated");
        }
        } catch(DuplicateKeyException ex){
            throw new DuplicateNameException("Given name already exists");
        }
        template.update("DELETE FROM Affiliations WHERE superId = ?", toEdit.getId());
        for(Org o : toEdit.getOrgs()){
            template.update("INSERT INTO Affiliations(orgId, superId) VALUES(?, ?)", o.getId(), toEdit.getId());
        }
        template.update("DELETE FROM Super_Powers WHERE superId = ?", toEdit.getId());
        for(Power p: toEdit.getPowers()){
            template.update("INSERT INTO Super_Powers(powerId, superId) VALUES(?, ?)", p.getId(), toEdit.getId());
        }
    }

    @Override
    public void removeSuper(int id) throws BadUpdateException {
        template.update("DELETE FROM Affiliations WHERE superId = ?", id);
        template.update("DELETE FROM Super_Powers WHERE superId = ?", id);
        template.update("DELETE FROM Sightings WHERE superId = ?", id);
        int affectedRows = template.update("DELETE FROM Supers WHERE superId = ?", id);
        if (affectedRows < 1) {
            throw new BadUpdateException("No rows removed");
        }
        if (affectedRows > 1) {
            throw new BadUpdateException("More than one row removed. Oops");
        }
    }

    private Set<Power> getPowersBySuperId(int id){
        return new HashSet<>(template.query("SELECT * FROM Powers pw INNER JOIN Super_Powers sp ON pw.powerId = sp.powerId WHERE sp.superId = ?",
                new PowerMapper(), id));
    }
    
    private Set<Org> getOrgsBySuperId(int id){
        return new HashSet<>(template.query("SELECT * FROM Orgs og INNER JOIN Affiliations af ON og.orgId = af.orgId WHERE af.superId = ?",
                new OrgMapper(), id));
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
    
    private void validateSuperData(Super s) throws InvalidEntityException{
        if(s == null) throw new InvalidEntityException("Super object cannot be null");
        if(s.getName() == null || s.getDescription() == null || s.getOrgs() == null || s.getPowers() == null){
            throw new InvalidEntityException("Super fields cannot be null");
        }
        if(s.getName().trim().length() > 30) throw new InvalidEntityException("Super name must be 30 characters or less");
        if(s.getDescription().trim().length() > 255) throw new InvalidEntityException("Super description must be 255 characters or less");
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
