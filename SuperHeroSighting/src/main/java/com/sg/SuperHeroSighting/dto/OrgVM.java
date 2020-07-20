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
    private Integer[] superIds;

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
    public Integer[] getSuperIds() {
        return superIds;
    }

    /**
     * @param superIds
     */
    public void setSuperIds(Integer[] superIds) {
        this.superIds = superIds;
    }
    
}
