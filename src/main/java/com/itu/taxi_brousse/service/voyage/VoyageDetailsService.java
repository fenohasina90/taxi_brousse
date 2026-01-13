package com.itu.taxi_brousse.service.voyage;

import java.time.LocalTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itu.taxi_brousse.entity.core.Voiture;
import com.itu.taxi_brousse.entity.voyage.TypeVoyage;
import com.itu.taxi_brousse.entity.voyage.Voyage;
import com.itu.taxi_brousse.entity.voyage.VoyageDetails;
import com.itu.taxi_brousse.repository.core.VoitureRepository;
import com.itu.taxi_brousse.repository.voyage.TypeVoyageRepository;
import com.itu.taxi_brousse.repository.voyage.VoyageDetailsRepository;
import com.itu.taxi_brousse.repository.voyage.VoyageRepository;

@Service
public class VoyageDetailsService {

    private final VoyageDetailsRepository voyageDetailsRepository;
    private final VoyageRepository voyageRepository;
    private final VoitureRepository voitureRepository;
    private final TypeVoyageRepository typeVoyageRepository;

    public VoyageDetailsService(VoyageDetailsRepository voyageDetailsRepository,
                                VoyageRepository voyageRepository,
                                VoitureRepository voitureRepository,
                                TypeVoyageRepository typeVoyageRepository) {
        this.voyageDetailsRepository = voyageDetailsRepository;
        this.voyageRepository = voyageRepository;
        this.voitureRepository = voitureRepository;
        this.typeVoyageRepository = typeVoyageRepository;
    }

    @Transactional
    public VoyageDetails creerVoyageDetails(Integer idVoyage,
                                            Integer idVoiture,
                                            Integer idTypeVoyage,
                                            LocalTime heureDepart) {

        Voyage voyage = voyageRepository.findById(idVoyage)
                .orElseThrow(() -> new IllegalArgumentException("Voyage introuvable"));

        Voiture voiture = voitureRepository.findById(idVoiture)
                .orElseThrow(() -> new IllegalArgumentException("Voiture introuvable"));

        TypeVoyage typeVoyage = typeVoyageRepository.findById(idTypeVoyage)
                .orElseThrow(() -> new IllegalArgumentException("Type de voyage introuvable"));

        long conflits = voyageDetailsRepository.countConflitsVoiture(
                voiture.getId(), voyage.getDateVoyage(), heureDepart
        );

        if (conflits > 0) {
            throw new IllegalStateException("La voiture sélectionnée n'est pas disponible pour cette date et heure.");
        }

        VoyageDetails details = new VoyageDetails();
        details.setVoyage(voyage);
        details.setVoiture(voiture);
        details.setTypeVoyage(typeVoyage);
        details.setHeureDepart(heureDepart);

        return voyageDetailsRepository.save(details);
    }
}
