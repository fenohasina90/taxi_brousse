package com.itu.taxi_brousse.repository.paiement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.itu.taxi_brousse.entity.paiement.PaiementPublication;

public interface PaiementPublicationRepository extends JpaRepository<PaiementPublication, Integer> {

    @Query(value = "SELECT COALESCE(SUM(montant), 0.00) FROM paiement_publication WHERE id_voyage_pub = :idVoyagePub", nativeQuery = true)
    Double sumMontantByVoyagePub(@Param("idVoyagePub") Integer idVoyagePub);
}
