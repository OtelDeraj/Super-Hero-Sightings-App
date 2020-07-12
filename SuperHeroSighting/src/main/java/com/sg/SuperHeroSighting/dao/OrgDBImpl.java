/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.dao;

import com.sg.SuperHeroSighting.dto.Org;
import com.sg.SuperHeroSighting.dto.Super;
import com.sg.SuperHeroSighting.exceptions.BadUpdateException;
import com.sg.SuperHeroSighting.exceptions.OrgDaoException;
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
public class OrgDBImpl implements OrgDao {

    @Autowired
    JdbcTemplate template;

    @Override
    public Org getOrgById(int id) {
        Org toReturn = template.queryForObject("SELECT * FROM Orgs WHERE orgId = ?", new OrgMapper(), id);
        toReturn.setSupers(getSupersByOrgId(id));
        return toReturn;
    }

    @Override
    public Org getOrgByName(String name) {
        Org toReturn = template.queryForObject("SELECT * FROM Orgs WHERE name = ?", new OrgMapper(), name);
        toReturn.setSupers(getSupersByOrgId(toReturn.getId()));
        return toReturn;
    }

    @Override
    public List<Org> getAllOrgs() throws OrgDaoException {
        List<Org> allOrgs = template.query("SELECT * FROM Orgs", new OrgMapper());
        if (allOrgs.size() == 0) {
            throw new OrgDaoException("No Orgs found");
        }
        associateSupersToOrg(allOrgs);
        return allOrgs;
    }

    @Override
    public List<Org> getOrgsForSuperId(int id) throws OrgDaoException {
        List<Org> orgsForSuper = template.query("SELECT * FROM Affiliations WHERE superId = ?", new OrgMapper(), id);
        if (orgsForSuper.size() == 0) {
            throw new OrgDaoException("No orgs found for given super id");
        }
        associateSupersToOrg(orgsForSuper);
        return orgsForSuper;
    }

    @Override
    public Org createOrg(Org toAdd) throws BadUpdateException {
        int affectedRows = template.update("INSERT INTO Orgs(name, description, address, phone) "
                + "VALUES(?, ?, ?, ?)", toAdd.getName(), toAdd.getDescription(),
                toAdd.getAddress(), toAdd.getPhone());
        if (affectedRows < 1) {
            throw new BadUpdateException("Failed to add Org to database");
        }
        int newId = template.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        toAdd.setId(newId);
        return toAdd;
    }

    @Override
    public void editOrg(Org toEdit) throws BadUpdateException {
        int affectedRows = template.update("UPDATE Orgs SET name = ?, description = ?, address = ?, phone = ? WHERE orgId = ?",
                toEdit.getName(), toEdit.getDescription(), toEdit.getAddress(), toEdit.getPhone(), toEdit.getId());
        if (affectedRows < 1) {
            throw new BadUpdateException("No rows affected by update");
        }
    }

    @Override
    public void removeOrg(int id) throws BadUpdateException {
        template.update("DELETE FROM Affiliations WHERE orgId = ?", id);
        int affectedRows = template.update("DELETE FROM Orgs WHERE orgId = ?", id);
        if (affectedRows < 1) {
            throw new BadUpdateException("No rows removed");
        }
        if (affectedRows > 1) {
            throw new BadUpdateException("More than one row removed. I have a bad feeling about this.");
        }
    }

    private Set<Super> getSupersByOrgId(int id) {
        return new HashSet<>(template.query("SELECT * FROM Affiliations WHERE orgId = ?", new SuperMapper(), id));
    }

    private void associateSupersToOrg(List<Org> orgs) {
        for (Org o : orgs) {
            o.setSupers(getSupersByOrgId(o.getId()));
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

}
