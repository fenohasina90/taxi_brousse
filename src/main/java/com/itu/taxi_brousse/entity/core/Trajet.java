package com.itu.taxi_brousse.entity.core;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "trajet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Trajet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "gare_depart", nullable = false)
    private GareRoutiere gareDepart;

    @ManyToOne
    @JoinColumn(name = "gare_arrivee", nullable = false)
    private GareRoutiere gareArrivee;

    @Column(name = "distance_km")
    private double distanceKm;

    @Column(name = "estimation_heure")
    private int estimationHeure;
}

