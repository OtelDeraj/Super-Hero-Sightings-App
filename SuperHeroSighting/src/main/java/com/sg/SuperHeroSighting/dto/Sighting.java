/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.dto;

import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author Isaia
 */
public class Sighting {
    
    private int id;
    private LocalDate date;
    private Super spottedSuper;
    private Location location;
    
    public Sighting(){
        
    }
    
    public Sighting(LocalDate date, Super spottedSuper, Location location){
        this.date = date;
        this.spottedSuper = spottedSuper;
        this.location = location;
    }
    
    public Sighting(int id, LocalDate date, Super spottedSuper, Location location){
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
    public LocalDate getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(LocalDate date) {
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
