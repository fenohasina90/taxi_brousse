package com.itu.taxi_brousse.entity.reservation;

import com.itu.taxi_brousse.entity.client.CategorieClient;

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
@Table(name = "reservation_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_reservation_siege", nullable = false)
    private ReservationSiege reservationSiege;

    @ManyToOne
    @JoinColumn(name = "id_categorie_client", nullable = false)
    private CategorieClient categorieClient;
}
