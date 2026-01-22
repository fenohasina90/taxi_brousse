package com.itu.taxi_brousse.projection;

import java.time.LocalDate;
import java.time.LocalTime;

public interface VoyagePubPaiementProjection {
    Integer getIdVoyagePub();
    LocalDate getDateVoyage();
    LocalTime getHeureDepart();
    Integer getIdPublication();
    String getTitre();
    Integer getIdSociete();
    String getSociete();
    Integer getNbRepetition();
    Double getMontantUnitaire();
    Double getTotalAPayer();
    Double getMontantPaye();
    Double getResteAPayer();
}
