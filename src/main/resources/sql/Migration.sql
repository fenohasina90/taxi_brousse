ALTER TABLE tarif_actuel
    ADD COLUMN montant_enfant decimal(15,2) not null check (montant > 0);