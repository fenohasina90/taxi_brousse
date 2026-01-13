package com.itu.taxi_brousse.repository.tarif;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.itu.taxi_brousse.entity.tarif.TarifActuel;
import com.itu.taxi_brousse.projection.TarifListeProjection;

public interface TarifActuelRepository extends JpaRepository<TarifActuel, Integer> {
   @Query(value = "SELECT * FROM tarif_actuel " +
                "WHERE id_trajet = :idTrajet " +
                "AND id_type_voyage = :idTypeVoyage", 
        nativeQuery = true)
    Optional<TarifActuel> findTarifByTrajetAndTypeVoyageNative(
        @Param("idTrajet") Integer idTrajet,
        @Param("idTypeVoyage") Integer idTypeVoyage
    );


    @Query(value = """
    SELECT *
    FROM v_tarif_actuel_liste
    WHERE 1=1
    AND (CAST(:dateDebut AS TEXT) IS NULL OR date_creation >= CAST(:dateDebut AS TIMESTAMP))
    AND (CAST(:dateFin AS TEXT) IS NULL OR date_creation <= CAST(:dateFin AS TIMESTAMP))
    AND (CAST(:idTypeVoyage AS TEXT) IS NULL OR id_type_voyage = CAST(:idTypeVoyage AS INTEGER))
    AND (CAST(:idGareDepart AS TEXT) IS NULL OR id_gare_depart = CAST(:idGareDepart AS INTEGER))
    AND (CAST(:idGareArrivee AS TEXT) IS NULL OR id_gare_arrivee = CAST(:idGareArrivee AS INTEGER))
    ORDER BY date_creation DESC
    """, nativeQuery = true)
    List<TarifListeProjection> listerTarifsFiltres(
            @Param("dateDebut") LocalDateTime dateDebut,
            @Param("dateFin") LocalDateTime dateFin,
            @Param("idTypeVoyage") Integer idTypeVoyage,
            @Param("idGareDepart") Integer idGareDepart,
            @Param("idGareArrivee") Integer idGareArrivee
    );
}
