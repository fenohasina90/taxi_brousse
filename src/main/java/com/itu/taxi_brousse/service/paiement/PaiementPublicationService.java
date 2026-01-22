package com.itu.taxi_brousse.service.paiement;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itu.taxi_brousse.entity.paiement.PaiementPublication;
import com.itu.taxi_brousse.projection.VoyagePubPaiementProjection;
import com.itu.taxi_brousse.repository.paiement.PaiementPublicationRepository;
import com.itu.taxi_brousse.repository.voyage.VoyagePubRepository;

@Service
public class PaiementPublicationService {

    private final PaiementPublicationRepository paiementPublicationRepository;
    private final VoyagePubRepository voyagePubRepository;

    public PaiementPublicationService(PaiementPublicationRepository paiementPublicationRepository,
                                     VoyagePubRepository voyagePubRepository) {
        this.paiementPublicationRepository = paiementPublicationRepository;
        this.voyagePubRepository = voyagePubRepository;
    }

    public List<VoyagePubPaiementProjection> listerEtatPaiement(LocalDate dateDebut, LocalDate dateFin) {
        return voyagePubRepository.listerEtatPaiement(dateDebut, dateFin);
    }

    public VoyagePubPaiementProjection getEtatPaiement(Integer idVoyagePub) {
        return voyagePubRepository.getEtatPaiement(idVoyagePub);
    }

    @Transactional
    public void effectuerPaiement(Integer idVoyagePub, double montant, LocalDateTime datePaiement) {
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant doit etre > 0");
        }

        if (datePaiement == null) {
            throw new IllegalArgumentException("La date de paiement est obligatoire");
        }

        VoyagePubPaiementProjection etat = voyagePubRepository.getEtatPaiement(idVoyagePub);
        if (etat == null) {
            throw new IllegalArgumentException("Diffusion introuvable");
        }

        Double resteObj = etat.getResteAPayer();
        double reste = (resteObj != null) ? resteObj : 0.0;

        if (montant > reste) {
            throw new IllegalArgumentException("Montant trop eleve. Reste a payer: " + reste);
        }

        PaiementPublication pp = new PaiementPublication();
        pp.setVoyagePub(voyagePubRepository.getReferenceById(idVoyagePub));
        pp.setMontant(montant);
        pp.setDatePaiement(datePaiement);
        paiementPublicationRepository.save(pp);
    }
}
