package com.itu.taxi_brousse.entity.paiement;

import java.time.LocalDateTime;

import com.itu.taxi_brousse.entity.voyage.VoyagePub;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "paiement_publication")
public class PaiementPublication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_voyage_pub", nullable = false)
    private VoyagePub voyagePub;

    @Column(nullable = false)
    private double montant;

    @Column(name = "date_paiement")
    private LocalDateTime datePaiement = LocalDateTime.now();

    public PaiementPublication() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public VoyagePub getVoyagePub() {
        return voyagePub;
    }

    public void setVoyagePub(VoyagePub voyagePub) {
        this.voyagePub = voyagePub;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public LocalDateTime getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDateTime datePaiement) {
        this.datePaiement = datePaiement;
    }
}
