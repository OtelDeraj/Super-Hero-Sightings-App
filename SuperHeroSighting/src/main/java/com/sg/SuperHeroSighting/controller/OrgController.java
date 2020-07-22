/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.controller;

import com.sg.SuperHeroSighting.dto.Org;
import com.sg.SuperHeroSighting.dto.OrgVM;
import com.sg.SuperHeroSighting.dto.Sighting;
import com.sg.SuperHeroSighting.dto.Super;
import com.sg.SuperHeroSighting.exceptions.DuplicateNameException;
import com.sg.SuperHeroSighting.exceptions.EmptyResultException;
import com.sg.SuperHeroSighting.exceptions.InvalidEntityException;
import com.sg.SuperHeroSighting.exceptions.InvalidIdException;
import com.sg.SuperHeroSighting.service.OrgService;
import com.sg.SuperHeroSighting.service.SightingService;
import com.sg.SuperHeroSighting.service.SuperService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
public class OrgController {
    
    @Autowired
    OrgService service;
    
    @Autowired
    SuperService supServ;
    
    
    @GetMapping("/orgs")
    public String displayOrgsPage(Model pageModel){
        List<Org> allOrgs = new ArrayList<>();
        List<Super> allSupers = new ArrayList<>();
        try {
            allOrgs = service.getAllOrgs();
        } catch (EmptyResultException ex) {
        }
        try {
            allSupers = supServ.getAllSupers();
        } catch (EmptyResultException ex) {
        }
        pageModel.addAttribute("orgs", allOrgs);
        pageModel.addAttribute("supers", allSupers);
        return "orgs";
    }
    
    @GetMapping("/org/{id}")
    public String displayOrgDetails(@PathVariable Integer id, Model pageModel) {
        Org toDisplay = new Org();
        try {
            toDisplay = service.getOrgById(id);
        } catch (InvalidIdException ex) {
        }
        pageModel.addAttribute("org", toDisplay);
        return "orgdetail";
    }
    
    @PostMapping("addorg")
    public String addOrg(OrgVM toAdd) throws InvalidIdException, InvalidEntityException, DuplicateNameException{
        Set<Super> supersForOrg = new HashSet<>();
        for(Integer s: toAdd.getSuperIds()){
            supersForOrg.add(supServ.getSuperById(s));
        }
        toAdd.getToGet().setSupers(supersForOrg);
        service.addNewOrg(toAdd.getToGet());
        return "redirect:/orgs";
    }
    
    @GetMapping("/editorg/{id}")
    public String displayEditOrg(Model pageModel, @PathVariable Integer id) throws InvalidIdException{
        List<Super> allSupers = new ArrayList<>();
        List<Org> allOrgs = new ArrayList<>();
        Org toEdit = service.getOrgById(id);
        try {
            allSupers = supServ.getAllSupers();
            allSupers.forEach(s -> {
            s.setOrgs(null);
            s.setPowers(null);
            });
        } catch (EmptyResultException ex) {
        }
        try {
            allOrgs = service.getAllOrgs();
        } catch (EmptyResultException ex) {
        }
        pageModel.addAttribute("org", toEdit);
        pageModel.addAttribute("orgs", allOrgs);
        pageModel.addAttribute("supers", allSupers);
        return "editorg";
    }
    
    @PostMapping("editorg")
    public String editOrg(OrgVM toEdit) throws InvalidIdException, InvalidEntityException, DuplicateNameException{
        Set<Super> supersForOrg = new HashSet<>();
        for(Integer s: toEdit.getSuperIds()){
            supersForOrg.add(supServ.getSuperById(s));
        }
        toEdit.getToGet().setSupers(supersForOrg);
        service.editOrg(toEdit.getToGet());
        return "redirect:/orgs";
    }
    
    @GetMapping("deleteorg/{id}")
    public String deleteOrgById(@PathVariable Integer id) throws InvalidIdException{
        service.removeOrg(id);
        return "redirect:/orgs";
    }
    
    
}
