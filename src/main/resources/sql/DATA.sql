INSERT INTO voiture (immatricule, nb_place) VALUES
('1234 TBA', 18),
('4565 TBR', 22),
('7894 TBC', 18),
('1476 DF', 22),
('2584 UD', 18),
('3698 ME', 22),
('7417 TBG', 18),
('8528 FE', 22),
('9639 TBK', 18),
('8651 AJ', 18),
('1591 TBJ', 22);

INSERT INTO gare_routiere (nom, ville, contact) VALUES
('Andohatapenaka', 'Antananarivo', '+261 34 12 345 67'),
('Ambodivona', 'Antananarivo', '+261 32 45 678 90'),
('Fasankarana', 'Antananarivo', '+261 38 98 765 43'),
('Bazary Be', 'Toamasina', '+261 34 56 789 01'),
('Morafeno', 'Toamasina', '+261 32 87 654 32'),
('Ambolomadinika', 'Toamasina', '+261 33 87 658 90'),
('Anjoma', 'Fianarantsoa', '+261 33 45 123 90'),
('Tanambao', 'Fianarantsoa', '+261 38 76 543 21'),
('Scama', 'Mahajanga', '+261 34 98 234 56'),
('Amborovy', 'Mahajanga', '+261 32 11 223 34'),
('Tsimenatse', 'Toliara', '+261 33 67 890 12'),
('Betania', 'Toliara', '+261 38 44 556 78'),
('Hell-Ville', 'Nosy Be', '+261 34 22 334 45'),
('Ambalavato', 'Antsiranana', '+261 32 66 778 89');


-- INSERT INTO jour_ferie (date_jour) VALUES
-- ('2026-01-01'), -- Nouvel An
-- ('2026-03-29'), -- Vendredi Saint
-- ('2026-04-01'), -- Lundi de Pâques
-- ('2026-05-01'), -- Fête du Travail
-- ('2026-05-09'), -- Ascension
-- ('2026-05-20'), -- Lundi de Pentecôte
-- ('2026-06-26'), -- Fête Nationale
-- ('2026-08-15'), -- Assomption
-- ('2026-11-01'), -- Toussaint
-- ('2026-12-25'); -- Noël

INSERT INTO type_voyage (description) VALUES
('VIP'),
('Premium'),
('Economique');

INSERT INTO mode_paiement (mode) VALUES
('Espèces'),
('MVola'),
('Airtel Money'),
('Orange Money');

-- Insertion des statuts de réservation
INSERT INTO reservation_status (status) VALUES
('EN ATTENTE'),
('COMPLET'),
('CONFIRME'),
('ANNULE');


INSERT INTO categorie_client(nom) VALUES
('Senior'),
('Adulte'),
('Enfant');

INSERT INTO remise_tarif(id_tarif_actuel, id_categorie_client, pourcentage ,montant) VALUES
(5, 3, 20, ((180000 * 20)/100)),
(6, 3, 20, ((140000 * 20)/100)),
(7, 3, 20, ((90000 * 20)/100));
(7, 2, 0, 50000);

INSERT INTO remise_tarif(id_tarif_actuel, id_categorie_client, pourcentage, montant) VALUES
(8, 2, 0, 65000),
(9, 2, 0, 50000),
(10, 2, 0, 40000);

INSERT INTO remise_tarif(id_tarif_actuel, id_categorie_client, pourcentage, montant) VALUES
(8, 1, 20, 70000),
(9, 1, 20, 60000),
(10, 1, 20, 50000); --

-- Insertion des sociétés
INSERT INTO societe (nom) VALUES
('Orinasa Malagasy'),
('Fitaratra Anjiro'),
('Trano Fivoarana'),
('Vola Malagasy'),
('Sakafo Matsilo');

-- Insertion des publications
INSERT INTO publication (titre, description, id_societe, montant) VALUES
('Trano fanofana', 'Trano fanofana tsara tarehy any amoron-dranomasina', 1, 100000.00);
INSERT INTO publication (titre, description, id_societe, montant) VALUES
('vqwerty', 'Trano fanofana tsara tarehy', 2, 100000.00);

--Insertion voyage pub
INSERT INTO voyage_pub (id_publication, nb_repetition, id_voyage_details) VALUES
(1, 20, 7),
(2, 10, 6);
