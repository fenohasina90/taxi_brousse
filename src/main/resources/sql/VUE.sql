-- Vue de synthèse des voyages pour la page de planning
CREATE OR REPLACE VIEW v_voyage_planning AS
SELECT 
    v.id AS id_voyage,
    gd.id AS id_gare_depart,
    ga.id AS id_gare_arrivee,
    CONCAT(gd.ville, ' → ', ga.ville) AS trajet,
    v.date_voyage,
    TO_CHAR(v.date_voyage, 'DD/MM/YYYY') AS date_depart,
    MIN(vd.heure_depart) AS premiere_heure_depart,
    COUNT(DISTINCT vd.id) AS total_voyage_details,
    COALESCE(SUM(CASE 
        WHEN rs.status != 'ANNULE' THEN r.total_amount 
        ELSE 0 
    END), 0) AS total_chiffre_affaire
FROM voyage v
JOIN trajet t ON v.id_trajet = t.id
JOIN gare_routiere gd ON t.gare_depart = gd.id
JOIN gare_routiere ga ON t.gare_arrivee = ga.id
LEFT JOIN voyage_details vd ON v.id = vd.id_voyage
LEFT JOIN voiture vo ON vd.id_voiture = vo.id
LEFT JOIN type_voyage tv ON vd.id_type_voyage = tv.id
LEFT JOIN reservation r ON vd.id = r.id_voyage_details
LEFT JOIN reservation_status rs ON r.id_status = rs.id
GROUP BY 
    v.id, gd.id, ga.id, CONCAT(gd.ville, ' → ', ga.ville), v.date_voyage;

-- Vue détaillée par voyage_details pour l'affichage dans le modal
CREATE OR REPLACE VIEW v_voyage_details_planning AS
SELECT 
    v.id AS id_voyage,
    vd.id AS id_voyage_details,
    vd.heure_depart,
    vo.immatricule AS voiture,
    vo.nb_place AS nb_place_total,
    vo.nb_place - COALESCE(SUM(
        CASE 
            WHEN rs.status IN ('CONFIRME', 'COMPLET', 'EN ATTENTE') 
            THEN r.nb_place 
            ELSE 0 
        END
    ), 0) AS places_disponibles,
    COALESCE(SUM(CASE WHEN rs.status != 'ANNULE' THEN r.nb_place ELSE 0 END), 0) AS nombre_reservations,
    tv.description AS type_voyage,
    COALESCE(ta.montant, 0.00) AS tarif,
    COALESCE(SUM(CASE 
        WHEN rs.status != 'ANNULE' THEN r.total_amount 
        ELSE 0 
    END), 0.00) AS total_chiffre_affaire,
    (
        SELECT COALESCE(SUM(ta2.montant), 0.00)
        FROM voyage_details_place_type vpt
        JOIN type_voyage tv2 ON vpt.id_type_voyage = tv2.id
        JOIN voyage_details vd2 ON vd2.id = vpt.id_voyage_details
        JOIN voyage v2 ON v2.id = vd2.id_voyage
        JOIN trajet t2 ON t2.id = v2.id_trajet
        JOIN tarif_actuel ta2 ON ta2.id_trajet = t2.id AND ta2.id_type_voyage = tv2.id
        WHERE vpt.id_voyage_details = vd.id
    ) AS max_chiffre_affaire
FROM voyage v
JOIN trajet t ON v.id_trajet = t.id
JOIN voyage_details vd ON v.id = vd.id_voyage
JOIN voiture vo ON vd.id_voiture = vo.id
JOIN type_voyage tv ON vd.id_type_voyage = tv.id
LEFT JOIN tarif_actuel ta ON t.id = ta.id_trajet AND tv.id = ta.id_type_voyage
LEFT JOIN reservation r ON vd.id = r.id_voyage_details
LEFT JOIN reservation_status rs ON r.id_status = rs.id
GROUP BY 
    v.id, vd.id, vd.heure_depart, vo.immatricule, vo.nb_place, tv.description, ta.montant;

-- Resultat Recherche Voyage
CREATE OR REPLACE VIEW v_recherche_voyage AS
SELECT 
    v.id AS id_voyage,
    vd.id AS id_voyage_details,
    CONCAT(gd.ville, ' → ', ga.ville) AS parcours,
    gd.id AS id_gare_depart,
    gd.nom AS gare_depart,
    gd.ville AS ville_depart,
    ga.id AS id_gare_arrivee,
    ga.nom AS gare_arrivee,
    ga.ville AS ville_arrivee,
    vo.immatricule,
    vo.nb_place AS capacite_totale,
    vo.nb_place - COALESCE(SUM(CASE WHEN rs.status != 'ANNULE' THEN r.nb_place ELSE 0 END), 0) AS places_disponibles,
    TO_CHAR(v.date_voyage, 'DD/MM/YYYY') AS date_depart,
    v.date_voyage AS date_voyage,
    vd.heure_depart AS heure_depart,
    tv.description AS type_voyage,
    tv.id AS id_type_voyage,
    COALESCE(ta.montant, 0.00) AS tarif,
    t.distance_km || ' km' AS distance,
    t.estimation_heure || ' h' AS duree_estimee
FROM voyage v
JOIN trajet t ON v.id_trajet = t.id
JOIN gare_routiere gd ON t.gare_depart = gd.id
JOIN gare_routiere ga ON t.gare_arrivee = ga.id
JOIN voyage_details vd ON v.id = vd.id_voyage
JOIN voiture vo ON vd.id_voiture = vo.id
JOIN type_voyage tv ON vd.id_type_voyage = tv.id
LEFT JOIN tarif_actuel ta ON t.id = ta.id_trajet AND tv.id = ta.id_type_voyage
LEFT JOIN reservation r ON vd.id = r.id_voyage_details
LEFT JOIN reservation_status rs ON r.id_status = rs.id
GROUP BY 
    v.id, gd.id, gd.nom, ga.id, ga.nom, 
    vo.immatricule, vo.nb_place, v.date_voyage, 
    vd.heure_depart, tv.description, tv.id, 
    ta.montant, vd.id, t.distance_km, t.estimation_heure
-- HAVING vo.nb_place - COALESCE(SUM(CASE WHEN rs.status != 'ANNULE' THEN r.nb_place ELSE 0 END), 0) > 0
ORDER BY v.date_voyage, vd.heure_depart;

-- Vue de liste des reservations avec infos voyage pour filtres et details
CREATE OR REPLACE VIEW v_reservation_liste AS
SELECT 
    r.id AS id_reservation,
    c.nom AS nom_client,
    c.contact AS contact_client,
    gd.id AS id_gare_depart,
    ga.id AS id_gare_arrivee,
    CONCAT(gd.ville, ' → ', ga.ville) AS trajet,
    v.date_voyage,
    TO_CHAR(v.date_voyage, 'DD/MM/YYYY') AS date_depart,
    vd.heure_depart,
    r.date_reservation,
    TO_CHAR(r.date_reservation, 'DD/MM/YYYY - HH24:MI') AS reservation_date,
    r.total_amount,
    rs.status AS status_reservation,
    r.nb_place AS nb_place_reserve,
    vo.immatricule,
    vo.nb_place AS capacite_totale,
    tv.description AS type_voyage,
    COALESCE(ta.montant, 0.00) AS tarif_unitaire
FROM reservation r
JOIN client c ON r.id_client = c.id
JOIN reservation_status rs ON r.id_status = rs.id
JOIN voyage_details vd ON r.id_voyage_details = vd.id
JOIN voyage v ON vd.id_voyage = v.id
JOIN trajet t ON v.id_trajet = t.id
JOIN gare_routiere gd ON t.gare_depart = gd.id
JOIN gare_routiere ga ON t.gare_arrivee = ga.id
JOIN voiture vo ON vd.id_voiture = vo.id
JOIN type_voyage tv ON vd.id_type_voyage = tv.id
LEFT JOIN tarif_actuel ta ON t.id = ta.id_trajet AND tv.id = ta.id_type_voyage;


-- Vue de liste des tarifs actuels avec infos trajet et type voyage
CREATE OR REPLACE VIEW v_tarif_actuel_liste AS
SELECT
    ta.id AS id_tarif,
    t.id AS id_trajet,
    gd.id AS id_gare_depart,
    ga.id AS id_gare_arrivee,
    CONCAT(gd.ville, ' → ', ga.ville) AS trajet,
    tv.id AS id_type_voyage,
    tv.description AS type_voyage,
    ta.montant,
    ta.created_at AS date_creation, 
    TO_CHAR(ta.created_at, 'DD/MM/YYYY - HH24:MI') AS daty
FROM tarif_actuel ta
JOIN trajet t ON ta.id_trajet = t.id
JOIN gare_routiere gd ON t.gare_depart = gd.id
JOIN gare_routiere ga ON t.gare_arrivee = ga.id
JOIN type_voyage tv ON ta.id_type_voyage = tv.id;


-- Chiffre d'affaires des diffusions des publications (filtrer ensuite avec BETWEEN sur date_voyage)
CREATE OR REPLACE VIEW v_voyage_pub_paiement AS
SELECT
    vp.id AS id_voyage_pub,
    v.date_voyage,
    vd.heure_depart,
    p.id AS id_publication,
    p.titre,
    s.id AS id_societe,
    s.nom AS societe,
    COALESCE(vp.nb_repetition, 0) AS nb_repetition,
    COALESCE(p.montant, 0.00) AS montant_unitaire,
    COALESCE(COALESCE(vp.nb_repetition, 0) * COALESCE(p.montant, 0.00), 0.00) AS total_a_payer,
    COALESCE(pp.montant_paye, 0.00) AS montant_paye,
    GREATEST(
        COALESCE(COALESCE(vp.nb_repetition, 0) * COALESCE(p.montant, 0.00), 0.00) - COALESCE(pp.montant_paye, 0.00),
        0.00
    ) AS reste_a_payer
FROM voyage_pub vp
JOIN publication p ON vp.id_publication = p.id
LEFT JOIN societe s ON p.id_societe = s.id
JOIN voyage_details vd ON vp.id_voyage_details = vd.id
JOIN voyage v ON vd.id_voyage = v.id
LEFT JOIN (
    SELECT id_voyage_pub, SUM(montant) AS montant_paye
    FROM paiement_publication
    GROUP BY id_voyage_pub
) pp ON pp.id_voyage_pub = vp.id;


CREATE OR REPLACE VIEW v_ca_publication_diffusion AS
SELECT
    x.date_voyage,
    x.id_publication,
    x.titre,
    x.id_societe,
    x.societe,
    COALESCE(SUM(x.nb_repetition), 0) AS total_repetition,
    COALESCE(x.montant_unitaire, 0.00) AS montant_unitaire,
    COALESCE(SUM(x.total_a_payer), 0.00) AS chiffre_affaires,
    COALESCE(SUM(x.montant_paye), 0.00) AS montant_paye,
    COALESCE(SUM(x.reste_a_payer), 0.00) AS reste_a_payer
FROM v_voyage_pub_paiement x
GROUP BY
    x.date_voyage,
    x.id_publication,
    x.titre,
    x.id_societe,
    x.societe,
    x.montant_unitaire;