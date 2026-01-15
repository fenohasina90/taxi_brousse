package com.itu.taxi_brousse.repository.voyage;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itu.taxi_brousse.entity.voyage.VoyageDetailsPlaceType;

public interface VoyageDetailsPlaceTypeRepository extends JpaRepository<VoyageDetailsPlaceType, Integer> {

    Optional<VoyageDetailsPlaceType> findByVoyageDetailsIdAndNumeroPlace(Integer idVoyageDetails, Integer numeroPlace);

    void deleteByVoyageDetailsId(Integer idVoyageDetails);

    List<VoyageDetailsPlaceType> findAllByVoyageDetailsId(Integer idVoyageDetails);
}
