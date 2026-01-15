package com.itu.taxi_brousse.entity.voyage;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "voyage_details_place_type")
public class VoyageDetailsPlaceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_voyage_details", nullable = false)
    private VoyageDetails voyageDetails;

    @NotNull
    private Integer numeroPlace;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_type_voyage", nullable = false)
    private TypeVoyage typeVoyage;

    public VoyageDetailsPlaceType() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public VoyageDetails getVoyageDetails() {
        return voyageDetails;
    }

    public void setVoyageDetails(VoyageDetails voyageDetails) {
        this.voyageDetails = voyageDetails;
    }

    public Integer getNumeroPlace() {
        return numeroPlace;
    }

    public void setNumeroPlace(Integer numeroPlace) {
        this.numeroPlace = numeroPlace;
    }

    public TypeVoyage getTypeVoyage() {
        return typeVoyage;
    }

    public void setTypeVoyage(TypeVoyage typeVoyage) {
        this.typeVoyage = typeVoyage;
    }
}
