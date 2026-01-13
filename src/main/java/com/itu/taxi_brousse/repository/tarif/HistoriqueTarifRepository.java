package com.itu.taxi_brousse.repository.tarif;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itu.taxi_brousse.entity.tarif.HistoriqueTarif;

public interface HistoriqueTarifRepository extends JpaRepository<HistoriqueTarif, Integer> {
}
