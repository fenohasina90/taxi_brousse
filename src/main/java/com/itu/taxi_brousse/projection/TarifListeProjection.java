package com.itu.taxi_brousse.projection;

import java.time.LocalDateTime;

public interface TarifListeProjection {
    Integer getIdTarif();
    Integer getIdTrajet();
    Integer getIdGareDepart();
    Integer getIdGareArrivee();
    String getTrajet();
    Integer getIdTypeVoyage();
    String getTypeVoyage();
    Double getMontant();
    LocalDateTime getDateCreation();
    String getDaty();
}
