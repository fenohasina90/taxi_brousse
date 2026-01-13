package com.itu.taxi_brousse.service.tarif;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.itu.taxi_brousse.entity.core.Trajet;
import com.itu.taxi_brousse.entity.tarif.HistoriqueTarif;
import com.itu.taxi_brousse.entity.tarif.TarifActuel;
import com.itu.taxi_brousse.entity.voyage.TypeVoyage;
import com.itu.taxi_brousse.projection.TarifListeProjection;
import com.itu.taxi_brousse.repository.core.TrajetRepository;
import com.itu.taxi_brousse.repository.tarif.HistoriqueTarifRepository;
import com.itu.taxi_brousse.repository.tarif.TarifActuelRepository;
import com.itu.taxi_brousse.repository.voyage.TypeVoyageRepository;

import jakarta.transaction.Transactional;

@Service
public class TarifService {
    private final TarifActuelRepository tarifActuelRepository;
    private final HistoriqueTarifRepository historiqueTarifRepository;
    private final TrajetRepository trajetRepository;
    private final TypeVoyageRepository typeVoyageRepository;

    public TarifService(TarifActuelRepository tarifActuelRepository, HistoriqueTarifRepository historiqueTarifRepository, TrajetRepository trajetRepository, TypeVoyageRepository typeVoyageRepository) {
        this.tarifActuelRepository = tarifActuelRepository;
        this.historiqueTarifRepository = historiqueTarifRepository;
        this.trajetRepository = trajetRepository;
        this.typeVoyageRepository = typeVoyageRepository;
    }

    public TarifActuel getTarifActuel(Trajet trajet, TypeVoyage typeVoyage) {
        return tarifActuelRepository.findTarifByTrajetAndTypeVoyageNative(trajet.getId(), typeVoyage.getId()).orElse(null);
    }

    @Transactional
    public void creerTarifsPourTrajet(Integer idTrajet, Map<Integer, Double> montantsParType) {
        Trajet trajet = trajetRepository.findById(idTrajet)
                .orElseThrow(() -> new IllegalArgumentException("Trajet introuvable"));
        for (Map.Entry<Integer, Double> e : montantsParType.entrySet()) {
            Integer idTypeVoyage = e.getKey();
            Double montant = e.getValue();
            if (montant == null || montant <= 0) continue;
            TypeVoyage typeVoyage = typeVoyageRepository.findById(idTypeVoyage)
                    .orElseThrow(() -> new IllegalArgumentException("Type de voyage introuvable"));
            // Vérif si un tarif existe déjà
            TarifActuel existant = tarifActuelRepository
                    .findTarifByTrajetAndTypeVoyageNative(trajet.getId(), typeVoyage.getId())
                    .orElse(null);
            if (existant != null) {
                throw new IllegalArgumentException("Un tarif existe deja pour ce trajet et ce type de voyage");
            }
            TarifActuel tarif = new TarifActuel();
            tarif.setTrajet(trajet);
            tarif.setTypeVoyage(typeVoyage);
            tarif.setMontant(montant);
            tarif.setCreatedAt(LocalDateTime.now());
            tarif = tarifActuelRepository.save(tarif);

            HistoriqueTarif hist = new HistoriqueTarif();
            hist.setTrajet(trajet);
            hist.setTypeVoyage(typeVoyage);
            hist.setAncienMontant(0.0);
            hist.setNouveauMontant(montant);
            hist.setCreatedAt(LocalDateTime.now());
            historiqueTarifRepository.save(hist);
        }
    }
    public List<TarifListeProjection> listerTarifsFiltres(
            LocalDateTime dateDebut,
            LocalDateTime dateFin,
            Integer idTypeVoyage,
            Integer idGareDepart,
            Integer idGareArrivee
    ) {
        return tarifActuelRepository.listerTarifsFiltres(
                dateDebut, dateFin, idTypeVoyage, idGareDepart, idGareArrivee
        );
    }
    public TarifActuel getById(Integer idTarif) {
        return tarifActuelRepository.findById(idTarif)
                .orElseThrow(() -> new IllegalArgumentException("Tarif introuvable"));
    }


    @Transactional
    public void modifierTarif(Integer idTarif, Double nouveauMontant) {
        TarifActuel tarif = getById(idTarif);
        Double ancien = tarif.getMontant();
        tarif.setMontant(nouveauMontant);
        tarif.setCreatedAt(LocalDateTime.now());
        tarifActuelRepository.save(tarif);
        HistoriqueTarif hist = new HistoriqueTarif();
        hist.setTrajet(tarif.getTrajet());
        hist.setTypeVoyage(tarif.getTypeVoyage());
        hist.setAncienMontant(ancien);
        hist.setNouveauMontant(nouveauMontant);
        hist.setCreatedAt(LocalDateTime.now());
        historiqueTarifRepository.save(hist);
    }
}