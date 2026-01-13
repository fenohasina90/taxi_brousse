package com.itu.taxi_brousse.repository.core;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itu.taxi_brousse.entity.core.Voiture;

public interface VoitureRepository extends JpaRepository<Voiture, Integer> {
}
