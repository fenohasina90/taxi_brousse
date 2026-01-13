package com.itu.taxi_brousse.entity.reservation;

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
@Table(name = "reservation_siege")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationSiege {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_reservation", nullable = false)
    private Reservation reservation;
    
    @Column(name = "numero_place", nullable = false)
    private Integer numeroPlace;
}
