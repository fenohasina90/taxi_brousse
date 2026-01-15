package com.itu.taxi_brousse.service.voyage;

import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itu.taxi_brousse.entity.core.Voiture;
import com.itu.taxi_brousse.entity.voyage.TypeVoyage;
import com.itu.taxi_brousse.entity.voyage.Voyage;
import com.itu.taxi_brousse.entity.voyage.VoyageDetails;
import com.itu.taxi_brousse.entity.voyage.VoyageDetailsPlaceType;
import com.itu.taxi_brousse.repository.core.VoitureRepository;
import com.itu.taxi_brousse.repository.voyage.TypeVoyageRepository;
import com.itu.taxi_brousse.repository.voyage.VoyageDetailsPlaceTypeRepository;
import com.itu.taxi_brousse.repository.voyage.VoyageDetailsRepository;
import com.itu.taxi_brousse.repository.voyage.VoyageRepository;

@Service
public class VoyageDetailsService {

    private final VoyageDetailsRepository voyageDetailsRepository;
    private final VoyageRepository voyageRepository;
    private final VoitureRepository voitureRepository;
    private final TypeVoyageRepository typeVoyageRepository;
    private final VoyageDetailsPlaceTypeRepository voyageDetailsPlaceTypeRepository;

    public VoyageDetailsService(VoyageDetailsRepository voyageDetailsRepository,
                                VoyageRepository voyageRepository,
                                VoitureRepository voitureRepository,
                                TypeVoyageRepository typeVoyageRepository,
                                VoyageDetailsPlaceTypeRepository voyageDetailsPlaceTypeRepository) {
        this.voyageDetailsRepository = voyageDetailsRepository;
        this.voyageRepository = voyageRepository;
        this.voitureRepository = voitureRepository;
        this.typeVoyageRepository = typeVoyageRepository;
        this.voyageDetailsPlaceTypeRepository = voyageDetailsPlaceTypeRepository;
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

    @Transactional
    public void configurerPlaces(Integer idVoyageDetails,
                                 List<Integer> numeros,
                                 Integer idTypeVoyage) {
        VoyageDetails vd = voyageDetailsRepository.findById(idVoyageDetails)
                .orElseThrow(() -> new IllegalArgumentException("Voyage introuvable"));
        TypeVoyage typeVoyage = typeVoyageRepository.findById(idTypeVoyage)
                .orElseThrow(() -> new IllegalArgumentException("Type de voyage introuvable"));
        for (Integer num : numeros) {
            VoyageDetailsPlaceType mapping = new VoyageDetailsPlaceType();
            mapping.setVoyageDetails(vd);
            mapping.setNumeroPlace(num);
            mapping.setTypeVoyage(typeVoyage);
            voyageDetailsPlaceTypeRepository.save(mapping);
        }
    }

    @Transactional
    public void supprimerConfigurationPlaces(Integer idVoyageDetails) {
        voyageDetailsPlaceTypeRepository.deleteByVoyageDetailsId(idVoyageDetails);
    }

        public VoyageDetails getById(Integer id) {
                return voyageDetailsRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Voyage introuvable"));
        }

}
