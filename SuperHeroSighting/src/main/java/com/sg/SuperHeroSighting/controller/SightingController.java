/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.controller;

import com.sg.SuperHeroSighting.dto.Sighting;
import com.sg.SuperHeroSighting.exceptions.EmptyResultException;
import com.sg.SuperHeroSighting.exceptions.InvalidEntityException;
import com.sg.SuperHeroSighting.exceptions.InvalidIdException;
import com.sg.SuperHeroSighting.service.SightingService;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author Isaia
 */
@Controller
public class SightingController {
    
    @Autowired
    SightingService service;
    
    @GetMapping("/sightings")
    public String displaySightingPage(Model pageModel){
        List<Sighting> allSightings = new ArrayList<>();
        try {
            allSightings = service.getAllSightings();
        } catch (EmptyResultException ex) {
        }
        
        pageModel.addAttribute("sightings", allSightings);
        return "sightings";
    }
    
    @GetMapping("/sighting/{id}")
    public String displaySightingDetails(@PathVariable Integer id, Model pageModel) throws InvalidEntityException, InvalidIdException{
        Sighting toDisplay = service.getSightingById(id);
        pageModel.addAttribute("sighting", toDisplay);
        return "sightdetail";
    }
}
