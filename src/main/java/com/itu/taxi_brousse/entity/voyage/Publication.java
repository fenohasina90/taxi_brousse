package com.itu.taxi_brousse.entity.voyage;

import java.math.BigDecimal;

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
@Table(name = "publication")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Publication {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "titre", length = 100)
	private String titre;

	@Column(name = "description", columnDefinition = "text")
	private String description;

	@ManyToOne
	@JoinColumn(name = "id_societe")
	private Societe societe;

	@Column(name = "montant", precision = 15, scale = 2)
	private BigDecimal montant;
}
