package com.itu.taxi_brousse.projection;

import java.time.LocalTime;

public interface VoyageDetailsPlanningProjection {
    Integer getIdVoyage();
    Integer getIdVoyageDetails();
    LocalTime getHeureDepart();
    String getVoiture();
    Integer getNbPlaceTotal();
    Integer getPlacesDisponibles();
    Long getNombreReservations();
    String getTypeVoyage();
    Double getTarif();
    Double getTotalChiffreAffaire();
    Double getMaxChiffreAffaire();
}
