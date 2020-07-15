/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.dto;

import java.awt.Point;
import java.util.Objects;
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
    private Coord coord; // coord object holds lat and lon values
    
    public Location(){
        
    }
    
    // copy constructor
    public Location(Location that){
        this.id = that.id;
        this.name = that.name;
        this.description = that.description;
        this.address = that.address;
        this.coord = that.coord;
    }
    
    // use for creation
    public Location(String name, String description, String address, Coord coord){
        this.name = name;
        this.description = description;
        this.address = address;
        this.coord = coord;
    }
    
    // use for testing
    public Location(int id, String name, String description, String address, Coord coord){
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.coord = coord;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.id;
        hash = 67 * hash + Objects.hashCode(this.name);
        hash = 67 * hash + Objects.hashCode(this.description);
        hash = 67 * hash + Objects.hashCode(this.address);
        hash = 67 * hash + Objects.hashCode(this.coord);
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
        if (!Objects.equals(this.coord, other.coord)) {
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
     * @return the coord
     */
    public Coord getCoord() {
        return coord;
    }

    /**
     * @param coord the coord to set
     */
    public void setCoord(Coord coord) {
        this.coord = coord;
    }
    
}
