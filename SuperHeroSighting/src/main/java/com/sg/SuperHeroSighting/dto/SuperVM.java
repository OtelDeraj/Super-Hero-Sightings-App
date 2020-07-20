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
    private Integer[] powerIds;
    private Integer[] orgIds;

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
    public Integer[] getPowerIds() {
        return powerIds;
    }

    /**
     * @param powers the powers to set
     */
    public void setPowerIds(Integer[] powerIds) {
        this.powerIds = powerIds;
    }

    /**
     * @return the orgs
     */
    public Integer[] getOrgIds() {
        return orgIds;
    }

    /**
     * @param orgs the orgs to set
     */
    public void setOrgIds(Integer[] orgIds) {
        this.orgIds = orgIds;
    }
}
