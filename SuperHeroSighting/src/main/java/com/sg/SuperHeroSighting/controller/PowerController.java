/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.controller;

import com.sg.SuperHeroSighting.dto.Power;
import com.sg.SuperHeroSighting.dto.PowerVM;
import com.sg.SuperHeroSighting.dto.Super;
import com.sg.SuperHeroSighting.exceptions.DuplicateNameException;
import com.sg.SuperHeroSighting.exceptions.EmptyResultException;
import com.sg.SuperHeroSighting.exceptions.InvalidEntityException;
import com.sg.SuperHeroSighting.exceptions.InvalidIdException;
import com.sg.SuperHeroSighting.service.PowerService;
import com.sg.SuperHeroSighting.service.SuperService;
import java.util.ArrayList;
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
public class PowerController {
    
    @Autowired
    PowerService service;
    
    @Autowired
    SuperService supServ;
    
    @GetMapping("/powers")
    public String displayPowersPage(Model pageModel){
        List<Power> allPowers = new ArrayList<>();
        try {
            allPowers = service.getAllPowers();
        } catch (EmptyResultException ex) {
        }
        pageModel.addAttribute("powers", allPowers);
        return "powers";
    }
    
    @GetMapping("/power/{id}") 
    public String displayPowerDetails(@PathVariable Integer id, Model pageModel) {
        List<Super> supersWithPower = new ArrayList<>();
        Power toDisplay = new Power();
        try {
            toDisplay = service.getPowerById(id);
        } catch (InvalidIdException ex) {
        }
        try {
            supersWithPower = supServ.getSupersByPowerId(toDisplay.getId());
        } catch (InvalidIdException ex) {
        }
        pageModel.addAttribute("isValid", true);
        pageModel.addAttribute("supers", supersWithPower);
        pageModel.addAttribute("power", toDisplay);
        return "powerdetail";
    }
    
    @PostMapping("addpower")
    public String addPower(PowerVM toAdd, Model pageModel) {
        List<Power> allPowers = new ArrayList<>();
        try {
            allPowers = service.getAllPowers();
        } catch (EmptyResultException ex) {
        }
        try {
            service.createPower(toAdd.getToGet());
        } catch (DuplicateNameException | InvalidEntityException ex) {
            pageModel.addAttribute("isValid", false);
            pageModel.addAttribute("errorMessage", ex.getMessage());
            pageModel.addAttribute("powers", allPowers);
            return "powers";
        }
        return "redirect:/powers";
    }
    
    @GetMapping("/editpower/{id}")
    public String displayEditPower(Model pageModel, @PathVariable Integer id) throws InvalidIdException{
        List<Power> allPowers = new ArrayList<>();
        try {
            allPowers = service.getAllPowers();
        } catch (EmptyResultException ex) {
        }
        Power toEdit = service.getPowerById(id);
        pageModel.addAttribute("isValid", true);
        pageModel.addAttribute("power", toEdit);
        pageModel.addAttribute("powers", allPowers);
        return "editpower";
    }
    
    @PostMapping("editpower")
    public String editPower(PowerVM toEdit, Model pageModel) throws EmptyResultException {
        try {
            service.editPower(toEdit.getToGet());
        } catch (InvalidEntityException | DuplicateNameException ex) {
            pageModel.addAttribute("isValid", false);
            pageModel.addAttribute("errorMessage", ex.getMessage());
            pageModel.addAttribute("power", toEdit.getToGet());
            pageModel.addAttribute("powers", service.getAllPowers());
            return "editpower";
        }
        return "redirect:/powers";
    }
    
    @GetMapping("/deletepower/{id}")
    public String deletePowerById(@PathVariable Integer id) throws InvalidIdException{
        service.removePower(id);
        return "redirect:/powers";
    }
}
