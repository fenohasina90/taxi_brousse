package com.itu.taxi_brousse.projection;

import java.time.LocalDate;

public interface PublicationDiffusionCaProjection {
    LocalDate getDateVoyage();
    Integer getIdPublication();
    String getTitre();
    Integer getIdSociete();
    String getSociete();
    Long getTotalRepetition();
    Double getMontantUnitaire();
    Double getChiffreAffaires();
}
