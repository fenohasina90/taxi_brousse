package com.itu.taxi_brousse.projection;

import java.time.LocalDate;
import java.time.LocalTime;


public interface VoyageListeProjection {
    Integer getIdVoyage();
    Integer getIdVoyageDetails();
    String getParcours();
    Integer getIdGareDepart();
    String getGareDepart();
    String getVilleDepart();
    Integer getIdGareArrivee();
    String getGareArrivee();
    String getVilleArrivee();
    String getImmatricule();
    Integer getCapaciteTotale();
    Integer getPlacesDisponibles();
    String getDateDepart();
    LocalDate getDateVoyage();
    LocalTime getHeureDepart();
    String getTypeVoyage();
    Integer getIdTypeVoyage();
    double getTarif();
    String getDistance();
    String getDureeEstimee();
}
