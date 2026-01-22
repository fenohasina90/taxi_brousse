package com.itu.taxi_brousse.repository.voyage;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.itu.taxi_brousse.entity.voyage.VoyagePub;
import com.itu.taxi_brousse.projection.VoyagePubPaiementProjection;

@Repository
public interface VoyagePubRepository extends JpaRepository<VoyagePub, Integer> {

	@Query(value = """
		SELECT *
		FROM v_voyage_pub_paiement
		WHERE 1=1
		AND (CAST(:dateDebut AS TEXT) IS NULL OR date_voyage >= CAST(:dateDebut AS DATE))
		AND (CAST(:dateFin AS TEXT) IS NULL OR date_voyage <= CAST(:dateFin AS DATE))
		ORDER BY date_voyage DESC, heure_depart ASC
		""", nativeQuery = true)
	List<VoyagePubPaiementProjection> listerEtatPaiement(
			@Param("dateDebut") LocalDate dateDebut,
			@Param("dateFin") LocalDate dateFin
	);

	@Query(value = """
		SELECT *
		FROM v_voyage_pub_paiement
		WHERE id_voyage_pub = :idVoyagePub
		""", nativeQuery = true)
	VoyagePubPaiementProjection getEtatPaiement(@Param("idVoyagePub") Integer idVoyagePub);
}
