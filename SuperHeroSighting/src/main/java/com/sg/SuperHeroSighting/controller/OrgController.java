/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.controller;

import com.sg.SuperHeroSighting.dto.Org;
import com.sg.SuperHeroSighting.exceptions.InvalidIdException;
import com.sg.SuperHeroSighting.service.OrgService;
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
public class OrgController {
    
    @Autowired
    OrgService service;
    
    
    @GetMapping("/orgs")
    public String displayOrgsPage(){
        return "orgs";
    }
    
    @GetMapping("/org/{id}")
    public String displayOrgDetails(@PathVariable Integer id, Model pageModel) throws InvalidIdException{
        Org toDisplay = service.getOrgById(id);
        pageModel.addAttribute("org", toDisplay);
        return "orgdetail";
    }
}
