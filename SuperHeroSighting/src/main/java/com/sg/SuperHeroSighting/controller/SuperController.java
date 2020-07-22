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
import com.sg.SuperHeroSighting.dto.SuperVM;
import com.sg.SuperHeroSighting.exceptions.DuplicateNameException;
import com.sg.SuperHeroSighting.exceptions.EmptyResultException;
import com.sg.SuperHeroSighting.exceptions.InvalidEntityException;
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
import org.springframework.web.bind.annotation.RequestBody;

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
    public String displaySupersPage(Model pageModel) {
//        Super toAdd = new Super();
//        toAdd.setOrgs(new HashSet<>());
//        toAdd.setPowers(new HashSet<>());
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
//        pageModel.addAttribute("newsuper", toAdd);
        pageModel.addAttribute("powers", allPowers);
        pageModel.addAttribute("orgs", allOrgs);
        pageModel.addAttribute("supers", allSupers);
        return "supers";
    }

    @GetMapping("/super/{id}")
    public String displaySuperDetails(@PathVariable Integer id, Model pageModel) {
        List<Sighting> sightingsForSuper = new ArrayList<>();
        Super toDisplay = new Super();
        try {
            toDisplay = service.getSuperById(id);
        } catch (InvalidIdException ex) {
        }
        try {
            sightingsForSuper = sightServ.getSightingsBySuper(toDisplay.getId());
        } catch (EmptyResultException ex) {
        }
        pageModel.addAttribute("sightings", sightingsForSuper);
        pageModel.addAttribute("super", toDisplay);
        return "superdetail";
    }

    @PostMapping("addsuper")
    public String addSuper(SuperVM toAdd) throws InvalidIdException, InvalidEntityException, DuplicateNameException {
        Set<Power> allPowers = new HashSet<>();
        Set<Org> allOrgs = new HashSet<>();
        for (Integer p : toAdd.getPowerIds()) {
            allPowers.add(powServ.getPowerById(p));
        }
        for (Integer o : toAdd.getOrgIds()) {
            allOrgs.add(orgServ.getOrgById(o));
        }
        toAdd.getToGet().setPowers(allPowers);
        toAdd.getToGet().setOrgs(allOrgs);
        service.createSuper(toAdd.getToGet());
        return "redirect:/supers";
    }

    @GetMapping("editsuper/{id}")
    public String displayEditSuper(Model pageModel, @PathVariable Integer id) throws InvalidIdException, EmptyResultException {
        List<Org> allOrgs = new ArrayList<>();
        List<Super> allSupers = new ArrayList<>();
        Super toEdit = service.getSuperById(id);
        try {
            allOrgs = orgServ.getAllOrgs();
            allOrgs.forEach(o -> o.setSupers(null));
        } catch (EmptyResultException ex) {
        }
        try {
            allSupers = service.getAllSupers();
        } catch (EmptyResultException ex) {
        }
        pageModel.addAttribute("isValid", true);
        pageModel.addAttribute("super", toEdit);
        pageModel.addAttribute("supers", allSupers);
        pageModel.addAttribute("powers", powServ.getAllPowers());
        pageModel.addAttribute("orgs", allOrgs);
        return "editsuper";
    }

    @PostMapping("editsuper")
    public String editSuper(SuperVM toEdit, Model pageModel) throws InvalidIdException, EmptyResultException {
        Set<Power> powersToEdit = new HashSet<>();
        Set<Org> orgsToEdit = new HashSet<>();
        for (Integer p : toEdit.getPowerIds()) {
            powersToEdit.add(powServ.getPowerById(p));
        }
        for (Integer o : toEdit.getOrgIds()) {
            orgsToEdit.add(orgServ.getOrgById(o));
        }
        toEdit.getToGet().setPowers(powersToEdit);
        toEdit.getToGet().setOrgs(orgsToEdit);
        try {
            service.editSuper(toEdit.getToGet());
        } catch (DuplicateNameException | InvalidEntityException ex) {
            pageModel.addAttribute("supers", service.getAllSupers());
            pageModel.addAttribute("powers", powServ.getAllPowers());
            pageModel.addAttribute("orgs", orgServ.getAllOrgs());
            pageModel.addAttribute("super", toEdit.getToGet());
            pageModel.addAttribute("isValid", false);
            pageModel.addAttribute("errorMessage", ex.getMessage());
            return "editsuper";
        } 
        return "redirect:/supers";
    }

    @GetMapping("/deletesuper/{id}")
    public String deleteSuperById(@PathVariable Integer id) throws InvalidIdException {
        service.removeSuper(id);
        return "redirect:/supers";
    }

}
