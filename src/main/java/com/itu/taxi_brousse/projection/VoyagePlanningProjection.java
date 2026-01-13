package com.itu.taxi_brousse.projection;

import java.time.LocalDate;
import java.time.LocalTime;

public interface VoyagePlanningProjection {
    Integer getIdVoyage();
    Integer getIdGareDepart();
    Integer getIdGareArrivee();
    String getTrajet();
    LocalDate getDateVoyage();
    String getDateDepart(); 
    LocalTime getPremiereHeureDepart();
    Long getTotalVoyageDetails();
    Double getTotalChiffreAffaire();
}
