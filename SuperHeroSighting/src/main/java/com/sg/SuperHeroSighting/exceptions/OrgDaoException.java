/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.exceptions;

/**
 *
 * @author Isaia
 */
public class OrgDaoException extends Exception{
    
    public OrgDaoException(String message){
        super(message);
    }
    
    public OrgDaoException(String message, Throwable cause){
        super(message, cause);
    }
}
