package com.itu.taxi_brousse.entity.tarif;

import java.time.LocalDateTime;

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
@Table(name = "tarif_actuel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TarifActuel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_trajet", nullable = false)
    private Trajet trajet;
    
    @ManyToOne
    @JoinColumn(name = "id_type_voyage", nullable = false)
    private TypeVoyage typeVoyage;
    
    @Column(name = "montant", nullable = false)
    private double montant;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
