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
public class SightingVM {
 
    private Sighting toGet;
    private String sDate;

    /**
     * @return the toGet
     */
    public Sighting getToGet() {
        return toGet;
    }

    /**
     * @param toGet the toGet to set
     */
    public void setToGet(Sighting toGet) {
        this.toGet = toGet;
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
