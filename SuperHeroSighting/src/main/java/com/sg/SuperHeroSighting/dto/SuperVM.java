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
public class SuperVM {
    
    private Super toGet;
    private Power[] powers;
    private Org[] orgs;

    /**
     * @return the toGet
     */
    public Super getToGet() {
        return toGet;
    }

    /**
     * @param toGet the toGet to set
     */
    public void setToGet(Super toGet) {
        this.toGet = toGet;
    }

    /**
     * @return the powers
     */
    public Power[] getPowers() {
        return powers;
    }

    /**
     * @param powers the powers to set
     */
    public void setPowers(Power[] powers) {
        this.powers = powers;
    }

    /**
     * @return the orgs
     */
    public Org[] getOrgs() {
        return orgs;
    }

    /**
     * @param orgs the orgs to set
     */
    public void setOrgs(Org[] orgs) {
        this.orgs = orgs;
    }
}
