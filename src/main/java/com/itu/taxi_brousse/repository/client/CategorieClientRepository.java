package com.itu.taxi_brousse.repository.client;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itu.taxi_brousse.entity.client.CategorieClient;

public interface CategorieClientRepository extends JpaRepository<CategorieClient, Integer> {
}
