/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.controller;

import com.sg.SuperHeroSighting.dto.Location;
import com.sg.SuperHeroSighting.dto.Sighting;
import com.sg.SuperHeroSighting.dto.Super;
import com.sg.SuperHeroSighting.exceptions.EmptyResultException;
import com.sg.SuperHeroSighting.exceptions.InvalidEntityException;
import com.sg.SuperHeroSighting.exceptions.InvalidIdException;
import com.sg.SuperHeroSighting.service.LocationService;
import com.sg.SuperHeroSighting.service.SightingService;
import com.sg.SuperHeroSighting.service.SuperService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
    public String displaySightingPage(Model pageModel) {
        List<Sighting> allSightings = new ArrayList<>();
        List<Super> allSupers = new ArrayList<>();
        List<Location> allLocations = new ArrayList<>();
        try {
            allSightings = service.getAllSightings();
            allSupers = supServ.getAllSupers();
            allLocations = locServ.getAllLocations();
        } catch (EmptyResultException ex) {
        }
        pageModel.addAttribute("sighting", new Sighting());
        pageModel.addAttribute("locations", allLocations);
        pageModel.addAttribute("supers", allSupers);
        pageModel.addAttribute("sightings", allSightings);
        return "sightings";
    }

    @GetMapping("/sighting/{id}")
    public String displaySightingDetails(@PathVariable Integer id, Model pageModel) throws InvalidEntityException, InvalidIdException {
        Sighting toDisplay = service.getSightingById(id);
        pageModel.addAttribute("sighting", toDisplay);
        return "sightdetail";
    }

    @PostMapping("addsighting")
    public String addSighting(@Valid Sighting toAdd, BindingResult result, Model pageModel) throws InvalidIdException, InvalidEntityException {
        List<Sighting> allSightings = new ArrayList<>();
        List<Super> allSupers = new ArrayList<>();
        List<Location> allLocations = new ArrayList<>();
        LocalDate parsed = null;
        try {
            parsed = LocalDate.parse(toAdd.getsDate());
        } catch (DateTimeParseException ex) {
        }
        boolean validDate = toAdd.getsDate() != null && toAdd.getsDate().matches("^(\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$")
                && toAdd.getsDate().trim().length() == 10;

        if (validDate) {
            if (parsed.isAfter(LocalDate.now())) {
                FieldError error = new FieldError("sighting", "sDate", "Sightings cannot be in the future. Unless you are a super... In which case we ask that you sight yourself.");
                result.addError(error);
            }
        }

        Super spotted = supServ.getSuperById(toAdd.getSpottedSuper().getId());
        Location seen = locServ.getLocationById(toAdd.getLocation().getId());
        toAdd.setSpottedSuper(spotted);
        toAdd.setLocation(seen);
        toAdd.setDate(parsed);

        if (result.hasErrors()) {
            try {
                allSightings = service.getAllSightings();
                allSupers = supServ.getAllSupers();
                allLocations = locServ.getAllLocations();
            } catch (EmptyResultException ex) {
            }
            pageModel.addAttribute("sighting", toAdd);
            pageModel.addAttribute("locations", allLocations);
            pageModel.addAttribute("supers", allSupers);
            pageModel.addAttribute("sightings", allSightings);
            return "sightings";
        }
        service.addSighting(toAdd);
        return "redirect:/sightings";
    }

    @GetMapping("/editsighting/{id}")
    public String displayEditSighting(Model pageModel, @PathVariable Integer id) throws InvalidIdException {
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
    public String editSighting(@Valid Sighting toEdit, BindingResult result, Model pageModel) throws InvalidEntityException {
        List<Sighting> allSightings = new ArrayList<>();
        List<Super> allSupers = new ArrayList<>();
        List<Location> allLocations = new ArrayList<>();
        LocalDate parsed = null;
        try {
            parsed = LocalDate.parse(toEdit.getsDate());
        } catch (DateTimeParseException ex) {
        }
        boolean validDate = toEdit.getsDate() != null && toEdit.getsDate().matches("^(\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$")
                && toEdit.getsDate().trim().length() == 10;

        if (validDate) {
            if (parsed.isAfter(LocalDate.now())) {
                FieldError error = new FieldError("sighting", "sDate", "Sightings cannot be in the future. Unless you are a super... In which case we ask that you sight yourself.");
                result.addError(error);
            }
        }

        if (result.hasErrors()) {
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
        toEdit.setDate(parsed);
        service.updateSighting(toEdit);
        return "redirect:/sightings";
    }

    @GetMapping("/deletesighting/{id}")
    public String deleteSightingById(@PathVariable Integer id) throws InvalidIdException {
        service.removeSighting(id);
        return "redirect:/sightings";
    }

}
