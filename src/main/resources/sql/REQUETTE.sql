-- Trajet DTO
CREATE OR REPLACE VIEW v_trajet AS
SELECT 
    t.id as id_trajet,
    gd.nom || ' ' || gd.ville || ' → ' || ga.nom || ' ' || ga.ville as trajet,
    t.distance_km || ' km' as distance,
    t.estimation_heure || ' h' as duree_estimee,
    gd.id as id_gare_depart,
    ga.id as id_gare_arrivee,
    t.distance_km,
    t.estimation_heure
FROM trajet t
JOIN gare_routiere gd ON t.gare_depart = gd.id
JOIN gare_routiere ga ON t.gare_arrivee = ga.id;


-- Afficher tous les voyages programmés avec tous les détails
SELECT 
    v.id AS id_voyage,
    CONCAT(gd.ville, ' → ', ga.ville) AS parcours,
    gd.nom AS gare_depart,
    ga.nom AS gare_arrivee,
    vo.immatricule,
    vo.nb_place || ' places' AS capacite,
    v.date_voyage,
    vd.heure_depart,
    tv.description AS type_voyage,
    t.distance_km || ' km' AS distance,
    t.estimation_heure || ' h' AS duree,
    CASE 
        WHEN v.date_voyage > CURRENT_DATE THEN 'À venir'
        WHEN v.date_voyage = CURRENT_DATE THEN 'Aujourd''hui'
        ELSE 'Passé'
    END AS statut
FROM voyage v
JOIN trajet t ON v.id_trajet = t.id
JOIN gare_routiere gd ON t.gare_depart = gd.id
JOIN gare_routiere ga ON t.gare_arrivee = ga.id
JOIN voyage_details vd ON v.id = vd.id_voyage
JOIN voiture vo ON vd.id_voiture = vo.id
JOIN type_voyage tv ON vd.id_type_voyage = tv.id
ORDER BY v.date_voyage DESC, vd.heure_depart;


-- Nombre de voyages par type
SELECT 
    tv.description AS type_voyage,
    COUNT(*) AS nombre_voyages,
    STRING_AGG(DISTINCT gd.ville || '→' || ga.ville, ', ') AS trajets
FROM voyage_details vd
JOIN type_voyage tv ON vd.id_type_voyage = tv.id
JOIN voyage v ON vd.id_voyage = v.id
JOIN trajet t ON v.id_trajet = t.id
JOIN gare_routiere gd ON t.gare_depart = gd.id
JOIN gare_routiere ga ON t.gare_arrivee = ga.id
GROUP BY tv.id, tv.description
ORDER BY nombre_voyages DESC;

-- Voitures les plus utilisées
SELECT 
    vo.immatricule,
    vo.nb_place,
    COUNT(vd.id) AS nombre_voyages_assignes,
    MIN(v.date_voyage) AS premiere_utilisation,
    MAX(v.date_voyage) AS derniere_utilisation
FROM voiture vo
LEFT JOIN voyage_details vd ON vo.id = vd.id_voiture
LEFT JOIN voyage v ON vd.id_voyage = v.id
GROUP BY vo.id, vo.immatricule, vo.nb_place
ORDER BY nombre_voyages_assignes DESC;

-- Voyages par jour de la semaine
SELECT 
    TO_CHAR(v.date_voyage, 'Day') AS jour_semaine,
    COUNT(*) AS nombre_voyages,
    AVG(t.distance_km) AS distance_moyenne
FROM voyage v
JOIN trajet t ON v.id_trajet = t.id
GROUP BY TO_CHAR(v.date_voyage, 'Day'), EXTRACT(DOW FROM v.date_voyage)
ORDER BY EXTRACT(DOW FROM v.date_voyage);


-- pour voir toutes les réservations d'un voyage

SELECT 
    v.id AS id_voyage,
    CONCAT(gd.ville, ' → ', ga.ville) AS trajet,
    v.date_voyage,
    vd.heure_depart,
    vo.immatricule,
    tv.description AS type_voyage,
    -- Détails des réservations
    COALESCE(COUNT(DISTINCT r.id), 0) AS nombre_reservations,
    COALESCE(SUM(CASE WHEN rs.status = 'CONFIRME' THEN r.nb_place ELSE 0 END), 0) AS places_confirmees,
    COALESCE(SUM(CASE WHEN rs.status = 'EN ATTENTE' THEN r.nb_place ELSE 0 END), 0) AS places_en_attente,
    COALESCE(SUM(CASE WHEN rs.status = 'COMPLET' THEN r.nb_place ELSE 0 END), 0) AS places_completes,
    COALESCE(SUM(CASE WHEN rs.status = 'ANNULE' THEN r.nb_place ELSE 0 END), 0) AS places_annulees,
    -- Calcul disponibilité
    vo.nb_place AS capacite,
    vo.nb_place - COALESCE(SUM(CASE WHEN rs.status = 'CONFIRME' THEN r.nb_place ELSE 0 END), 0) AS places_restantes,
    -- Liste des clients
    STRING_AGG(DISTINCT CASE WHEN rs.status = 'CONFIRME' THEN c.nom ELSE NULL END, ', ') AS clients_confirmes
FROM voyage v
JOIN trajet t ON v.id_trajet = t.id
JOIN gare_routiere gd ON t.gare_depart = gd.id
JOIN gare_routiere ga ON t.gare_arrivee = ga.id
JOIN voyage_details vd ON v.id = vd.id_voyage
JOIN voiture vo ON vd.id_voiture = vo.id
JOIN type_voyage tv ON vd.id_type_voyage = tv.id
LEFT JOIN reservation r ON vd.id = r.id_voyage_details
LEFT JOIN reservation_status rs ON r.id_status = rs.id
LEFT JOIN client c ON r.id_client = c.id
GROUP BY 
    v.id, gd.ville, ga.ville, v.date_voyage, 
    vd.heure_depart, vo.immatricule, tv.description, 
    vo.nb_place, vd.id
ORDER BY v.date_voyage, vd.heure_depart;




-- Option 1: Avec une série générée (si les sièges sont numérotés de 1 à nb_place)
SELECT s.numero_place
FROM generate_series(1, (
    SELECT vo.nb_place 
    FROM voyage_details vd
    JOIN voiture vo ON vd.id_voiture = vo.id
    WHERE vd.id = 12
)) AS s(numero_place)
WHERE s.numero_place NOT IN (
    SELECT rs.numero_place
    FROM reservation r
    JOIN reservation_siege rs ON r.id = rs.id_reservation
    WHERE r.id_voyage_details = 12
    AND r.id_status != (SELECT id FROM reservation_status WHERE status = 'ANNULE')
);



