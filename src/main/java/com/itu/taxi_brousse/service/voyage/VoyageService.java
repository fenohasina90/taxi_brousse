package com.itu.taxi_brousse.service.voyage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.itu.taxi_brousse.entity.core.Trajet;
import com.itu.taxi_brousse.entity.voyage.Voyage;
import com.itu.taxi_brousse.projection.VoyageListeProjection;
import com.itu.taxi_brousse.projection.VoyagePlanningProjection;
import com.itu.taxi_brousse.projection.VoyageDetailsPlanningProjection;
import com.itu.taxi_brousse.repository.core.TrajetRepository;
import com.itu.taxi_brousse.repository.voyage.VoyageRepository;

@Service
public class VoyageService {
    private final VoyageRepository voyageRepository;
    private final TrajetRepository trajetRepository;

    public VoyageService(VoyageRepository voyageRepository, TrajetRepository trajetRepository) {
        this.voyageRepository = voyageRepository;
        this.trajetRepository = trajetRepository;
    }

    public List<VoyageListeProjection> rechercherVoyages(
            Integer idGareDepart,
            Integer idGareArrivee,
            LocalDate dateDebut,
            LocalDate dateFin,
            LocalTime heureDebut,
            LocalTime heureFin,
            Double tarifMin,
            Double tarifMax,
            Integer placesMin,
            Integer placesMax,
            Integer idTypeVoyage) {
        
        return voyageRepository.rechercherVoyages(
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
        );
    }

    public Voyage creerVoyage(Integer idTrajet, LocalDate dateVoyage) {
        Trajet trajet = trajetRepository.findById(idTrajet)
                .orElseThrow(() -> new IllegalArgumentException("Trajet introuvable"));

        Voyage voyage = new Voyage();
        voyage.setTrajet(trajet);
        voyage.setDateVoyage(dateVoyage);

        return voyageRepository.save(voyage);
    }

    public List<VoyagePlanningProjection> listerVoyagesPlanning(
            Integer idGareDepart,
            Integer idGareArrivee,
            LocalDate dateDebut,
            LocalDate dateFin,
            LocalTime heureDebut,
            LocalTime heureFin
    ) {
        return voyageRepository.listerVoyagesPlanning(
                idGareDepart,
                idGareArrivee,
                dateDebut,
                dateFin,
                heureDebut,
                heureFin
        );
    }

    public Map<Integer, List<VoyageDetailsPlanningProjection>> listerDetailsPlanningParVoyage(List<Integer> idsVoyage) {
        return idsVoyage.stream()
                .collect(Collectors.toMap(
                        id -> id,
                        id -> voyageRepository.listerDetailsPlanning(id)
                ));
    }
}
