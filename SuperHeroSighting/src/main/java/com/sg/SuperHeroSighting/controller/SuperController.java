/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.controller;

import com.sg.SuperHeroSighting.dto.Org;
import com.sg.SuperHeroSighting.dto.Power;
import com.sg.SuperHeroSighting.dto.Sighting;
import com.sg.SuperHeroSighting.dto.Super;
import com.sg.SuperHeroSighting.exceptions.EmptyResultException;
import com.sg.SuperHeroSighting.exceptions.InvalidIdException;
import com.sg.SuperHeroSighting.service.OrgService;
import com.sg.SuperHeroSighting.service.PowerService;
import com.sg.SuperHeroSighting.service.SightingService;
import com.sg.SuperHeroSighting.service.SuperService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
public class SuperController {
    
    @Autowired
    SuperService service;
    
    @Autowired
    SightingService sightServ;
    
    @Autowired
    OrgService orgServ;
    
    @Autowired
    PowerService powServ;
    
    @GetMapping("/supers")
    public String displaySupersPage(Model pageModel){
        Super toAdd = new Super();
        toAdd.setOrgs(new HashSet<>());
        toAdd.setPowers(new HashSet<>());
        List<Super> allSupers = new ArrayList<>();
        List<Org> allOrgs = new ArrayList<>();
        List<Power> allPowers = new ArrayList<>();
        try {
            allSupers = service.getAllSupers();
        } catch (EmptyResultException ex) {
        }
        try {
            allOrgs = orgServ.getAllOrgs();
        } catch (EmptyResultException ex) {
        }
        try {
            allPowers = powServ.getAllPowers();
        } catch (EmptyResultException ex) {
        }
        pageModel.addAttribute("newsuper", toAdd);
        pageModel.addAttribute("powers", allPowers);
        pageModel.addAttribute("orgs", allOrgs);
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
    
    
    @PostMapping("addsuper")
    public String addSuper(@Valid Super toAdd, BindingResult result, HttpServletRequest req, Model pageModel) throws InvalidIdException{
        String[] powerIds = req.getParameterValues("powerId");
        String[] orgIds = req.getParameterValues("orgId");
        if(powerIds == null){
            FieldError error = new FieldError("super", "power", "A Super must have at least one power");
            result.addError(error);
        } else {
            toAdd = setPowers(toAdd, powerIds);
        }
        if(orgIds == null){
            FieldError error = new FieldError("super", "org", "A Super must be affiliated with one or more orgs");
            result.addError(error);
        } else {
            toAdd = setOrgs(toAdd, orgIds);
        }
        
        if(result.hasErrors()) {
            toAdd = setPowers(toAdd, powerIds);
            toAdd = setOrgs(toAdd, orgIds);
            List<Power> powers = new ArrayList<>();
            List<Org> orgs = new ArrayList<>();
            try {
                powers = powServ.getAllPowers();
            } catch (EmptyResultException ex) {
            }
            try {
                orgs = orgServ.getAllOrgs();
            } catch (EmptyResultException ex) {
            }
            pageModel.addAttribute("super", toAdd);
            pageModel.addAttribute("powers", powers);
            pageModel.addAttribute("orgs", orgs);
            return "addSuper";
        }
        
        
        service.createSuper(toAdd);
        return "redirect:/supers";
    }

    private Super setPowers(Super toAdd, String[] powerIds) {
        Set<Power> powers = new HashSet<>();
        if(powerIds != null){
            for(String idString : powerIds){
                int id = Integer.parseInt(idString);
                Power p = powServ.getPowerById(id);
                powers.add(p);
            }
        }
        toAdd.setPowers(powers);
        return toAdd;
    }
    
    private Super setOrgs(Super toAdd, String[] orgIds) throws InvalidIdException {
        Set<Org> orgs = new HashSet<>();
        if(orgIds != null){
            for(String idString : orgIds){
                int id = Integer.parseInt(idString);
                Org o = orgServ.getOrgById(id);
                orgs.add(o);
            }
        }
        toAdd.setOrgs(orgs);
        return toAdd;
    }
}
