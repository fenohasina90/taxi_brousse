ALTER TABLE voiture
    ADD COLUMN nb_place_premium int default 0 check (nb_place_premium >= 0),
    ADD COLUMN nb_place_standard int default 0 check (nb_place_standard >= 0);