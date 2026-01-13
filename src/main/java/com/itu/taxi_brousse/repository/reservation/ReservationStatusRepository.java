package com.itu.taxi_brousse.repository.reservation;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itu.taxi_brousse.entity.reservation.ReservationStatus;

@Repository
public interface ReservationStatusRepository extends JpaRepository<ReservationStatus, Integer> {

    Optional<ReservationStatus> findByStatusIgnoreCase(String status);
}
