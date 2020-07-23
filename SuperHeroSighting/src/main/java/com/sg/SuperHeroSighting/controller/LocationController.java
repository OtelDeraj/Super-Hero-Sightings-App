/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.controller;

import com.sg.SuperHeroSighting.dto.Coord;
import com.sg.SuperHeroSighting.dto.Location;
import com.sg.SuperHeroSighting.dto.Sighting;
import com.sg.SuperHeroSighting.exceptions.DuplicateNameException;
import com.sg.SuperHeroSighting.exceptions.EmptyResultException;
import com.sg.SuperHeroSighting.exceptions.InvalidEntityException;
import com.sg.SuperHeroSighting.exceptions.InvalidIdException;
import com.sg.SuperHeroSighting.exceptions.InvalidNameException;
import com.sg.SuperHeroSighting.service.LocationService;
import com.sg.SuperHeroSighting.service.SightingService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
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
public class LocationController {

    @Autowired
    LocationService service;

    @Autowired
    SightingService sightServ;

    @GetMapping("/locations")
    public String displayLocations(Model pageModel) {
        List<Location> allLocations = new ArrayList<>();
        try {
            allLocations = service.getAllLocations();
        } catch (EmptyResultException ex) {
        }
        pageModel.addAttribute("location", new Location());
        pageModel.addAttribute("locations", allLocations);
        return "locations";
    }

    @GetMapping("/location/{id}")
    public String displayLocationDetails(@PathVariable Integer id, Model pageModel) throws InvalidIdException {
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

    @PostMapping("addlocation")
    public String addLocation(@Valid Location toAdd, BindingResult result, HttpServletRequest request, Model pageModel) {
        List<Location> allLocations = new ArrayList<>();
        String latString = request.getParameter("latString");
        String lonString = request.getParameter("lonString");
        if (latString.isBlank()) {
            FieldError latError = new FieldError("location", "lat", "Please enter a latitude");
            result.addError(latError);
        } else {
            try {
                BigDecimal lat = new BigDecimal(latString);
                toAdd.setLat(lat);
                if(toAdd.getLat().compareTo(new BigDecimal("90.00000")) == 1 || toAdd.getLat().compareTo(new BigDecimal("-90.00000")) == -1){
                    FieldError illegalLon = new FieldError("location", "lat", "Latitude must be between 90 and -90");
                    result.addError(illegalLon);
                }
            } catch (NumberFormatException ex) {
                FieldError latFormat = new FieldError("location", "lat", "Please enter a NUMBER between -90.00000 and 90.00000");
                result.addError(latFormat);
            }
        }
        if (lonString.isBlank()) {
            FieldError lonError = new FieldError("location", "lon", "Please enter a latitude");
            result.addError(lonError);
        } else {
            try {
                toAdd.setLon(new BigDecimal(lonString));
                if(toAdd.getLon().compareTo(new BigDecimal("180.00000")) == 1 || toAdd.getLon().compareTo(new BigDecimal("-180.00000")) == -1){
                    FieldError illegalLon = new FieldError("location", "lon", "Longitude must be between 180 and -180");
                    result.addError(illegalLon);
                }
            } catch (NumberFormatException ex) {
                FieldError lonFormat = new FieldError("location", "lon", "Please enter a NUMBER between -180.00000 and 180.00000");
                result.addError(lonFormat);
            }
        }
        try {
            service.getLocationByName(toAdd.getName());
            FieldError error = new FieldError("location", "name", "Location name already exists");
            result.addError(error);
        } catch (InvalidNameException ex) {
        }
        if (result.hasErrors()) {
            try {
                allLocations = service.getAllLocations();
            } catch (EmptyResultException ex) {
            }
            pageModel.addAttribute("location", toAdd);
            pageModel.addAttribute("locations", allLocations);
            return "locations";
        }
        try {
            service.createLocation(toAdd);
        } catch (DuplicateNameException | InvalidEntityException ex) {
        }
        return "redirect:/locations";
    }

    @GetMapping("/editlocation/{id}")
    public String displayEditLocation(Model pageModel, @PathVariable Integer id) throws InvalidIdException {
        List<Location> allLocations = new ArrayList<>();
        Location toEdit = new Location();
        try {
            allLocations = service.getAllLocations();
        } catch (EmptyResultException ex) {
        }
        toEdit = service.getLocationById(id);

        pageModel.addAttribute("location", toEdit);
        pageModel.addAttribute("locations", allLocations);
        return "editlocation";
    }

    @PostMapping("editlocation")
    public String editLocation(@Valid Location toEdit, BindingResult result, HttpServletRequest request, Model pageModel) {
        List<Location> allLocations = new ArrayList<>();
        String latString = request.getParameter("latString");
        String lonString = request.getParameter("lonString");
        if (latString.isBlank()) {
            FieldError latError = new FieldError("location", "lat", "Please enter a latitude");
            result.addError(latError);
        } else {
            try {
                BigDecimal lat = new BigDecimal(latString);
                toEdit.setLat(lat);
            } catch (NumberFormatException ex) {
                FieldError latFormat = new FieldError("location", "lat", "Please enter a NUMBER between -90.00000 and 90.00000");
                result.addError(latFormat);
            }
        }
        if (lonString.isBlank()) {
            FieldError lonError = new FieldError("location", "lon", "Please enter a latitude");
            result.addError(lonError);
        } else {
            try {
                toEdit.setLon(new BigDecimal(lonString));
            } catch (NumberFormatException ex) {
                FieldError lonFormat = new FieldError("location", "lon", "Please enter a NUMBER between -180.00000 and 180.00000");
                result.addError(lonFormat);
            }
        }

        try {
            Location toCheck = service.getLocationByName(toEdit.getName());
            if (toCheck.getId() != toEdit.getId()) {
                FieldError error = new FieldError("location", "name", "Location name already exists");
                result.addError(error);
            }
        } catch (InvalidNameException ex) {
        }
        if (result.hasErrors()) {
            try {
                allLocations = service.getAllLocations();
            } catch (EmptyResultException ex) {
            }
            pageModel.addAttribute("location", toEdit);
            pageModel.addAttribute("locations", allLocations);
            return "editlocation";
        }
        try {
            service.editLocation(toEdit);
        } catch (InvalidEntityException | DuplicateNameException ex) {
        }
        return "redirect:/locations";
    }

    @GetMapping("/deletelocation/{id}")
    public String deleteLocationById(@PathVariable Integer id) {
        try {
            service.removeLocation(id);
        } catch (InvalidIdException ex) {
        }
        return "redirect:/supers";
    }
}
