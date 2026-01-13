package com.itu.taxi_brousse.entity.voyage;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "type_voyage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TypeVoyage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "description", nullable = false, unique = true, length = 100)
    private String description;
}

