package com.itu.taxi_brousse.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.itu.taxi_brousse.service.voyage.PublicationService;

@Controller
@RequestMapping("/publications")
public class PublicationController {

    private final PublicationService publicationService;

    public PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @GetMapping("/chiffre-affaire")
    public ModelAndView chiffreAffaireDiffusion(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin
    ) {
        ModelAndView mv = new ModelAndView("publication/chiffre_affaire");

        if (dateDebut != null && dateFin != null && dateDebut.isAfter(dateFin)) {
            mv.addObject("errorMessage", "La date debut doit etre avant la date fin");
            mv.addObject("caPublications", java.util.List.of());
            mv.addObject("totalCa", 0.0);
            return mv;
        }

        var ca = publicationService.listerChiffreAffaireDiffusion(dateDebut, dateFin);
        var total = publicationService.sommeChiffreAffaireDiffusion(dateDebut, dateFin);

        mv.addObject("caPublications", ca);
        mv.addObject("totalCa", total);
        return mv;
    }
}
