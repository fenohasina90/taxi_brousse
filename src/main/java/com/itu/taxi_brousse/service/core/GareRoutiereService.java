package com.itu.taxi_brousse.service.core;

import java.util.List;

import org.springframework.stereotype.Service;

import com.itu.taxi_brousse.entity.core.GareRoutiere;
import com.itu.taxi_brousse.repository.core.GareRoutiereRepository;

@Service
public class GareRoutiereService {
    private final GareRoutiereRepository gareRoutiereRepository;

    public GareRoutiereService(GareRoutiereRepository gareRoutiereRepository) {
        this.gareRoutiereRepository = gareRoutiereRepository;
    }

    public List<GareRoutiere> getAllGaresRoutieres() {
        return gareRoutiereRepository.findAll();
    }
}
