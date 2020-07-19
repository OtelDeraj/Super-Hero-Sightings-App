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
import java.util.logging.Level;
import java.util.logging.Logger;
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
        List<Sighting> allSightings = new ArrayList<>();
        try {
            allSightings = service.getAllSightings();
        } catch (EmptyResultException ex) {
        }
        int recentSightingsCount = 10;
        if(allSightings.size() < recentSightingsCount){
            recentSightingsCount = allSightings.size();
        }
        
        Sighting[] lastTen = new Sighting[recentSightingsCount];
        
        for(int i = 0; i < 10; i++){
            if(i < allSightings.size()){
                lastTen[i] = allSightings.get(allSightings.size() - 1 - i);
            } else {
                break;
            }
        }
        
        pageModel.addAttribute("sightings", lastTen);
        
        return "home";
    }
    
}
