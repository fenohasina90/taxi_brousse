package com.itu.taxi_brousse.entity.voyage;

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
@Table(name = "voyage_pub")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoyagePub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_publication")
    private Publication publication;

    @Column(name = "nb_repetition")
    private Integer nbRepetition;

    @ManyToOne
    @JoinColumn(name = "id_voyage_details")
    private VoyageDetails voyageDetails;
}
