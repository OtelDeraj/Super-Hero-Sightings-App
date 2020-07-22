/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.controller;

import com.sg.SuperHeroSighting.dto.Sighting;
import com.sg.SuperHeroSighting.exceptions.EmptyResultException;
import com.sg.SuperHeroSighting.service.SightingService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Isaia
 */
@Controller
public class HomeController {
    
    @Autowired
    SightingService service;
    
    @GetMapping("/")
    public String displayHome(Model pageModel){
        List<Sighting> lastTen = new ArrayList<>();
        try {
            lastTen = service.getLastTenSightings();
        } catch (EmptyResultException ex) {
        }
        pageModel.addAttribute("sightings", lastTen);
        
        return "home";
    }
    
}
