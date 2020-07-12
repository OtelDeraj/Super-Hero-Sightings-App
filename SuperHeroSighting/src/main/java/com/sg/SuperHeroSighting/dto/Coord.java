/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author Isaia
 */
public class Coord {
    
    private BigDecimal lat;
    private BigDecimal lon;
    
    public Coord(){
        
    }
    
    public Coord(BigDecimal lat, BigDecimal lon){
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.lat);
        hash = 59 * hash + Objects.hashCode(this.lon);
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
        final Coord other = (Coord) obj;
        if (!Objects.equals(this.lat, other.lat)) {
            return false;
        }
        if (!Objects.equals(this.lon, other.lon)) {
            return false;
        }
        return true;
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
