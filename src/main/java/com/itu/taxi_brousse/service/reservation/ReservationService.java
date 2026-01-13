package com.itu.taxi_brousse.service.reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itu.taxi_brousse.entity.client.Client;
import com.itu.taxi_brousse.entity.core.Trajet;
import com.itu.taxi_brousse.entity.reservation.Reservation;
import com.itu.taxi_brousse.entity.reservation.ReservationSiege;
import com.itu.taxi_brousse.entity.reservation.ReservationStatus;
import com.itu.taxi_brousse.entity.tarif.TarifActuel;
import com.itu.taxi_brousse.entity.voyage.TypeVoyage;
import com.itu.taxi_brousse.entity.voyage.VoyageDetails;
import com.itu.taxi_brousse.entity.voyage.VoyagePassager;
import com.itu.taxi_brousse.projection.ReservationListProjection;
import com.itu.taxi_brousse.repository.client.ClientRepository;
import com.itu.taxi_brousse.repository.reservation.ReservationRepository;
import com.itu.taxi_brousse.repository.reservation.ReservationSiegeRepository;
import com.itu.taxi_brousse.repository.reservation.ReservationStatusRepository;
import com.itu.taxi_brousse.repository.voyage.VoyageDetailsRepository;
import com.itu.taxi_brousse.service.paiement.paiementService;
import com.itu.taxi_brousse.service.tarif.TarifService;
import com.itu.taxi_brousse.service.voyage.VoyagePassagerService;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationSiegeRepository reservationSiegeRepository;
    private final ClientRepository clientRepository;
    private final VoyageDetailsRepository voyageDetailsRepository;
    private final ReservationStatusRepository reservationStatusRepository; 
    private final paiementService paiementService;
    private final VoyagePassagerService voyagePassagerService;
    private final TarifService tarifService;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationSiegeRepository reservationSiegeRepository,
                              ClientRepository clientRepository,
                              VoyageDetailsRepository voyageDetailsRepository,
                              ReservationStatusRepository reservationStatusRepository,
                              paiementService paiementService,
                              VoyagePassagerService voyagePassagerService,
                              TarifService tarifService) {
        this.reservationRepository = reservationRepository;
        this.reservationSiegeRepository = reservationSiegeRepository;
        this.clientRepository = clientRepository;
        this.voyageDetailsRepository = voyageDetailsRepository;
        this.reservationStatusRepository = reservationStatusRepository;
        this.paiementService = paiementService;
        this.voyagePassagerService = voyagePassagerService;
        this.tarifService = tarifService;
    }

    public List<ReservationListProjection> listerReservationsFiltrees(
            Integer idGareDepart,
            Integer idGareArrivee,
            LocalDate dateDebut,
            LocalDate dateFin,
            LocalTime heureDebut,
            LocalTime heureFin
    ) {
        return reservationRepository.listerReservationsFiltrees(
                idGareDepart,
                idGareArrivee,
                dateDebut,
                dateFin,
                heureDebut,
                heureFin
        );
    }

    public List<Integer> obtenirPlacesDisponibles(Integer idVoyageDetails) {
        return reservationSiegeRepository.trouverPlacesDisponibles(idVoyageDetails);
    }

    public Integer getCapaciteVoiture(Integer idVoyageDetails) {
        VoyageDetails voyageDetails = voyageDetailsRepository.findById(idVoyageDetails)
                .orElseThrow(() -> new IllegalArgumentException("Voyage introuvable"));
        return voyageDetails.getVoiture().getNbPlace();
    }

    public double getTarifUnitaire(Integer idVoyageDetails) {
        VoyageDetails voyageDetails = voyageDetailsRepository.findById(idVoyageDetails).orElse(null);
        Trajet trajet = voyageDetails.getVoyage().getTrajet();
        TypeVoyage typeVoyage = voyageDetails.getTypeVoyage();

        TarifActuel tarif = tarifService.getTarifActuel(trajet, typeVoyage);
        return tarif.getMontant();
    }

    public Map<Integer, List<Integer>> getPlacesReserveesParReservation(List<Integer> idsReservation) {
        Map<Integer, List<Integer>> resultat = new HashMap<>();
        if (idsReservation == null) {
            return resultat;
        }
        for (Integer id : idsReservation) {
            if (id != null) {
                resultat.put(id, reservationSiegeRepository.findNumerosByReservationId(id));
            }
        }
        return resultat;
    }

    @Transactional
    public void annulerReservation(Integer idReservation) {
        Reservation reservation = reservationRepository.findById(idReservation)
                .orElseThrow(() -> new IllegalArgumentException("Reservation introuvable"));

        ReservationStatus statusAnnule = reservationStatusRepository
                .findByStatusIgnoreCase("ANNULE")
                .orElseThrow(() -> new IllegalStateException("Statut ANNULE introuvable"));

        reservation.setStatus(statusAnnule);
        reservationRepository.save(reservation);
    }

    @Transactional
    public Reservation creerReservation(Integer idVoyageDetails,
                                        String nomClient,
                                        String contactClient,
                                        double montantTotal,
                                        List<Integer> numerosPlaces,
                                        Integer idTypePaiement,
                                        double montantVerse) {

        if (numerosPlaces == null || numerosPlaces.isEmpty()) {
            throw new IllegalArgumentException("Au moins une place doit etre selectionnee");
        }

        if (montantVerse > montantTotal) {
            throw new IllegalArgumentException("Le montant verse ne peut pas etre superieur au montant total");
        }

        VoyageDetails voyageDetails = voyageDetailsRepository.findById(idVoyageDetails)
                .orElseThrow(() -> new IllegalArgumentException("Voyage introuvable"));

        
        Client client = new Client();
        client.setNom(nomClient);
        client.setContact(contactClient);
        client = clientRepository.save(client);



        Reservation reservation = new Reservation();
        reservation.setVoyageDetails(voyageDetails);
        reservation.setClient(client);
        reservation.setNbPlace(numerosPlaces.size());
        reservation.setTotalAmount(montantTotal);



        if (montantTotal != montantVerse || montantVerse == 0.0) {
            ReservationStatus status = reservationStatusRepository.findByStatusIgnoreCase("EN ATTENTE").orElse(null);
            reservation.setStatus(status);
        } else {
            ReservationStatus status = reservationStatusRepository.findByStatusIgnoreCase("CONFIRME").orElse(null);
            reservation.setStatus(status);
        }

        reservation.setDateReservation(LocalDateTime.now());
        reservation = reservationRepository.save(reservation);

        for (Integer numeroPlace : numerosPlaces) {
            ReservationSiege siege = new ReservationSiege();
            siege.setReservation(reservation);
            siege.setNumeroPlace(numeroPlace);
            reservationSiegeRepository.save(siege);
        }

        VoyagePassager voyagePassager = voyagePassagerService.saveVoyagePassager(voyageDetails, client, reservation, numerosPlaces);
        if (idTypePaiement != null) {
            paiementService.effectuerPaiement(voyagePassager, idTypePaiement, montantVerse);
        }

        return reservation;
    }
}
