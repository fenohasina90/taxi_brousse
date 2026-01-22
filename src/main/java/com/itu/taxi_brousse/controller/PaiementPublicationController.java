package com.itu.taxi_brousse.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itu.taxi_brousse.service.paiement.PaiementPublicationService;

@Controller
@RequestMapping("/publications/paiements")
public class PaiementPublicationController {

    private final PaiementPublicationService paiementPublicationService;

    public PaiementPublicationController(PaiementPublicationService paiementPublicationService) {
        this.paiementPublicationService = paiementPublicationService;
    }

    @GetMapping("")
    public ModelAndView liste(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin
    ) {
        ModelAndView mv = new ModelAndView("publication/liste_paiements_publication");
        mv.addObject("etatPaiement", paiementPublicationService.listerEtatPaiement(dateDebut, dateFin));
        return mv;
    }

    @GetMapping("/ajouter")
    public ModelAndView formulaire(@RequestParam("idVoyagePub") Integer idVoyagePub) {
        ModelAndView mv = new ModelAndView("publication/formulaire_paiement_publication");
        mv.addObject("etat", paiementPublicationService.getEtatPaiement(idVoyagePub));
        mv.addObject("idVoyagePub", idVoyagePub);
        return mv;
    }

    @PostMapping("/ajouter")
    public ModelAndView traiter(
            @RequestParam("idVoyagePub") Integer idVoyagePub,
            @RequestParam("montant") double montant,
            @RequestParam("datePaiement") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime datePaiement,
            RedirectAttributes redirectAttributes
    ) {
        try {
            paiementPublicationService.effectuerPaiement(idVoyagePub, montant, datePaiement);
            redirectAttributes.addFlashAttribute("successMessage", "Paiement enregistre");
            return new ModelAndView("redirect:/publications/paiements");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return new ModelAndView("redirect:/publications/paiements/ajouter?idVoyagePub=" + idVoyagePub);
        }
    }
}
