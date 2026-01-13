package com.itu.taxi_brousse.entity.reservation;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.itu.taxi_brousse.entity.client.Client;
import com.itu.taxi_brousse.entity.voyage.VoyageDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_voyage_details", nullable = false)
    private VoyageDetails voyageDetails;
    
    @ManyToOne
    @JoinColumn(name = "id_client", nullable = false)
    private Client client;
    
    @Min(value = 1, message = "Le nombre de places doit être supérieur à 0")
    @Column(name = "nb_place", nullable = false)
    private Integer nbPlace;
    
    @DecimalMin(value = "0.00", inclusive = true, message = "Le montant total ne peut pas être négatif")
    @Column(name = "total_amount", nullable = false)
    private double totalAmount;
    
    @ManyToOne
    @JoinColumn(name = "id_status", nullable = false)
    private ReservationStatus status;
    
    @Column(name = "date_reservation")
    private LocalDateTime dateReservation;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    // Méthodes utilitaires
    public void setDateReservation(LocalDateTime dateReservation) {
        this.dateReservation = dateReservation;
        if (this.dateReservation == null) {
            this.dateReservation = LocalDateTime.now();
        }
    }
    
}

