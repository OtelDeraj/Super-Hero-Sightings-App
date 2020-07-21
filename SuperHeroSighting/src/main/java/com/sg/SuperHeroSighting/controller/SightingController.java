/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.controller;

import com.sg.SuperHeroSighting.dto.Location;
import com.sg.SuperHeroSighting.dto.Sighting;
import com.sg.SuperHeroSighting.dto.SightingVM;
import com.sg.SuperHeroSighting.dto.Super;
import com.sg.SuperHeroSighting.exceptions.EmptyResultException;
import com.sg.SuperHeroSighting.exceptions.InvalidEntityException;
import com.sg.SuperHeroSighting.exceptions.InvalidIdException;
import com.sg.SuperHeroSighting.service.LocationService;
import com.sg.SuperHeroSighting.service.SightingService;
import com.sg.SuperHeroSighting.service.SuperService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Isaia
 */
@Controller
public class SightingController {
    
    @Autowired
    SightingService service;
    
    @Autowired
    SuperService supServ;
    
    @Autowired
    LocationService locServ;
    
    @GetMapping("/sightings")
    public String displaySightingPage(Model pageModel){
        List<Sighting> allSightings = new ArrayList<>();
        List<Super> allSupers = new ArrayList<>();
        List<Location> allLocations = new ArrayList<>();
        try {
            allSightings = service.getAllSightings();
            allSupers = supServ.getAllSupers();
            allLocations = locServ.getAllLocations();
        } catch (EmptyResultException ex) {
        }
        
        pageModel.addAttribute("locations", allLocations);
        pageModel.addAttribute("supers", allSupers);
        pageModel.addAttribute("sightings", allSightings);
        return "sightings";
    }
    
    @GetMapping("/sighting/{id}")
    public String displaySightingDetails(@PathVariable Integer id, Model pageModel) throws InvalidEntityException, InvalidIdException{
        Sighting toDisplay = service.getSightingById(id);
        pageModel.addAttribute("sighting", toDisplay);
        return "sightdetail";
    }
    
    @PostMapping("addsighting")
    public String addSighting(SightingVM toAdd) throws InvalidIdException, InvalidEntityException{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date parsed = new Date();
        try {
            parsed = format.parse(toAdd.getsDate());
        } catch (ParseException ex) {
        }
        Super spotted = supServ.getSuperById(toAdd.getToGet().getSpottedSuper().getId());
        Location seen = locServ.getLocationById(toAdd.getToGet().getLocation().getId());
        toAdd.getToGet().setSpottedSuper(spotted);
        toAdd.getToGet().setLocation(seen);
        toAdd.getToGet().setDate(parsed);
        service.addSighting(toAdd.getToGet());
        return "redirect:/sightings";
    }
    
    @GetMapping("/editsighting/{id}")
    public String displayEditSighting(Model pageModel, @PathVariable Integer id) throws InvalidIdException{
        List<Sighting> allSightings = new ArrayList<>();
        List<Super> allSupers = new ArrayList<>();
        List<Location> allLocations = new ArrayList<>();
        Sighting toEdit = service.getSightingById(id);
        try {
            allSightings = service.getAllSightings();
            allSupers = supServ.getAllSupers();
            allLocations = locServ.getAllLocations();
        } catch (EmptyResultException ex) {
        }
        pageModel.addAttribute("sighting", toEdit);
        pageModel.addAttribute("sightings", allSightings);
        pageModel.addAttribute("supers", allSupers);
        pageModel.addAttribute("locations", allLocations);
        return "editsighting";
    }
    
    @PostMapping("editsighting")
    public String editSighting(SightingVM toEdit) throws InvalidEntityException{
        service.updateSighting(toEdit.getToGet());
        return "redirect:/sightings";
    }
    
    @GetMapping("/deletesighting/{id}")
    public String deleteSightingById(@PathVariable Integer id) throws InvalidIdException{
        service.removeSighting(id);
        return "redirect:/sightings";
    }
    
    
}
