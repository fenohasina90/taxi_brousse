package com.itu.taxi_brousse.repository.voyage;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.itu.taxi_brousse.entity.voyage.VoyageDetails;

@Repository
public interface VoyageDetailsRepository extends JpaRepository<VoyageDetails, Integer> {  

    @Query("SELECT COUNT(vd) FROM VoyageDetails vd " +
           "WHERE vd.voiture.id = :idVoiture " +
           "AND vd.voyage.dateVoyage = :dateVoyage " +
           "AND vd.heureDepart = :heureDepart")
    long countConflitsVoiture(@Param("idVoiture") Integer idVoiture,
                              @Param("dateVoyage") LocalDate dateVoyage,
                              @Param("heureDepart") LocalTime heureDepart);
    // @Query("""
    //     SELECT new com.itu.taxi_brousse.dto.VoyageDepartListeDTO(
    //         t.villeDepart.nom,
    //         t.villeArrivee.nom,
    //         v.immatricule,
    //         sv.nom,
    //         CAST(vd.dateHeureDepart AS date),
    //         CAST(vd.dateHeureDepart AS time)
    //     )
    //     FROM VoyageDepart vd
    //     JOIN vd.trajet t
    //     JOIN vd.voiture v
    //     JOIN vd.status sv
    //     WHERE (:villeDepart IS NULL OR t.villeDepart.id = :villeDepart)
    //     AND (:villeArrivee IS NULL OR t.villeArrivee.id = :villeArrivee)
    //     AND (:status IS NULL OR sv.id = :status)
    //     AND vd.dateHeureDepart >= :dateTimeDebut
    //     AND vd.dateHeureDepart <= :dateTimeFin
    //     ORDER BY vd.dateHeureDepart ASC
    // """)
    // List<VoyageDepartListeDTO> rechercherVoyages(
    //         @Param("villeDepart") Integer villeDepart,
    //         @Param("villeArrivee") Integer villeArrivee,
    //         @Param("status") Integer status,
    //         @Param("dateTimeDebut") LocalDateTime dateTimeDebut,
    //         @Param("dateTimeFin") LocalDateTime dateTimeFin
    // );
}
