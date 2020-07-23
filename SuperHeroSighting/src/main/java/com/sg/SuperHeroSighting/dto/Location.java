/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.dto;

import java.awt.Point;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author Isaia
 */
public class Location {
    
    private int id;
    @NotBlank(message="Please enter a location name")
    @Size(max=50, message="Name cannot be more than 50 characters long")
    private String name;
    @NotBlank(message="Please enter a location description")
    @Size(max=255, message="Please describe the hero in 255 characters or less")
    private String description;
    @NotBlank(message="Please enter an address")
    @Size(max=60, message="Address cannot be more than 60 characters long")
    private String address;
//    @NotBlank(message="Please enter a latitude")
    @DecimalMax("90.00000")
    @DecimalMin("-90.00000")
    private BigDecimal lat;
    
//    @NotBlank(message="Please enter a longitude")
    @DecimalMax("180.00000")
    @DecimalMin("-180.00000")
    private BigDecimal lon;
    
    
    public Location(){
        
    }
    
    // copy constructor
    public Location(Location that){
        this.id = that.id;
        this.name = that.name;
        this.description = that.description;
        this.address = that.address;
        this.lat = that.lat;
        this.lon = that.lon;
    }
    
    // use for creation
    public Location(String name, String description, String address, BigDecimal lat, BigDecimal lon){
        this.name = name;
        this.description = description;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
    }
    
    // use for testing
    public Location(int id, String name, String description, String address, BigDecimal lat, BigDecimal lon){
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + this.id;
        hash = 19 * hash + Objects.hashCode(this.name);
        hash = 19 * hash + Objects.hashCode(this.description);
        hash = 19 * hash + Objects.hashCode(this.address);
        hash = 19 * hash + Objects.hashCode(this.lat);
        hash = 19 * hash + Objects.hashCode(this.lon);
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
        final Location other = (Location) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.lat, other.lat)) {
            return false;
        }
        if (!Objects.equals(this.lon, other.lon)) {
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
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the lat
     */
    public BigDecimal getLat() {
        return lat;
    }

    /**
     * @param lat the lat to set
     */
    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    /**
     * @return the lon
     */
    public BigDecimal getLon() {
        return lon;
    }

    /**
     * @param lon the lon to set
     */
    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }

    
    
}
