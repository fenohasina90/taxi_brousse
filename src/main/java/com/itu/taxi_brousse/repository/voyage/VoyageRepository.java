package com.itu.taxi_brousse.repository.voyage;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.itu.taxi_brousse.entity.voyage.Voyage;
import com.itu.taxi_brousse.projection.VoyageListeProjection;
import com.itu.taxi_brousse.projection.VoyagePlanningProjection;
import com.itu.taxi_brousse.projection.VoyageDetailsPlanningProjection;

@Repository
public interface VoyageRepository extends JpaRepository<Voyage, Integer> {
    @Query(value = """
        SELECT * FROM v_recherche_voyage WHERE 1=1
        AND (CAST(:idGareDepart AS TEXT) IS NULL OR id_gare_depart = CAST(:idGareDepart AS INTEGER))
        AND (CAST(:idGareArrivee AS TEXT) IS NULL OR id_gare_arrivee = CAST(:idGareArrivee AS INTEGER))
        AND (CAST(:dateDebut AS TEXT) IS NULL OR date_voyage >= CAST(:dateDebut AS DATE))
        AND (CAST(:dateFin AS TEXT) IS NULL OR date_voyage <= CAST(:dateFin AS DATE))
        AND (CAST(:heureDebut AS TEXT) IS NULL OR heure_depart >= CAST(:heureDebut AS TIME))
        AND (CAST(:heureFin AS TEXT) IS NULL OR heure_depart <= CAST(:heureFin AS TIME))
        AND (CAST(:tarifMin AS TEXT) IS NULL OR tarif >= CAST(:tarifMin AS DECIMAL))
        AND (CAST(:tarifMax AS TEXT) IS NULL OR tarif <= CAST(:tarifMax AS DECIMAL))
        AND (CAST(:placesMin AS TEXT) IS NULL OR places_disponibles >= CAST(:placesMin AS INTEGER))
        AND (CAST(:placesMax AS TEXT) IS NULL OR places_disponibles <= CAST(:placesMax AS INTEGER))
        AND (CAST(:idTypeVoyage AS TEXT) IS NULL OR id_type_voyage = CAST(:idTypeVoyage AS INTEGER))
        AND (
        (CAST(:dateDebut AS TEXT) IS NOT NULL OR CAST(:dateFin AS TEXT) IS NOT NULL)
        OR 
        (CAST(:dateDebut AS TEXT) IS NULL AND CAST(:dateFin AS TEXT) IS NULL AND date_voyage >= CURRENT_DATE))
        """, nativeQuery = true)
    List<VoyageListeProjection> rechercherVoyages(
        @Param("idGareDepart") Integer idGareDepart,
        @Param("idGareArrivee") Integer idGareArrivee,
        @Param("dateDebut") LocalDate dateDebut,
        @Param("dateFin") LocalDate dateFin,
        @Param("heureDebut") LocalTime heureDebut,
        @Param("heureFin") LocalTime heureFin,
        @Param("tarifMin") Double tarifMin,
        @Param("tarifMax") Double tarifMax,
        @Param("placesMin") Integer placesMin,
        @Param("placesMax") Integer placesMax,
        @Param("idTypeVoyage") Integer idTypeVoyage
    );

    @Query(value = """
        SELECT * FROM v_voyage_planning
        WHERE 1=1
        AND (CAST(:idGareDepart AS TEXT) IS NULL OR id_gare_depart = CAST(:idGareDepart AS INTEGER))
        AND (CAST(:idGareArrivee AS TEXT) IS NULL OR id_gare_arrivee = CAST(:idGareArrivee AS INTEGER))
        AND (CAST(:dateDebut AS TEXT) IS NULL OR date_voyage >= CAST(:dateDebut AS DATE))
        AND (CAST(:dateFin AS TEXT) IS NULL OR date_voyage <= CAST(:dateFin AS DATE))
        AND (CAST(:heureDebut AS TEXT) IS NULL OR premiere_heure_depart >= CAST(:heureDebut AS TIME))
        AND (CAST(:heureFin AS TEXT) IS NULL OR premiere_heure_depart <= CAST(:heureFin AS TIME))
        AND (
        (CAST(:dateDebut AS TEXT) IS NOT NULL OR CAST(:dateFin AS TEXT) IS NOT NULL)
        OR 
        (CAST(:dateDebut AS TEXT) IS NULL AND CAST(:dateFin AS TEXT) IS NULL AND date_voyage >= CURRENT_DATE)
        )
        ORDER BY date_voyage DESC, premiere_heure_depart ASC
        """, nativeQuery = true)
    List<VoyagePlanningProjection> listerVoyagesPlanning(
            @Param("idGareDepart") Integer idGareDepart,
            @Param("idGareArrivee") Integer idGareArrivee,
            @Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin,
            @Param("heureDebut") LocalTime heureDebut,
            @Param("heureFin") LocalTime heureFin
    );

    @Query(value = """
        SELECT * FROM v_voyage_details_planning
        WHERE id_voyage = :idVoyage
        ORDER BY heure_depart
        """, nativeQuery = true)
    List<VoyageDetailsPlanningProjection> listerDetailsPlanning(@Param("idVoyage") Integer idVoyage);
}