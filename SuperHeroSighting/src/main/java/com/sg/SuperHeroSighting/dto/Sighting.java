/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.dto;

import java.util.Date;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

/**
 *
 * @author Isaia
 */
public class Sighting {
    
    private int id;
//    @Pattern(regexp="^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$")
    @NotBlank(message="Please enter a date for this sighting")
    @Past(message="Sighting date must be in the past")
    private Date date;
    @NotBlank(message="Please select which super was spotted")
    private Super spottedSuper;
    @NotBlank(message="Please select a location for this sighting")
    private Location location;
    
    public Sighting(){
        
    }
    
    // creation
    public Sighting(Date date, Super spottedSuper, Location location){
        this.date = date;
        this.spottedSuper = spottedSuper;
        this.location = location;
    }
    
    // testing & mapping
    public Sighting(int id, Date date, Super spottedSuper, Location location){
        this.id = id;
        this.date = date;
        this.spottedSuper = spottedSuper;
        this.location = location;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.id;
        hash = 67 * hash + Objects.hashCode(this.date);
        hash = 67 * hash + Objects.hashCode(this.spottedSuper);
        hash = 67 * hash + Objects.hashCode(this.location);
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
        final Sighting other = (Sighting) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.spottedSuper, other.spottedSuper)) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
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
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the spottedSuper
     */
    public Super getSpottedSuper() {
        return spottedSuper;
    }

    /**
     * @param spottedSuper the spottedSuper to set
     */
    public void setSpottedSuper(Super spottedSuper) {
        this.spottedSuper = spottedSuper;
    }

    /**
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(Location location) {
        this.location = location;
    }
    
    
}
