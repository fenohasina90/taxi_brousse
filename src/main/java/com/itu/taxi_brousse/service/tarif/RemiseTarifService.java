package com.itu.taxi_brousse.service.tarif;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.itu.taxi_brousse.entity.client.CategorieClient;
import com.itu.taxi_brousse.entity.tarif.RemiseTarif;
import com.itu.taxi_brousse.entity.tarif.TarifActuel;
import com.itu.taxi_brousse.repository.tarif.RemiseTarifRepository;

@Service
public class RemiseTarifService {

    private final RemiseTarifRepository remiseTarifRepository;

    public RemiseTarifService(RemiseTarifRepository remiseTarifRepository) {
        this.remiseTarifRepository = remiseTarifRepository;
    }

    /**
     * Retourne le tarif applicable pour une categorie de client.
     * Si une remise (en pourcentage) est definie, on applique la formule :
     *   montant = montant_tarif_actuel - ((montant_tarif_actuel * pourcentage)/100)
     * Sinon on renvoie le tarif normal.
     */
    public BigDecimal getTarifPourCategorie(TarifActuel tarifActuel, CategorieClient categorieClient) {
        if (tarifActuel == null || categorieClient == null) {
            return BigDecimal.ZERO;
        }

        if (tarifActuel != null && categorieClient == null) {
            return BigDecimal.valueOf(tarifActuel.getMontant());
        }

        BigDecimal montantBase = BigDecimal.valueOf(tarifActuel.getMontant());

        return remiseTarifRepository
                .findByTarifActuelAndCategorieClient(tarifActuel, categorieClient)
                .map(remise -> {
                    // Cas 1 : montant explicite defini (ex: tarif enfant propre)
                    if (categorieClient.getNom().equalsIgnoreCase("senior")) {
                        BigDecimal reduction = montantBase
                            .multiply(BigDecimal.valueOf(20))
                            .divide(BigDecimal.valueOf(100));
                        return montantBase.subtract(reduction); 
                    }

                    if (remise.getMontant() != null) {
                        return remise.getMontant();
                    }

                    // Cas 2 : remise en pourcentage (ex: senior = adulte - 20%)
                    BigDecimal pourcentage = remise.getPourcentage();
                    if (pourcentage == null) {
                        return montantBase;
                    }
                    BigDecimal reduction = montantBase
                            .multiply(pourcentage)
                            .divide(BigDecimal.valueOf(100));
                    return montantBase.subtract(reduction);
                })
                .orElse(montantBase);
    }

    public List<RemiseTarif> findAll() {
        return remiseTarifRepository.findAll();
    }
    public Optional<RemiseTarif> findById(Integer id) {
        return remiseTarifRepository.findById(id);
    }
    public RemiseTarif save(RemiseTarif remiseTarif) {
        return remiseTarifRepository.save(remiseTarif);
    }
    public void deleteById(Integer id) {
        remiseTarifRepository.deleteById(id);
    }
}
