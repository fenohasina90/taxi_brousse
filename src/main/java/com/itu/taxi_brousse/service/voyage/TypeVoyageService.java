package com.itu.taxi_brousse.service.voyage;

import java.util.List;

import org.springframework.stereotype.Service;

import com.itu.taxi_brousse.entity.voyage.TypeVoyage;
import com.itu.taxi_brousse.repository.voyage.TypeVoyageRepository;

@Service
public class TypeVoyageService {
    private final TypeVoyageRepository typeVoyageRepository;

    public TypeVoyageService(TypeVoyageRepository typeVoyageRepository) {
        this.typeVoyageRepository = typeVoyageRepository;
    }

    public List<TypeVoyage> getAllTypesVoyage() {
        return typeVoyageRepository.findAll();
    }
}
