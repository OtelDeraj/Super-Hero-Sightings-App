/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.controller;

import com.sg.SuperHeroSighting.dto.Location;
import com.sg.SuperHeroSighting.dto.Sighting;
import com.sg.SuperHeroSighting.exceptions.EmptyResultException;
import com.sg.SuperHeroSighting.exceptions.InvalidIdException;
import com.sg.SuperHeroSighting.service.LocationService;
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
public class LocationController {
    
    @Autowired
    LocationService service;
    
    @Autowired
    SightingService sightServ;
    
    @GetMapping("/locations")
    public String displayLocations(Model pageModel){
        return "locations";
    }
    
    @GetMapping("/location/{id}")
    public String displayLocationDetails(@PathVariable Integer id, Model pageModel) throws InvalidIdException{
        List<Sighting> sightingsForLocation = new ArrayList<>();
        Location toDisplay = service.getLocationById(id);
        try {
            sightingsForLocation = sightServ.getSightingsByLoc(toDisplay.getId());
        } catch (EmptyResultException ex) {
        }
        pageModel.addAttribute("sightings", sightingsForLocation);
        pageModel.addAttribute("location", toDisplay);
        return "locationdetail";
    }
}
