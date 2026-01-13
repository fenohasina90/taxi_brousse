package com.itu.taxi_brousse.projection;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public interface ReservationListProjection {
    Integer getIdReservation();
    String getNomClient();
    String getContactClient();
    Integer getIdGareDepart();
    Integer getIdGareArrivee();
    String getTrajet();
    LocalDate getDateVoyage();
    String getDateDepart();
    LocalTime getHeureDepart();
    LocalDateTime getDateReservation();
    String getReservationDate();
    Double getTotalAmount();
    String getStatusReservation();
    Integer getNbPlaceReserve();
    String getImmatricule();
    Integer getCapaciteTotale();
    String getTypeVoyage();
    Double getTarifUnitaire();
}
