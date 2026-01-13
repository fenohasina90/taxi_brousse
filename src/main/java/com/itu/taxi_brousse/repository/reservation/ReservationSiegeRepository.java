package com.itu.taxi_brousse.repository.reservation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.itu.taxi_brousse.entity.reservation.ReservationSiege;

@Repository
public interface ReservationSiegeRepository extends JpaRepository<ReservationSiege, Integer> {   
    @Query(value = """
        SELECT s.numero_place
        FROM generate_series(1, (
            SELECT vo.nb_place 
            FROM voyage_details vd
            JOIN voiture vo ON vd.id_voiture = vo.id
            WHERE vd.id = :idVoyageDetails
        )) AS s(numero_place)
        WHERE s.numero_place NOT IN (
            SELECT rs.numero_place
            FROM reservation r
            JOIN reservation_siege rs ON r.id = rs.id_reservation
            WHERE r.id_voyage_details = :idVoyageDetails
            AND r.id_status != (SELECT id FROM reservation_status WHERE status = 'ANNULE')
        )        
    """, nativeQuery = true)
    List<Integer> trouverPlacesDisponibles(@Param("idVoyageDetails") Integer idVoyageDetails);

    @Query("SELECT rs.numeroPlace FROM ReservationSiege rs WHERE rs.reservation.id = :idReservation")
    List<Integer> findNumerosByReservationId(@Param("idReservation") Integer idReservation);
}
