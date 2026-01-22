package com.itu.taxi_brousse.repository.reservation;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itu.taxi_brousse.entity.reservation.ReservationDetails;

public interface ReservationDetailsRepository extends JpaRepository<ReservationDetails, Integer> {
}
