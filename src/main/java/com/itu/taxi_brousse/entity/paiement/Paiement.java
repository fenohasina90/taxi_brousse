package com.itu.taxi_brousse.entity.paiement;

import java.time.LocalDateTime;

import com.itu.taxi_brousse.entity.voyage.VoyagePassager;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "paiement")
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_voyage_passager", nullable = false)
    private VoyagePassager voyagePassager;

    @ManyToOne
    @JoinColumn(name = "id_mode_paiement", nullable = false)
    private ModePaiement modePaiement;

    @Column(nullable = false)
    private double montant;

    @Column(name = "date_paiement")
    private LocalDateTime datePaiement = LocalDateTime.now();
}

