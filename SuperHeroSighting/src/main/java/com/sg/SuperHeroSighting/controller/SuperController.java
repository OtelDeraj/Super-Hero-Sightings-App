/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Isaia
 */
@Controller
public class SuperController {
    
    @GetMapping("/supers")
    public String displaySupersPage(){
        return "supers";
    }
    
}
