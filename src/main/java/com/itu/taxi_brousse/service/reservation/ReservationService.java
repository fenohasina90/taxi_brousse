package com.itu.taxi_brousse.service.reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itu.taxi_brousse.entity.client.CategorieClient;
import com.itu.taxi_brousse.entity.client.Client;
import com.itu.taxi_brousse.entity.core.Trajet;
import com.itu.taxi_brousse.entity.reservation.Reservation;
import com.itu.taxi_brousse.entity.reservation.ReservationDetails;
import com.itu.taxi_brousse.entity.reservation.ReservationSiege;
import com.itu.taxi_brousse.entity.reservation.ReservationStatus;
import com.itu.taxi_brousse.entity.tarif.TarifActuel;
import com.itu.taxi_brousse.entity.voyage.TypeVoyage;
import com.itu.taxi_brousse.entity.voyage.VoyageDetails;
import com.itu.taxi_brousse.entity.voyage.VoyageDetailsPlaceType;
import com.itu.taxi_brousse.entity.voyage.VoyagePassager;
import com.itu.taxi_brousse.projection.ReservationListProjection;
import com.itu.taxi_brousse.repository.client.CategorieClientRepository;
import com.itu.taxi_brousse.repository.client.ClientRepository;
import com.itu.taxi_brousse.repository.reservation.ReservationDetailsRepository;
import com.itu.taxi_brousse.repository.reservation.ReservationRepository;
import com.itu.taxi_brousse.repository.reservation.ReservationSiegeRepository;
import com.itu.taxi_brousse.repository.reservation.ReservationStatusRepository;
import com.itu.taxi_brousse.repository.voyage.VoyageDetailsRepository;
import com.itu.taxi_brousse.repository.voyage.VoyageDetailsPlaceTypeRepository;
import com.itu.taxi_brousse.service.paiement.paiementService;
import com.itu.taxi_brousse.service.tarif.RemiseTarifService;
import com.itu.taxi_brousse.service.tarif.TarifService;
import com.itu.taxi_brousse.service.voyage.VoyagePassagerService;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationSiegeRepository reservationSiegeRepository;
    private final ReservationDetailsRepository reservationDetailsRepository;
    private final ClientRepository clientRepository;
    private final CategorieClientRepository categorieClientRepository;
    private final VoyageDetailsRepository voyageDetailsRepository;
    private final ReservationStatusRepository reservationStatusRepository; 
    private final paiementService paiementService;
    private final VoyagePassagerService voyagePassagerService;
    private final TarifService tarifService;
    private final RemiseTarifService remiseTarifService;
    private final VoyageDetailsPlaceTypeRepository voyageDetailsPlaceTypeRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationSiegeRepository reservationSiegeRepository,
                              ReservationDetailsRepository reservationDetailsRepository,
                              ClientRepository clientRepository,
                              CategorieClientRepository categorieClientRepository,
                              VoyageDetailsRepository voyageDetailsRepository,
                              ReservationStatusRepository reservationStatusRepository,
                              paiementService paiementService,
                              VoyagePassagerService voyagePassagerService,
                              TarifService tarifService,
                              RemiseTarifService remiseTarifService,
                              VoyageDetailsPlaceTypeRepository voyageDetailsPlaceTypeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationSiegeRepository = reservationSiegeRepository;
        this.reservationDetailsRepository = reservationDetailsRepository;
        this.clientRepository = clientRepository;
        this.categorieClientRepository = categorieClientRepository;
        this.voyageDetailsRepository = voyageDetailsRepository;
        this.reservationStatusRepository = reservationStatusRepository;
        this.paiementService = paiementService;
        this.voyagePassagerService = voyagePassagerService;
        this.tarifService = tarifService;
        this.remiseTarifService = remiseTarifService;
        this.voyageDetailsPlaceTypeRepository = voyageDetailsPlaceTypeRepository;
    }

    public String getTarifParPlaceJson(Integer idVoyageDetails) {
        VoyageDetails voyageDetails = voyageDetailsRepository.findById(idVoyageDetails)
                .orElseThrow(() -> new IllegalArgumentException("Voyage introuvable"));

        Trajet trajet = voyageDetails.getVoyage().getTrajet();

        Map<Integer, Double> map = voyageDetailsPlaceTypeRepository
                .findAllByVoyageDetailsId(idVoyageDetails)
                .stream()
                .collect(Collectors.toMap(
                        VoyageDetailsPlaceType::getNumeroPlace,
                        vpt -> {
                            TypeVoyage type = vpt.getTypeVoyage();
                            TarifActuel tarif = tarifService.getTarifActuel(trajet, type);
                            return tarif != null ? tarif.getMontant() : 0.0;
                        }
                ));

        String json = map.entrySet().stream()
                .map(e -> "\"" + e.getKey() + "\":" + e.getValue())
                .collect(Collectors.joining(","));

        return "{" + json + "}";
    }

    public String getTarifParPlaceEtCategorieJson(Integer idVoyageDetails) {
        VoyageDetails voyageDetails = voyageDetailsRepository.findById(idVoyageDetails)
                .orElseThrow(() -> new IllegalArgumentException("Voyage introuvable"));
        Trajet trajet = voyageDetails.getVoyage().getTrajet();
        List<VoyageDetailsPlaceType> mappings =
                voyageDetailsPlaceTypeRepository.findAllByVoyageDetailsId(idVoyageDetails);
        List<CategorieClient> categories = categorieClientRepository.findAll();
        Map<Integer, Map<Integer, Double>> map = new HashMap<>();
        for (VoyageDetailsPlaceType vpt : mappings) {
            int numeroPlace = vpt.getNumeroPlace();
            TypeVoyage type = vpt.getTypeVoyage();
            TarifActuel tarif = tarifService.getTarifActuel(trajet, type);
            if (tarif == null) {
                continue;
            }
            Map<Integer, Double> parCategorie = new HashMap<>();
            for (CategorieClient cat : categories) {
                BigDecimal montant = remiseTarifService.getTarifPourCategorie(tarif, cat);
                parCategorie.put(cat.getId(), montant.doubleValue());
            }
            map.put(numeroPlace, parCategorie);
        }
        String json = map.entrySet().stream()
                .map(e -> {
                    String inner = e.getValue().entrySet().stream()
                            .map(c -> "\"" + c.getKey() + "\":" + c.getValue())
                            .collect(Collectors.joining(","));
                    return "\"" + e.getKey() + "\":{" + inner + "}";
                })
                .collect(Collectors.joining(","));
        return "{" + json + "}";
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
                                        List<Integer> numerosPlaces,
                                        Integer idTypePaiement,
                                        double montantVerse) {

        if (numerosPlaces == null || numerosPlaces.isEmpty()) {
            throw new IllegalArgumentException("Au moins une place doit etre selectionnee");
        }

        double montantTotal = calculerMontantTotal(idVoyageDetails, numerosPlaces);

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
        reservation.setTotalAmount(montantVerse);



        // if (montantTotal != montantVerse || montantVerse == 0.0) {
        //     ReservationStatus status = reservationStatusRepository.findByStatusIgnoreCase("EN ATTENTE").orElse(null);
        //     reservation.setStatus(status);
        // } else {
            ReservationStatus status = reservationStatusRepository.findByStatusIgnoreCase("CONFIRME").orElse(null);
            reservation.setStatus(status);
        // }

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

    /**
     * Variante de creation de reservation qui prend en compte la categorie
     * de client par siege pour appliquer eventuellement des remises.
     */
    @Transactional
    public Reservation creerReservationAvecCategories(Integer idVoyageDetails,
                                                      String nomClient,
                                                      String contactClient,
                                                      List<Integer> numerosPlaces,
                                                      Map<Integer, Integer> categorieParPlace,
                                                      Integer idTypePaiement,
                                                      double montantVerse) {

        if (numerosPlaces == null || numerosPlaces.isEmpty()) {
            throw new IllegalArgumentException("Au moins une place doit etre selectionnee");
        }

        double montantTotal = calculerMontantTotalAvecCategories(idVoyageDetails, numerosPlaces, categorieParPlace);

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
        reservation.setTotalAmount(montantVerse);

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
            siege = reservationSiegeRepository.save(siege);

            Integer idCategorie = categorieParPlace != null ? categorieParPlace.get(numeroPlace) : null;
            if (idCategorie != null) {
                CategorieClient categorieClient = categorieClientRepository.findById(idCategorie)
                        .orElseThrow(() -> new IllegalArgumentException("Categorie client introuvable"));

                ReservationDetails details = new ReservationDetails();
                details.setReservationSiege(siege);
                details.setCategorieClient(categorieClient);
                reservationDetailsRepository.save(details);
            }
        }

        VoyagePassager voyagePassager = voyagePassagerService.saveVoyagePassager(voyageDetails, client, reservation, numerosPlaces);
        if (idTypePaiement != null) {
            paiementService.effectuerPaiement(voyagePassager, idTypePaiement, montantVerse);
        }

        return reservation;
    }

    private double calculerMontantTotal(Integer idVoyageDetails, List<Integer> numerosPlaces) {
        VoyageDetails voyageDetails = voyageDetailsRepository.findById(idVoyageDetails)
                .orElseThrow(() -> new IllegalArgumentException("Voyage introuvable"));

        Trajet trajet = voyageDetails.getVoyage().getTrajet();
        double total = 0.0;

        for (Integer numeroPlace : numerosPlaces) {
            VoyageDetailsPlaceType mapping = voyageDetailsPlaceTypeRepository
                    .findByVoyageDetailsIdAndNumeroPlace(idVoyageDetails, numeroPlace)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Type de place non configure pour le siege " + numeroPlace));

            TypeVoyage typePlace = mapping.getTypeVoyage();
            TarifActuel tarif = tarifService.getTarifActuel(trajet, typePlace);
            if (tarif == null) {
                throw new IllegalArgumentException("Tarif introuvable pour le type de place " + typePlace.getDescription());
            }

            total += tarif.getMontant();
        }

        return total;
    }

    private double calculerMontantTotalAvecCategories(Integer idVoyageDetails,
                                                       List<Integer> numerosPlaces,
                                                       Map<Integer, Integer> categorieParPlace) {
        VoyageDetails voyageDetails = voyageDetailsRepository.findById(idVoyageDetails)
                .orElseThrow(() -> new IllegalArgumentException("Voyage introuvable"));

        Trajet trajet = voyageDetails.getVoyage().getTrajet();
        double total = 0.0;

        for (Integer numeroPlace : numerosPlaces) {
            VoyageDetailsPlaceType mapping = voyageDetailsPlaceTypeRepository
                    .findByVoyageDetailsIdAndNumeroPlace(idVoyageDetails, numeroPlace)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Type de place non configure pour le siege " + numeroPlace));

            TypeVoyage typePlace = mapping.getTypeVoyage();
            TarifActuel tarif = tarifService.getTarifActuel(trajet, typePlace);
            if (tarif == null) {
                throw new IllegalArgumentException("Tarif introuvable pour le type de place " + typePlace.getDescription());
            }

            double montantPourPlace;
            Integer idCategorie = categorieParPlace != null ? categorieParPlace.get(numeroPlace) : null;
            if (idCategorie != null) {
                CategorieClient categorieClient = categorieClientRepository.findById(idCategorie)
                        .orElseThrow(() -> new IllegalArgumentException("Categorie client introuvable"));
                BigDecimal montantRemise = remiseTarifService.getTarifPourCategorie(tarif, categorieClient);
                montantPourPlace = montantRemise.doubleValue();
            } else {
                montantPourPlace = tarif.getMontant();
            }

            total += montantPourPlace;
        }

        return total;
    }
}
