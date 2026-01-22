package com.itu.taxi_brousse.entity.tarif;

import java.math.BigDecimal;

import com.itu.taxi_brousse.entity.client.CategorieClient;

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
@Table(name = "remise_tarif")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RemiseTarif {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_tarif_actuel", nullable = false)
    private TarifActuel tarifActuel;

    @ManyToOne
    @JoinColumn(name = "id_categorie_client", nullable = false)
    private CategorieClient categorieClient;

    @Column(name = "pourcentage", nullable = false)
    private BigDecimal pourcentage;

    @Column(name = "montant")
    private BigDecimal montant;
}
