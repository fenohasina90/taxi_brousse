package com.itu.taxi_brousse.service.core;

import java.util.List;

import org.springframework.stereotype.Service;

import com.itu.taxi_brousse.entity.core.Voiture;
import com.itu.taxi_brousse.repository.core.VoitureRepository;

@Service
public class VoitureService {
    private final VoitureRepository voitureRepository;

    public VoitureService(VoitureRepository voitureRepository) {
        this.voitureRepository = voitureRepository;
    }

    public List<Voiture> getToutesLesVoitures() {
        return voitureRepository.findAll();
    }
}
