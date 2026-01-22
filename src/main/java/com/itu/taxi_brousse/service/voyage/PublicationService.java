package com.itu.taxi_brousse.service.voyage;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.itu.taxi_brousse.projection.PublicationDiffusionCaProjection;
import com.itu.taxi_brousse.repository.voyage.PublicationRepository;

@Service
public class PublicationService {

    private final PublicationRepository publicationRepository;

    public PublicationService(PublicationRepository publicationRepository) {
        this.publicationRepository = publicationRepository;
    }

    public List<PublicationDiffusionCaProjection> listerChiffreAffaireDiffusion(LocalDate dateDebut, LocalDate dateFin) {
        return publicationRepository.listerChiffreAffaireDiffusion(dateDebut, dateFin);
    }

    public Double sommeChiffreAffaireDiffusion(LocalDate dateDebut, LocalDate dateFin) {
        return publicationRepository.sommeChiffreAffaireDiffusion(dateDebut, dateFin);
    }
}
