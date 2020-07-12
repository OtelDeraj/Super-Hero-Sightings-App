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
public class Org {
    
    private int id;
    private String name;
    private String description;
    private String address;
    private String phone;
    private Set<Super> supers;
    
    
    public Org(){
        
    }
    
    // used for Org Mapping
    public Org(int id, String name, String description, String address, String phone){
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.phone = phone;
    }
    
    // used for Org creation
    public Org(String name, String description, String address, String phone, Set<Super> supers){
        this.name = name;
        this.description = description;
        this.address = address;
        this.phone = phone;
        this.supers = supers;
    }
    
    // used for testing
    public Org(int id, String name, String description, String address, String phone, Set<Super> supers){
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.phone = phone;
        this.supers = supers;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.id;
        hash = 17 * hash + Objects.hashCode(this.name);
        hash = 17 * hash + Objects.hashCode(this.description);
        hash = 17 * hash + Objects.hashCode(this.address);
        hash = 17 * hash + Objects.hashCode(this.phone);
        hash = 17 * hash + Objects.hashCode(this.supers);
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
        final Org other = (Org) obj;
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
        if (!Objects.equals(this.phone, other.phone)) {
            return false;
        }
        if (!Objects.equals(this.supers, other.supers)) {
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
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the supers
     */
    public Set<Super> getSupers() {
        return supers;
    }

    /**
     * @param supers the supers to set
     */
    public void setSupers(Set<Super> supers) {
        this.supers = supers;
    }
}
