/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.controller;

import com.sg.SuperHeroSighting.dto.Org;
import com.sg.SuperHeroSighting.dto.Super;
import com.sg.SuperHeroSighting.exceptions.DuplicateNameException;
import com.sg.SuperHeroSighting.exceptions.EmptyResultException;
import com.sg.SuperHeroSighting.exceptions.InvalidEntityException;
import com.sg.SuperHeroSighting.exceptions.InvalidIdException;
import com.sg.SuperHeroSighting.exceptions.InvalidNameException;
import com.sg.SuperHeroSighting.service.OrgService;
import com.sg.SuperHeroSighting.service.SuperService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
public class OrgController {

    @Autowired
    OrgService service;

    @Autowired
    SuperService supServ;

    @GetMapping("/orgs")
    public String displayOrgsPage(Model pageModel) {
        List<Org> allOrgs = new ArrayList<>();
        List<Super> allSupers = new ArrayList<>();
        try {
            allOrgs = service.getAllOrgs();
            allSupers = supServ.getAllSupers();
        } catch (EmptyResultException ex) {
        }
        pageModel.addAttribute("org", new Org());
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
    public String addOrg(@Valid Org toAdd, BindingResult result, HttpServletRequest request, Model pageModel) throws InvalidIdException, InvalidEntityException, DuplicateNameException {
        List<Org> allOrgs = new ArrayList<>();
        List<Super> allSupers = new ArrayList<>();
        Set<Super> supersForOrg = new HashSet<>();
        String[] superIds = request.getParameterValues("superIds");

        try {
            service.getOrgByName(toAdd.getName());
            FieldError error = new FieldError("org", "name", "Org name already exists");
            result.addError(error);
        } catch (InvalidNameException ex) {
        }
        if (superIds == null) {
            FieldError idError = new FieldError("org", "supers", "Please select at least one super");
            result.addError(idError);
        } else {
            for (String s : superIds) {
                supersForOrg.add(supServ.getSuperById(Integer.parseInt(s)));
            }
        }
        toAdd.setSupers(supersForOrg);
        if (result.hasErrors()) {
            try {
                allOrgs = service.getAllOrgs();
                allSupers = supServ.getAllSupers();
            } catch (EmptyResultException ex) {
            }
            pageModel.addAttribute("org", toAdd);
            pageModel.addAttribute("orgs", allOrgs);
            pageModel.addAttribute("supers", allSupers);
            return "orgs";
        }
        service.addNewOrg(toAdd);
        return "redirect:/orgs";
    }

    @GetMapping("/editorg/{id}")
    public String displayEditOrg(Model pageModel, @PathVariable Integer id) throws InvalidIdException {
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
    public String editOrg(@Valid Org toEdit, BindingResult result, HttpServletRequest request, Model pageModel) throws InvalidIdException, InvalidEntityException, DuplicateNameException {
        Set<Super> supersForOrg = new HashSet<>();
        List<Super> allSupers = new ArrayList<>();
        List<Org> allOrgs = new ArrayList<>();
        String[] superIds = request.getParameterValues("superIds");
        try {
            Org toCheck = service.getOrgByName(toEdit.getName());
            if (toCheck.getId() != toEdit.getId()) {
                FieldError error = new FieldError("org", "name", "Org name already exists");
                result.addError(error);
            }
        } catch (InvalidNameException ex) {
        }
        if (superIds == null) {
            FieldError idError = new FieldError("org", "supers", "Please select at least one super");
            result.addError(idError);
        } else {
            for (String s : superIds) {
                supersForOrg.add(supServ.getSuperById(Integer.parseInt(s)));
            }
        }
        toEdit.setSupers(supersForOrg);
        if (result.hasErrors()) {
            try {
                allSupers = supServ.getAllSupers();
                allOrgs = service.getAllOrgs();
            } catch (EmptyResultException ex) {
            }
            pageModel.addAttribute("org", toEdit);
            pageModel.addAttribute("orgs", allOrgs);
            pageModel.addAttribute("supers", allSupers);
            return "editorg";
        }
        service.editOrg(toEdit);
        return "redirect:/orgs";
    }

    @GetMapping("deleteorg/{id}")
    public String deleteOrgById(@PathVariable Integer id) throws InvalidIdException {
        service.removeOrg(id);
        return "redirect:/orgs";
    }

}
