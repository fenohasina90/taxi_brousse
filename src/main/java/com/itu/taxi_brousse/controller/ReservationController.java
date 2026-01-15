package com.itu.taxi_brousse.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itu.taxi_brousse.service.core.GareRoutiereService;
import com.itu.taxi_brousse.service.paiement.paiementService;
import com.itu.taxi_brousse.service.reservation.ReservationService;
import com.itu.taxi_brousse.service.voyage.TypeVoyageService;
import com.itu.taxi_brousse.service.voyage.VoyageService;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final paiementService paiementService;
    private final VoyageService voyageService;
    private final TypeVoyageService typeVoyageService;
    private final GareRoutiereService gareRoutiereService;

    public ReservationController(ReservationService reservationService,
            com.itu.taxi_brousse.service.paiement.paiementService paiementService,
            VoyageService voyageService,
            TypeVoyageService typeVoyageService,
            GareRoutiereService gareRoutiereService) {
        this.reservationService = reservationService;
        this.paiementService = paiementService;
        this.voyageService = voyageService;
        this.typeVoyageService = typeVoyageService;
        this.gareRoutiereService = gareRoutiereService;
    }

    @GetMapping("")
    public ModelAndView listeReservations(
            @RequestParam(required = false) Integer idGareDepart,
            @RequestParam(required = false) Integer idGareArrivee,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime heureDebut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime heureFin
    ) {

        ModelAndView mv = new ModelAndView("reservation/liste_reservations");

        var reservations = reservationService.listerReservationsFiltrees(
                idGareDepart,
                idGareArrivee,
                dateDebut,
                dateFin,
                heureDebut,
                heureFin
        );

        mv.addObject("reservations", reservations);
        mv.addObject("villesDepart", gareRoutiereService.getAllGaresRoutieres());
        mv.addObject("villesArrivee", gareRoutiereService.getAllGaresRoutieres());

        var idsReservation = reservations.stream()
                .map(r -> r.getIdReservation())
                .toList();
        mv.addObject("placesParReservation", reservationService.getPlacesReserveesParReservation(idsReservation));

        return mv;
    }

    @PostMapping("/{id}/annuler")
    public ModelAndView annulerReservation(@PathVariable("id") Integer idReservation,
                                            RedirectAttributes redirectAttributes) {
        try {
            reservationService.annulerReservation(idReservation);
            redirectAttributes.addFlashAttribute("successMessage", "Réservation annulée avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return new ModelAndView("redirect:/reservations");
    }

    @GetMapping("/ajouter")
    public ModelAndView listeVoyages(
            @RequestParam(required = false) Integer idGareDepart,
            @RequestParam(required = false) Integer idGareArrivee,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime heureDebut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime heureFin,
            @RequestParam(required = false) Double tarifMin,
            @RequestParam(required = false) Double tarifMax,
            @RequestParam(required = false) Integer placesMin,
            @RequestParam(required = false) Integer placesMax,
            @RequestParam(required = false) Integer idTypeVoyage
    ) {

        ModelAndView mv = new ModelAndView("reservation/liste_voyage");

        mv.addObject("voyages",
                voyageService.rechercherVoyages(
                        idGareDepart,
                        idGareArrivee,
                        dateDebut,
                        dateFin,
                        heureDebut,
                        heureFin,
                        tarifMin,
                        tarifMax,
                        placesMin,
                        placesMax,
                        idTypeVoyage
                )
        );

        // Données pour les filtres
        mv.addObject("villesDepart", gareRoutiereService.getAllGaresRoutieres());
        mv.addObject("villesArrivee", gareRoutiereService.getAllGaresRoutieres());
        mv.addObject("statusVoyages", typeVoyageService.getAllTypesVoyage());
        return mv;
    }


    @GetMapping("/ajouter/{id}") 
    public ModelAndView formulaireReservation(@PathVariable("id") Integer idVoyageDetails) {
        ModelAndView mv = new ModelAndView("reservation/formulaire_reservation");
        mv.addObject("placesAutorisees", reservationService.obtenirPlacesDisponibles(idVoyageDetails));
        mv.addObject("totalPlaces", reservationService.getCapaciteVoiture(idVoyageDetails));
        mv.addObject("montantUnitaire", reservationService.getTarifUnitaire(idVoyageDetails));
        mv.addObject("tarifParPlaceJson", reservationService.getTarifParPlaceJson(idVoyageDetails));
        mv.addObject("idVoyageDetails", idVoyageDetails);
        mv.addObject("modePaiement",paiementService.getListModePaiements());
        return mv;
    }

    @PostMapping("/ajouter")
    public ModelAndView traiterReservation(
            @RequestParam("idVoyageDetails") Integer idVoyageDetails,
            @RequestParam(value = "typePaiement", required = false) Integer idTypePaiement,
            @RequestParam(value = "montant", required = false) Double montantVerse,
            @RequestParam("nom_client") String nomClient,
            @RequestParam("contactClient") String contactClient,
            @RequestParam("places") List<Integer> places,
            RedirectAttributes redirectAttributes
    ) {
        try {
            reservationService.creerReservation(
                    idVoyageDetails,
                    nomClient,
                    contactClient,
                    places,
                    idTypePaiement,
                    montantVerse != null ? montantVerse : 0.0
            );
            return new ModelAndView("redirect:/reservations/ajouter");
        } catch (Exception e) {
            ModelAndView mv = new ModelAndView("reservation/formulaire_reservation");
            mv.addObject("placesAutorisees", reservationService.obtenirPlacesDisponibles(idVoyageDetails));
            mv.addObject("totalPlaces", reservationService.getCapaciteVoiture(idVoyageDetails));
            mv.addObject("montantUnitaire", reservationService.getTarifUnitaire(idVoyageDetails));
            mv.addObject("idVoyageDetails", idVoyageDetails);
            mv.addObject("modePaiement",paiementService.getListModePaiements());
            mv.addObject("errorMessage", e.getMessage());
            return mv;
        }

    }
}
