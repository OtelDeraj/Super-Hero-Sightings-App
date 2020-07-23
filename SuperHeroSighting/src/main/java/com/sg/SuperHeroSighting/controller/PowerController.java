/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.controller;

import com.sg.SuperHeroSighting.dto.Power;
import com.sg.SuperHeroSighting.dto.Super;
import com.sg.SuperHeroSighting.exceptions.DuplicateNameException;
import com.sg.SuperHeroSighting.exceptions.EmptyResultException;
import com.sg.SuperHeroSighting.exceptions.InvalidEntityException;
import com.sg.SuperHeroSighting.exceptions.InvalidIdException;
import com.sg.SuperHeroSighting.exceptions.InvalidNameException;
import com.sg.SuperHeroSighting.service.PowerService;
import com.sg.SuperHeroSighting.service.SuperService;
import java.util.ArrayList;
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
public class PowerController {
    
    @Autowired
    PowerService service;
    
    @Autowired
    SuperService supServ;
    
    @GetMapping("/powers")
    public String displayPowersPage(Model pageModel) {
        List<Power> allPowers = new ArrayList<>();
        try {
            allPowers = service.getAllPowers();
        } catch (EmptyResultException ex) {
        }
        pageModel.addAttribute("power", new Power());
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
        pageModel.addAttribute("supers", supersWithPower);
        pageModel.addAttribute("power", toDisplay);
        return "powerdetail";
    }
    
    @PostMapping("addpower")
    public String addPower(@Valid Power toAdd, BindingResult result, Model pageModel) {
        List<Power> allPowers = new ArrayList<>();
            try {
                service.getPowerByName(toAdd.getName());
                FieldError error = new FieldError("power", "name", "Power name already exists");
                result.addError(error);
            } catch (InvalidNameException ex) {
            }
            if(result.hasErrors()){
                try {
                    allPowers = service.getAllPowers();
                } catch (EmptyResultException ex) {
                }
                pageModel.addAttribute("power", toAdd);
                pageModel.addAttribute("powers", allPowers);
                return "powers";
            }
        try {
            service.createPower(toAdd);
        } catch (InvalidEntityException | DuplicateNameException ex) {
        }
        
        return "redirect:/powers";
    }
    
    @GetMapping("/editpower/{id}")
    public String displayEditPower(Model pageModel, @PathVariable Integer id) throws InvalidIdException {
        List<Power> allPowers = new ArrayList<>();
        try {
            allPowers = service.getAllPowers();
        } catch (EmptyResultException ex) {
        }
        Power toEdit = service.getPowerById(id);
        pageModel.addAttribute("power", toEdit);
        pageModel.addAttribute("powers", allPowers);
        return "editpower";
    }
    
    @PostMapping("editpower")
    public String editPower(@Valid Power toEdit, BindingResult result, Model pageModel) {
        List<Power> allPowers = new ArrayList<>();
        try {
            Power toCheck = service.getPowerByName(toEdit.getName());
            if (toCheck.getId() != toEdit.getId()) {
                FieldError error = new FieldError("power", "name", "Power name already exists");
                result.addError(error);
            }
        } catch (InvalidNameException ex) {
        }
        if(result.hasErrors()){
            try {
                allPowers = service.getAllPowers();
            } catch (EmptyResultException ex) {
            }
            pageModel.addAttribute("powers", allPowers);
            pageModel.addAttribute("power", toEdit);
            return "editpower";
        }
        try {
            service.editPower(toEdit);
        } catch (InvalidEntityException | DuplicateNameException ex) {
        }
        return "redirect:/powers";
    }
    
    @GetMapping("/deletepower/{id}")
    public String deletePowerById(@PathVariable Integer id) throws InvalidIdException {
        service.removePower(id);
        return "redirect:/powers";
    }
}
