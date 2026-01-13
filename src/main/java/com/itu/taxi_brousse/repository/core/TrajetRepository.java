package com.itu.taxi_brousse.repository.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itu.taxi_brousse.entity.core.Trajet;

@Repository
public interface TrajetRepository extends JpaRepository<Trajet, Integer> {
}