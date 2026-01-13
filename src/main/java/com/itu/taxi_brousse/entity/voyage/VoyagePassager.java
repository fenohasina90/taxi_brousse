package com.itu.taxi_brousse.entity.voyage;

import com.itu.taxi_brousse.entity.client.Client;
import com.itu.taxi_brousse.entity.reservation.Reservation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "voyage_passager")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoyagePassager {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotNull(message = "Les détails du voyage sont obligatoires")
    @ManyToOne
    @JoinColumn(name = "id_voyage_details", nullable = false)
    private VoyageDetails voyageDetails;
    
    @NotNull(message = "Le client est obligatoire")
    @ManyToOne
    @JoinColumn(name = "id_client", nullable = false)
    private Client client;
    
    @NotNull(message = "La réservation est obligatoire")
    @ManyToOne
    @JoinColumn(name = "id_reservation", nullable = false)
    private Reservation reservation;
}

