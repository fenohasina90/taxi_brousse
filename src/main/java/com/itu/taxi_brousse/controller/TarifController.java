package com.itu.taxi_brousse.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itu.taxi_brousse.repository.core.TrajetRepository;
import com.itu.taxi_brousse.service.core.GareRoutiereService;
import com.itu.taxi_brousse.service.tarif.TarifService;
import com.itu.taxi_brousse.service.voyage.TypeVoyageService;

@Controller
@RequestMapping("/tarifs")
public class TarifController {
    private final GareRoutiereService gareRoutiereService;
    private final TarifService tarifService;
    private final TrajetRepository trajetRepository;
    private final TypeVoyageService typeVoyageService;

    public TarifController(GareRoutiereService gareRoutiereService,
                                TarifService tarifService,
                                TrajetRepository trajetRepository,
                                TypeVoyageService typeVoyageService) {
        this.gareRoutiereService = gareRoutiereService;
        this.tarifService = tarifService;
        this.trajetRepository = trajetRepository;
        this.typeVoyageService = typeVoyageService;
    }

    @GetMapping("/ajouter")
    public ModelAndView formulaireTarifs(@RequestParam("idTrajet") Integer idTrajet) {
        var trajet = trajetRepository.findById(idTrajet)
                .orElseThrow(() -> new IllegalArgumentException("Trajet introuvable"));

        ModelAndView mv = new ModelAndView("tarif/formulaire_tarifs_trajet");
        mv.addObject("trajet", trajet);
        mv.addObject("typesVoyage", typeVoyageService.getAllTypesVoyage());
        return mv;
    }

    @PostMapping("/ajouter")
    public ModelAndView traiterTarifs(
            @RequestParam("idTrajet") Integer idTrajet,
            @RequestParam java.util.Map<String, String> params,
            RedirectAttributes redirectAttributes
    ) {
        try {
            java.util.Map<Integer, Double> montantsParType = new java.util.HashMap<>();
            for (var e : params.entrySet()) {
                if (e.getKey().startsWith("montant_") && e.getValue() != null && !e.getValue().isBlank()) {
                    Integer idType = Integer.valueOf(e.getKey().substring("montant_".length()));
                    Double montant = Double.valueOf(e.getValue());
                    montantsParType.put(idType, montant);
                }
            }

            tarifService.creerTarifsPourTrajet(idTrajet, montantsParType);
            redirectAttributes.addFlashAttribute("successMessage", "Tarifs crees avec succes");
            return new ModelAndView("redirect:/tarifs");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return new ModelAndView("redirect:/tarifs/ajouter?idTrajet=" + idTrajet);
        }
    }

    // ---- LISTE TARIFS ----

    @GetMapping("")
    public ModelAndView listeTarifs(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin,
            @RequestParam(required = false) Integer idTypeVoyage,
            @RequestParam(required = false) Integer idGareDepart,
            @RequestParam(required = false) Integer idGareArrivee
    ) {
        ModelAndView mv = new ModelAndView("tarif/liste_tarifs");

        LocalDateTime dDebut = (dateDebut != null) ? dateDebut.atStartOfDay() : null;
        LocalDateTime dFin = (dateFin != null) ? dateFin.atTime(23, 59, 59) : null;

        var tarifs = tarifService.listerTarifsFiltres(
                dDebut,
                dFin,
                idTypeVoyage,
                idGareDepart,
                idGareArrivee
        );

        mv.addObject("tarifs", tarifs);
        mv.addObject("typesVoyage", typeVoyageService.getAllTypesVoyage());
        mv.addObject("villesDepart", gareRoutiereService.getAllGaresRoutieres());
        mv.addObject("villesArrivee", gareRoutiereService.getAllGaresRoutieres());
        return mv;
    }

    // ---- MODIFICATION TARIF ----

    @GetMapping("/{id}/modifier")
    public ModelAndView formulaireModifierTarif(@PathVariable("id") Integer idTarif) {
        var tarif = tarifService.getById(idTarif);
        ModelAndView mv = new ModelAndView("tarif/formulaire_modifier_tarif");
        mv.addObject("tarif", tarif);
        return mv;
    }

    @PostMapping("/{id}/modifier")
    public ModelAndView traiterModifierTarif(
            @PathVariable("id") Integer idTarif,
            @RequestParam("montant") Double montant,
            RedirectAttributes redirectAttributes
    ) {
        try {
            tarifService.modifierTarif(idTarif, montant);
            redirectAttributes.addFlashAttribute("successMessage", "Tarif modifie avec succes");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return new ModelAndView("redirect:/tarifs");
    }
}
