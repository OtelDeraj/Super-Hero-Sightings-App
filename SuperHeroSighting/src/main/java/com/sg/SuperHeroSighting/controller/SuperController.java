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
import com.sg.SuperHeroSighting.exceptions.DuplicateNameException;
import com.sg.SuperHeroSighting.exceptions.EmptyResultException;
import com.sg.SuperHeroSighting.exceptions.InvalidEntityException;
import com.sg.SuperHeroSighting.exceptions.InvalidIdException;
import com.sg.SuperHeroSighting.exceptions.InvalidNameException;
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
        List<Super> allSupers = new ArrayList<>();
        List<Org> allOrgs = new ArrayList<>();
        List<Power> allPowers = new ArrayList<>();
        try {
            allSupers = service.getAllSupers();
            allPowers = powServ.getAllPowers();
            allOrgs = orgServ.getAllOrgs();
        } catch (EmptyResultException ex) {
        }
        pageModel.addAttribute("super", new Super());
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
    public String addSuper(@Valid Super toAdd, BindingResult result, HttpServletRequest request, Model pageModel) throws InvalidIdException, InvalidEntityException, DuplicateNameException {
        List<Super> allSupers = new ArrayList<>();
        List<Power> allPowers = new ArrayList<>();
        List<Org> allOrgs = new ArrayList<>();
        Set<Power> powers = new HashSet<>();
        Set<Org> orgs = new HashSet<>();
        String[] powerIds = request.getParameterValues("powerIds");
        String[] orgIds = request.getParameterValues("orgIds");
        try {
            service.getSuperByName(toAdd.getName());
            FieldError nameError = new FieldError("super", "name", "Super name already exists");
            result.addError(nameError);
        } catch (InvalidNameException ex) {
        }
        if (powerIds == null) {
            FieldError powerError = new FieldError("super", "powers", "Please select at least one power.");
            result.addError(powerError);
        } else {
            for (String p : powerIds) {
                powers.add(powServ.getPowerById(Integer.parseInt(p)));
            }
        }
        if (orgIds == null) {
            FieldError orgError = new FieldError("super", "orgs", "Please select at least one org.");
            result.addError(orgError);
        } else {
            for (String o : orgIds) {
                orgs.add(orgServ.getOrgById(Integer.parseInt(o)));
            }
        }
        toAdd.setPowers(powers);
        toAdd.setOrgs(orgs);
        if (result.hasErrors()) {
            try {
                allSupers = service.getAllSupers();
                allPowers = powServ.getAllPowers();
                allOrgs = orgServ.getAllOrgs();
            } catch (EmptyResultException ex) {
            }
            pageModel.addAttribute("super", toAdd);
            pageModel.addAttribute("powers", allPowers);
            pageModel.addAttribute("orgs", allOrgs);
            pageModel.addAttribute("supers", allSupers);
            return "supers";
        }
        service.createSuper(toAdd);
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
    public String editSuper(@Valid Super toEdit, BindingResult result, HttpServletRequest request, Model pageModel) throws InvalidIdException, EmptyResultException, InvalidEntityException, DuplicateNameException {
        List<Super> allSupers = new ArrayList<>();
        List<Power> allPowers = new ArrayList<>();
        List<Org> allOrgs = new ArrayList<>();
        Set<Power> powers = new HashSet<>();
        Set<Org> orgs = new HashSet<>();
        String[] powerIds = request.getParameterValues("powerIds");
        String[] orgIds = request.getParameterValues("orgIds");
        try {
            Super toCheck = service.getSuperByName(toEdit.getName());
            if (toCheck.getId() != toEdit.getId()) {
                FieldError nameError = new FieldError("super", "name", "Super name already exists");
                result.addError(nameError);
            }
        } catch (InvalidNameException ex) {
        }
        if (powerIds == null) {
            FieldError powerError = new FieldError("super", "powers", "Please select at least one power.");
            result.addError(powerError);
        } else {
            for (String p : powerIds) {
                powers.add(powServ.getPowerById(Integer.parseInt(p)));
            }
        }
        if (orgIds == null) {
            FieldError orgError = new FieldError("super", "orgs", "Please select at least one org.");
            result.addError(orgError);
        } else {
            for (String o : orgIds) {
                orgs.add(orgServ.getOrgById(Integer.parseInt(o)));
            }
        }
        toEdit.setPowers(powers);
        toEdit.setOrgs(orgs);
        if (result.hasErrors()) {
            try {
                allSupers = service.getAllSupers();
                allPowers = powServ.getAllPowers();
                allOrgs = orgServ.getAllOrgs();
            } catch (EmptyResultException ex) {
            }
            pageModel.addAttribute("super", toEdit);
            pageModel.addAttribute("powers", allPowers);
            pageModel.addAttribute("orgs", allOrgs);
            pageModel.addAttribute("supers", allSupers);
            return "editsuper";
        }
        service.editSuper(toEdit);
        return "redirect:/supers";
    }

    @GetMapping("/deletesuper/{id}")
    public String deleteSuperById(@PathVariable Integer id) throws InvalidIdException {
        service.removeSuper(id);
        return "redirect:/supers";
    }

}
