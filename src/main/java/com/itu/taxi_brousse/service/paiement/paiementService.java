package com.itu.taxi_brousse.service.paiement;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.itu.taxi_brousse.entity.paiement.ModePaiement;
import com.itu.taxi_brousse.entity.paiement.Paiement;
import com.itu.taxi_brousse.entity.voyage.VoyagePassager;
import com.itu.taxi_brousse.repository.paiement.ModePaiementRepository;
import com.itu.taxi_brousse.repository.paiement.PaiementRepository;

@Service
public class paiementService {
    private final PaiementRepository paiementRepository;
    private final ModePaiementRepository modePaiementRepository;

    public paiementService(PaiementRepository paiementRepository,
                           ModePaiementRepository modePaiementRepository) {
        this.paiementRepository = paiementRepository;
        this.modePaiementRepository = modePaiementRepository;
    }

    public List<ModePaiement> getListModePaiements(){
        return modePaiementRepository.findAll();
    }

    public void effectuerPaiement(VoyagePassager voyagePassager, Integer modePaiement, double montantVerse) {
        Paiement paiement = new Paiement();
        paiement.setDatePaiement(LocalDateTime.now());
        paiement.setMontant(montantVerse);
        paiement.setModePaiement(modePaiementRepository.findById(modePaiement).orElse(null));
        paiement.setVoyagePassager(voyagePassager);
        paiementRepository.save(paiement);
    }
}
