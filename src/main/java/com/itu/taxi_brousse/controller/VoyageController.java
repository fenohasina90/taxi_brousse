package com.itu.taxi_brousse.controller;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.itu.taxi_brousse.service.core.GareRoutiereService;
import com.itu.taxi_brousse.service.core.TrajetService;
import com.itu.taxi_brousse.service.core.VoitureService;
import com.itu.taxi_brousse.service.voyage.VoyageService;
import com.itu.taxi_brousse.service.voyage.TypeVoyageService;
import com.itu.taxi_brousse.service.voyage.VoyageDetailsService;

@Controller
@RequestMapping("/voyages")
public class VoyageController {

    private final VoyageService voyageService;
    private final TypeVoyageService typeVoyageService;
    private final GareRoutiereService gareRoutiereService;
    private final TrajetService trajetService;
    private final VoitureService voitureService;
    private final VoyageDetailsService voyageDetailsService;

    public VoyageController(VoyageService voyageService,
                            TypeVoyageService typeVoyageService,
                            GareRoutiereService gareRoutiereService,
                            TrajetService trajetService,
                            VoitureService voitureService,
                            VoyageDetailsService voyageDetailsService) {
        this.voyageService = voyageService;
        this.typeVoyageService = typeVoyageService;
        this.gareRoutiereService = gareRoutiereService;
        this.trajetService = trajetService;
        this.voitureService = voitureService;
        this.voyageDetailsService = voyageDetailsService;
    }


    @GetMapping("/ajouter")
    public ModelAndView formulaireVoyage() {
        ModelAndView mv = new ModelAndView("voyage/formulaire_voyage");
        mv.addObject("liste_trajet", trajetService.getListeTrajets());
        return mv;
    }

    @PostMapping("/ajouter")
    public ModelAndView traitementFormulaireVoyage(
            @RequestParam("id_trajet") Integer idTrajet,
            @RequestParam("date_voyage") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) java.time.LocalDate dateVoyage
    ) {
        try {
            var voyage = voyageService.creerVoyage(idTrajet, dateVoyage);
            return new ModelAndView("redirect:/voyages/" + voyage.getId() + "/details/ajouter");
        } catch (Exception e) {
            ModelAndView mv = new ModelAndView("voyage/formulaire_voyage");
            mv.addObject("liste_trajet", trajetService.getListeTrajets());
            mv.addObject("errorMessage", "Erreur lors de la création du voyage : " + e.getMessage());
            return mv;
        }
    }

    @GetMapping("")
    public ModelAndView listeVoyagesPlanning(
            @RequestParam(required = false) Integer idGareDepart,
            @RequestParam(required = false) Integer idGareArrivee,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime heureDebut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime heureFin
    ) {
        var voyages = voyageService.listerVoyagesPlanning(
                idGareDepart,
                idGareArrivee,
                dateDebut,
                dateFin,
                heureDebut,
                heureFin
        );

        ModelAndView mv = new ModelAndView("voyage/liste_voyage_planning");
        mv.addObject("voyages", voyages);

        // Données pour les filtres (villes)
        mv.addObject("villesDepart", gareRoutiereService.getAllGaresRoutieres());
        mv.addObject("villesArrivee", gareRoutiereService.getAllGaresRoutieres());

        // Détails pour modals : map idVoyage -> liste de détails
        var idsVoyage = voyages.stream()
                .map(v -> v.getIdVoyage())
                .toList();
        mv.addObject("detailsParVoyage", voyageService.listerDetailsPlanningParVoyage(idsVoyage));

        return mv;
    }


    @GetMapping("/{idVoyage}/details/ajouter")
    public ModelAndView formulaireVoyageDetails(@PathVariable("idVoyage") Integer idVoyage) {
        ModelAndView mv = new ModelAndView("voyage/formulaire_voyage_details");
        mv.addObject("idVoyage", idVoyage);
        mv.addObject("liste_voiture", voitureService.getToutesLesVoitures());
        mv.addObject("liste_type_voyage", typeVoyageService.getAllTypesVoyage());
        return mv;
    }

    @PostMapping("/{idVoyage}/details/ajouter")
    public ModelAndView traitementFormulaireVoyageDetails(
            @PathVariable("idVoyage") Integer idVoyage,
            @RequestParam("id_voiture") Integer idVoiture,
            @RequestParam("id_type_voyage") Integer idTypeVoyage,
            @RequestParam("heure_depart") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime heureDepart
    ) {
        try {
            voyageDetailsService.creerVoyageDetails(idVoyage, idVoiture, idTypeVoyage, heureDepart);
            return new ModelAndView("redirect:/voyages");
        } catch (Exception e) {
            ModelAndView mv = new ModelAndView("voyage/formulaire_voyage_details");
            mv.addObject("idVoyage", idVoyage);
            mv.addObject("liste_voiture", voitureService.getToutesLesVoitures());
            mv.addObject("liste_type_voyage", typeVoyageService.getAllTypesVoyage());
            mv.addObject("errorMessage", "Erreur lors de la création du détail de voyage : " + e.getMessage());
            return mv;
        }
    }
}
