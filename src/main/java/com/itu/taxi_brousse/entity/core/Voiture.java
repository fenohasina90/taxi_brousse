package com.itu.taxi_brousse.entity.core;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "voiture")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Voiture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "immatricule", nullable = false, unique = true)
    private String immatricule;

    @Column(name = "nb_place", nullable = false)
    private int nbPlace;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}

