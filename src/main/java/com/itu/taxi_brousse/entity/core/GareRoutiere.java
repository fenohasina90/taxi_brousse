package com.itu.taxi_brousse.entity.core;

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
@Table(name = "gare_routiere")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GareRoutiere {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "nom", nullable = false)
    private String nom;
    
    @Column(name = "ville", nullable = false)
    private String ville;  
}
