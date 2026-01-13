package com.itu.taxi_brousse.repository.paiement;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itu.taxi_brousse.entity.paiement.Paiement;

public interface PaiementRepository extends JpaRepository<Paiement, Integer> {}
