/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.dto;

import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Isaia
 */
public class Super {
    
    private int id;
    private String name;
    private String description;
    private Set<Power> powers;
    private Set<Org> orgs;

    public Super(){
        
    }
    
    // used for Super Mapping
    public Super(int id, String name, String description){
        this.id = id;
        this.name = name;
        this.description = description;
    }
    
    // used for super creation
    public Super(String name, String description, Set<Power> powers, Set<Org> orgs){
        this.name = name;
        this.description = description;
        this.powers = powers;
        this.orgs = orgs;
    }
    
    // used for testing 
    public Super(int id, String name, String description, Set<Power> powers, Set<Org> orgs){
        this.id = id;
        this.name = name;
        this.description = description;
        this.powers = powers;
        this.orgs = orgs;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.id;
        hash = 47 * hash + Objects.hashCode(this.name);
        hash = 47 * hash + Objects.hashCode(this.description);
        hash = 47 * hash + Objects.hashCode(this.powers);
        hash = 47 * hash + Objects.hashCode(this.orgs);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Super other = (Super) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.powers, other.powers)) {
            return false;
        }
        if (!Objects.equals(this.orgs, other.orgs)) {
            return false;
        }
        return true;
    }

    
    
   
    
    
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the powers
     */
    public Set<Power> getPowers() {
        return powers;
    }

    /**
     * @param powers the powers to set
     */
    public void setPowers(Set<Power> powers) {
        this.powers = powers;
    }

    /**
     * @return the orgs
     */
    public Set<Org> getOrgs() {
        return orgs;
    }

    /**
     * @param orgs the orgs to set
     */
    public void setOrgs(Set<Org> orgs) {
        this.orgs = orgs;
    }
    
    
}
