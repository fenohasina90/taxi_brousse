package com.itu.taxi_brousse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.itu.taxi_brousse.service.core.GareRoutiereService;
import com.itu.taxi_brousse.service.core.TrajetService;

@Controller
@RequestMapping("/trajets")
public class TrajetController {

    private final TrajetService trajetService;
    private final GareRoutiereService gareRoutiereService;

    public TrajetController(TrajetService trajetService,
                                GareRoutiereService gareRoutiereService) {
        this.trajetService = trajetService;
        this.gareRoutiereService = gareRoutiereService;
    }

    @GetMapping("/ajouter")
    public ModelAndView formulaireTrajet() {
        ModelAndView mv = new ModelAndView("trajet/formulaire_trajet");
        mv.addObject("gares", gareRoutiereService.getAllGaresRoutieres());
        return mv;
    }

    @PostMapping("/ajouter")
    public ModelAndView traiterTrajet(
            @RequestParam("idGareDepart") Integer idGareDepart,
            @RequestParam("idGareArrivee") Integer idGareArrivee,
            @RequestParam(value = "distanceKm", required = false) Double distanceKm,
            @RequestParam(value = "estimationHeure", required = false) Integer estimationHeure,
            RedirectAttributes redirectAttributes
    ) {
        try {
            var trajet = trajetService.creerTrajet(idGareDepart, idGareArrivee, distanceKm, estimationHeure);
            return new ModelAndView("redirect:/tarifs/ajouter?idTrajet=" + trajet.getId());
        } catch (Exception e) {
            ModelAndView mv = new ModelAndView("trajet/formulaire_trajet");
            mv.addObject("gares", gareRoutiereService.getAllGaresRoutieres());
            mv.addObject("errorMessage", e.getMessage());
            return mv;
        }
    }
    
}
