package com.itu.taxi_brousse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.itu.taxi_brousse.service.core.VoitureService;


@Controller
@RequestMapping("/voitures")
public class VoitureController {
    private final VoitureService voitureService;

    public VoitureController(VoitureService voitureService) {
        this.voitureService = voitureService;
    }
    @GetMapping("/ajouter")
    public ModelAndView formulaireVoiture(){
        return new ModelAndView("voiture/formulaire_voiture");
    }    

    @PostMapping("/ajouter")
    public ModelAndView traitementFormulaire(@RequestParam("immatricule") String immatricule,
                                            @RequestParam("nbPlace") Integer nbPlace) {
        voitureService.creerVoiture(nbPlace, immatricule);
        return new ModelAndView("redirect:/voitures");
    }

    @GetMapping("")
    public ModelAndView listeVoiture(){
        ModelAndView mv = new ModelAndView("voiture/liste_voiture");
        mv.addObject("liste_voiture", voitureService.getToutesLesVoitures());
        return mv;
    }
}


