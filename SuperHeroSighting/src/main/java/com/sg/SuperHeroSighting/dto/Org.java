/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.dto;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author Isaia
 */
public class Org {
    
    private int id;
    @NotBlank(message="Please enter a name for this organization")
    @Size(max=50, message="Name must be 50 characters or less")
    private String name;
    @NotBlank(message="Please enter a description for this organization")
    @Size(max=255, message="Please describe this organization in 255 characters or less")
    private String description;
    @NotBlank(message="Please enter an address for this organization")
    @Size(max=60, message="Address must be 60 characters or less")
    private String address;
    @NotBlank(message="Please provide a phone number for this organization")
    @Size(max=15, message="Phone number must be 15 characters or less")
    @Pattern(regexp="^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$", message="Pattern must match XXX-XXX-XXXX")
    private String phone;
    private Set<Super> supers;
//    @NotNull(message="Please select one or more Super(s)")
//    private Integer[] superIds;
    
    
    public Org(){
        
    }
    
    // copy constructor
    public Org(Org that){
        this.id = that.id;
        this.name = that.name;
        this.description = that.description;
        this.address = that.address;
        this.phone = that.phone;
        this.supers = that.supers;
    }
    
    // used for Org Mapping and SuperService Tests
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
        int hash = 3;
        hash = 31 * hash + this.id;
        hash = 31 * hash + Objects.hashCode(this.name);
        hash = 31 * hash + Objects.hashCode(this.description);
        hash = 31 * hash + Objects.hashCode(this.address);
        hash = 31 * hash + Objects.hashCode(this.phone);
        hash = 31 * hash + Objects.hashCode(this.supers);
//        hash = 31 * hash + Arrays.deepHashCode(this.superIds);
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
//        if (!Arrays.deepEquals(this.superIds, other.superIds)) {
//            return false;
//        }
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
    
    /**
     * @return the supers
     */
//    public Integer[] getSuperIds() {
//        return superIds;
//    }
//
//    /**
//     * @param superIds
//     */
//    public void setSuperIds(Integer[] superIds) {
//        this.superIds = superIds;
//    }
}
