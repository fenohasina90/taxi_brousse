package com.itu.taxi_brousse.entity.tarif;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.itu.taxi_brousse.entity.core.Trajet;
import com.itu.taxi_brousse.entity.voyage.TypeVoyage;

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
@Table(name = "historique_tarif")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoriqueTarif {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "ancien_montant", nullable = false)
    private double ancienMontant;
    
    @Column(name = "nouveau_montant", nullable = false)
    private double nouveauMontant;
    
    @ManyToOne
    @JoinColumn(name = "id_trajet", nullable = false)
    private Trajet trajet;
    
    @ManyToOne
    @JoinColumn(name = "id_type_voyage", nullable = false)
    private TypeVoyage typeVoyage;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
