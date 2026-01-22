package com.itu.taxi_brousse.repository.voyage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itu.taxi_brousse.entity.voyage.VoyagePub;

@Repository
public interface VoyagePubRepository extends JpaRepository<VoyagePub, Integer> {}
