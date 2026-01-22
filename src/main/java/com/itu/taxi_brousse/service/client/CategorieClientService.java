package com.itu.taxi_brousse.service.client;

import java.util.List;

import org.springframework.stereotype.Service;

import com.itu.taxi_brousse.entity.client.CategorieClient;
import com.itu.taxi_brousse.repository.client.CategorieClientRepository;

@Service
public class CategorieClientService {

    private final CategorieClientRepository categorieClientRepository;

    public CategorieClientService(CategorieClientRepository categorieClientRepository) {
        this.categorieClientRepository = categorieClientRepository;
    }

    public List<CategorieClient> getAll() {
        return categorieClientRepository.findAll();
    }
}
