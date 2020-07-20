/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.controller;

import com.sg.SuperHeroSighting.dto.Power;
import com.sg.SuperHeroSighting.dto.Super;
import com.sg.SuperHeroSighting.exceptions.InvalidIdException;
import com.sg.SuperHeroSighting.service.PowerService;
import com.sg.SuperHeroSighting.service.SuperService;
import java.util.ArrayList;
import java.util.List;
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
public class PowerController {
    
    @Autowired
    PowerService service;
    
    @Autowired
    SuperService supServ;
    
    @GetMapping("/powers")
    public String displayPowersPage(){
        return "powers";
    }
    
    @GetMapping("/power/{id}") 
    public String displayPowerDetails(@PathVariable Integer id, Model pageModel) throws InvalidIdException{
        List<Super> supersWithPower = new ArrayList<>();
        Power toDisplay = service.getPowerById(id);
        supersWithPower = supServ.getSupersByPowerId(toDisplay.getId());
        pageModel.addAttribute("supers", supersWithPower);
        pageModel.addAttribute("power", toDisplay);
        return "powerdetail";
    }
}
