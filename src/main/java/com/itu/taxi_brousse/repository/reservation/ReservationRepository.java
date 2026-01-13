package com.itu.taxi_brousse.repository.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.itu.taxi_brousse.entity.reservation.Reservation;
import com.itu.taxi_brousse.projection.ReservationListProjection;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    
    @Query(value = """
        SELECT *
        FROM v_reservation_liste
        WHERE 1=1
        AND (CAST(:idGareDepart AS TEXT) IS NULL OR id_gare_depart = CAST(:idGareDepart AS INTEGER))
        AND (CAST(:idGareArrivee AS TEXT) IS NULL OR id_gare_arrivee = CAST(:idGareArrivee AS INTEGER))
        AND (CAST(:dateDebut AS TEXT) IS NULL OR date_voyage >= CAST(:dateDebut AS DATE))
        AND (CAST(:dateFin AS TEXT) IS NULL OR date_voyage <= CAST(:dateFin AS DATE))
        AND (CAST(:heureDebut AS TEXT) IS NULL OR heure_depart >= CAST(:heureDebut AS TIME))
        AND (CAST(:heureFin AS TEXT) IS NULL OR heure_depart <= CAST(:heureFin AS TIME))
        ORDER BY date_voyage DESC, heure_depart ASC, id_reservation DESC
        """, nativeQuery = true)
    List<ReservationListProjection> listerReservationsFiltrees(
            @Param("idGareDepart") Integer idGareDepart,
            @Param("idGareArrivee") Integer idGareArrivee,
            @Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin,
            @Param("heureDebut") LocalTime heureDebut,
            @Param("heureFin") LocalTime heureFin
    );
}
