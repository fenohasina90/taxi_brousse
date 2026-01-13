package com.itu.taxi_brousse.service.core;

import java.util.List;

import org.springframework.stereotype.Service;

import com.itu.taxi_brousse.entity.core.GareRoutiere;
import com.itu.taxi_brousse.entity.core.Trajet;
import com.itu.taxi_brousse.repository.core.TrajetRepository;

import jakarta.transaction.Transactional;

@Service
public class TrajetService {

    private final TrajetRepository trajetRepository;

    public TrajetService(TrajetRepository trajetRepository) {
        this.trajetRepository = trajetRepository;
    }

    public List<Trajet> getListeTrajets() {
        return trajetRepository.findAll();
    }

    @Transactional
    public Trajet creerTrajet(Integer idGareDepart, Integer idGareArrivee,
                            Double distanceKm, Integer estimationHeure) {
        if (idGareDepart.equals(idGareArrivee)) {
            throw new IllegalArgumentException("La gare de depart et d'arrivee doivent etre differentes");
        }
        boolean existe = trajetRepository
                .findAll()
                .stream()
                .anyMatch(t -> t.getGareDepart().getId().equals(idGareDepart)
                            && t.getGareArrivee().getId().equals(idGareArrivee));
        if (existe) {
            throw new IllegalArgumentException("Ce trajet existe deja");
        }
        Trajet trajet = new Trajet();
        GareRoutiere gareDep = new GareRoutiere();
        gareDep.setId(idGareDepart);
        GareRoutiere gareArr = new GareRoutiere();
        gareArr.setId(idGareArrivee);
        trajet.setGareDepart(gareDep);
        trajet.setGareArrivee(gareArr);
        trajet.setDistanceKm(distanceKm);
        trajet.setEstimationHeure(estimationHeure);
        return trajetRepository.save(trajet);
    }
}

