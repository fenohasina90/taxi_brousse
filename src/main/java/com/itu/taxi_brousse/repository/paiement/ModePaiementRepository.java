package com.itu.taxi_brousse.repository.paiement;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itu.taxi_brousse.entity.paiement.ModePaiement;

public interface ModePaiementRepository extends JpaRepository<ModePaiement, Integer> {}
