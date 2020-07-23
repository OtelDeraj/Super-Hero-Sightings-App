/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.dto;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author Isaia
 */
public class Sighting {
    
    private int id;
    private LocalDate date;
    private Super spottedSuper;
    private Location location;
//    @Pattern(regexp = "^(\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$", message = "Date improperly formatted")
    @NotBlank(message = "Date is required")
    @Size(min = 10, max = 10, message = "Date must be 10 characters")
    private String sDate;
    
    public Sighting(){
        
    }
    
    // creation
    public Sighting(LocalDate date, Super spottedSuper, Location location){
        this.date = date;
        this.spottedSuper = spottedSuper;
        this.location = location;
    }
    
    // testing & mapping
    public Sighting(int id, LocalDate date, Super spottedSuper, Location location){
        this.id = id;
        this.date = date;
        this.spottedSuper = spottedSuper;
        this.location = location;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.id;
        hash = 29 * hash + Objects.hashCode(this.date);
        hash = 29 * hash + Objects.hashCode(this.spottedSuper);
        hash = 29 * hash + Objects.hashCode(this.location);
        hash = 29 * hash + Objects.hashCode(this.sDate);
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
        if (!Objects.equals(this.sDate, other.sDate)) {
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
    
    /**
     * @return the sDate
     */
    public String getsDate() {
        return sDate;
    }

    /**
     * @param sDate the sDate to set
     */
    public void setsDate(String sDate) {
        this.sDate = sDate;
    }
    
}
