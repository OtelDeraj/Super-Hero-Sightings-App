/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.controller;

import com.sg.SuperHeroSighting.dto.Sighting;
import com.sg.SuperHeroSighting.dto.Super;
import com.sg.SuperHeroSighting.exceptions.EmptyResultException;
import com.sg.SuperHeroSighting.exceptions.InvalidIdException;
import com.sg.SuperHeroSighting.service.SightingService;
import com.sg.SuperHeroSighting.service.SuperService;
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
public class SuperController {
    
    @Autowired
    SuperService service;
    
    @Autowired
    SightingService sightServ;
    
    @GetMapping("/supers")
    public String displaySupersPage(Model pageModel){
        List<Super> allSupers = new ArrayList<>();
        try {
            allSupers = service.getAllSupers();
        } catch (EmptyResultException ex) {
        }
        pageModel.addAttribute("supers", allSupers);
        return "supers";
    }
    
    @GetMapping("/super/{id}")
    public String displaySuperDetails(@PathVariable Integer id, Model pageModel) throws InvalidIdException{
        List<Sighting> sightingsForSuper = new ArrayList<>(); 
        Super toDisplay = service.getSuperById(id);
        try {
            sightingsForSuper = sightServ.getSightingsBySuper(toDisplay.getId());
        } catch (EmptyResultException ex) {
        }
        pageModel.addAttribute("sightings", sightingsForSuper);
        pageModel.addAttribute("super", toDisplay);
        return "superdetail";
    }
}