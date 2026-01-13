package com.itu.taxi_brousse.entity.voyage;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "voyage_passager_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoyagePassagerDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotNull(message = "Le voyage passager est obligatoire")
    @ManyToOne
    @JoinColumn(name = "id_voyage_passager", nullable = false)
    private VoyagePassager voyagePassager;
    
    @NotNull(message = "Le numéro de place est obligatoire")
    @Min(value = 1, message = "Le numéro de place doit être au moins 1")
    @Column(name = "numero_place", nullable = false)
    private Integer numeroPlace;
}
