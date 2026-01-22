package com.itu.taxi_brousse.repository.tarif;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itu.taxi_brousse.entity.client.CategorieClient;
import com.itu.taxi_brousse.entity.tarif.RemiseTarif;
import com.itu.taxi_brousse.entity.tarif.TarifActuel;

public interface RemiseTarifRepository extends JpaRepository<RemiseTarif, Integer> {

    Optional<RemiseTarif> findByTarifActuelAndCategorieClient(TarifActuel tarifActuel, CategorieClient categorieClient);
}
