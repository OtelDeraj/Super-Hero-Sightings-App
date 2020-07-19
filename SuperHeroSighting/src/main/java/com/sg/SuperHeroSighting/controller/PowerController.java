/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author Isaia
 */
@Controller
public class PowerController {
    
    @GetMapping("/powers")
    public String displayPowersPage(){
        return "powers";
    }
    
    @GetMapping("/power/{id}")
    public String displayPowerDetails(@PathVariable Integer id, Model pageModel){
        
        
        return "powerdetail";
    }
}
