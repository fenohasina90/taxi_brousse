package com.itu.taxi_brousse.repository.voyage;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.itu.taxi_brousse.entity.voyage.Publication;
import com.itu.taxi_brousse.projection.PublicationDiffusionCaProjection;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Integer> {

	@Query(value = """
		SELECT *
		FROM v_ca_publication_diffusion
		WHERE 1=1
		AND (CAST(:dateDebut AS TEXT) IS NULL OR date_voyage >= CAST(:dateDebut AS DATE))
		AND (CAST(:dateFin AS TEXT) IS NULL OR date_voyage <= CAST(:dateFin AS DATE))
		ORDER BY date_voyage DESC, chiffre_affaires DESC
		""", nativeQuery = true)
	List<PublicationDiffusionCaProjection> listerChiffreAffaireDiffusion(
			@Param("dateDebut") LocalDate dateDebut,
			@Param("dateFin") LocalDate dateFin
	);

	@Query(value = """
		SELECT COALESCE(SUM(chiffre_affaires), 0.00)
		FROM v_ca_publication_diffusion
		WHERE 1=1
		AND (CAST(:dateDebut AS TEXT) IS NULL OR date_voyage >= CAST(:dateDebut AS DATE))
		AND (CAST(:dateFin AS TEXT) IS NULL OR date_voyage <= CAST(:dateFin AS DATE))
		""", nativeQuery = true)
	Double sommeChiffreAffaireDiffusion(
			@Param("dateDebut") LocalDate dateDebut,
			@Param("dateFin") LocalDate dateFin
	);
}
