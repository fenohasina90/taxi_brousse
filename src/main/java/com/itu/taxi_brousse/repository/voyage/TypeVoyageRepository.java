package com.itu.taxi_brousse.repository.voyage;

import org.springframework.data.jpa.repository.JpaRepository;
import com.itu.taxi_brousse.entity.voyage.TypeVoyage;

public interface TypeVoyageRepository extends JpaRepository<TypeVoyage, Integer> { 
}
