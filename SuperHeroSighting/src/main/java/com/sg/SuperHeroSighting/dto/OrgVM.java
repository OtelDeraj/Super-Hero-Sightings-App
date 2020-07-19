/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.dto;

/**
 *
 * @author Isaia
 */
public class OrgVM {
    
    private Org toGet;
    private Super[] supers;

    /**
     * @return the toGet
     */
    public Org getToGet() {
        return toGet;
    }

    /**
     * @param toGet the toGet to set
     */
    public void setToGet(Org toGet) {
        this.toGet = toGet;
    }

    /**
     * @return the supers
     */
    public Super[] getSupers() {
        return supers;
    }

    /**
     * @param supers the supers to set
     */
    public void setSupers(Super[] supers) {
        this.supers = supers;
    }
    
}
