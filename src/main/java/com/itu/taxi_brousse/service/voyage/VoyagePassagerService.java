package com.itu.taxi_brousse.service.voyage;

import java.util.List;

import org.springframework.stereotype.Service;

import com.itu.taxi_brousse.entity.client.Client;
import com.itu.taxi_brousse.entity.reservation.Reservation;
import com.itu.taxi_brousse.entity.voyage.VoyageDetails;
import com.itu.taxi_brousse.entity.voyage.VoyagePassager;
import com.itu.taxi_brousse.entity.voyage.VoyagePassagerDetails;
import com.itu.taxi_brousse.repository.voyage.VoyagePassagerDetailsRepository;
import com.itu.taxi_brousse.repository.voyage.VoyagePassagerRepository;

@Service
public class VoyagePassagerService {
    private final VoyagePassagerDetailsRepository voyagePassagerDetailsRepository;
    private final VoyagePassagerRepository voyagePassagerRepository;

    public VoyagePassagerService(VoyagePassagerDetailsRepository voyagePassagerDetailsRepository,
                                 VoyagePassagerRepository voyagePassagerRepository) {
        this.voyagePassagerDetailsRepository = voyagePassagerDetailsRepository;
        this.voyagePassagerRepository = voyagePassagerRepository;
    }

    public VoyagePassager saveVoyagePassager(VoyageDetails voyageDetailse, Client client, Reservation reservation, List<Integer> numerosPlaces) {
        VoyagePassager voyagePassager = new VoyagePassager();
        voyagePassager.setVoyageDetails(voyageDetailse);
        voyagePassager.setClient(client);
        voyagePassager.setReservation(reservation);
        voyagePassager = voyagePassagerRepository.save(voyagePassager);   

        for (Integer numeroPlace : numerosPlaces) {
            VoyagePassagerDetails voyagePassagerDetails = new VoyagePassagerDetails();
            voyagePassagerDetails.setVoyagePassager(voyagePassager);
            voyagePassagerDetails.setNumeroPlace(numeroPlace);
            voyagePassagerDetailsRepository.save(voyagePassagerDetails);    
        }

        return voyagePassager;
    }
}