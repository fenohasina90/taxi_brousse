


create table voiture (
    id serial primary key,
    immatricule varchar(20) not null unique,
    nb_place int not null check (nb_place > 0),
    created_at timestamp default current_timestamp
);

create table gare_routiere (
    id serial primary key,
    nom varchar(100) not null,
    ville varchar(100) not null,
    contact varchar(50)
);

create table trajet (
    id serial primary key,
    gare_depart int not null references gare_routiere(id),
    gare_arrivee int not null references gare_routiere(id),
    distance_km decimal(9,1),
    estimation_heure int,
    check (gare_depart <> gare_arrivee),
    unique (gare_depart, gare_arrivee)
);


-- Planification voyages

create table type_voyage (
    id serial primary key,
    description varchar(100) not null unique
);


create table voyage(
    id serial primary key,
    id_trajet int not null references trajet(id),
    date_voyage date not null,
    created_at timestamp default current_timestamp
);

create table voyage_details(
    id serial primary key,
    id_voyage int not null references voyage(id),
    id_voiture int not null references voiture(id),
    id_type_voyage int not null references type_voyage(id),
    heure_depart time not null,
    unique (id_voyage, id_voiture)
);

create table voyage_details_place_type (
    id serial primary key,
    id_voyage_details int not null references voyage_details(id),
    numero_place int not null,
    id_type_voyage int not null references type_voyage(id),
    unique (id_voyage_details, numero_place)
);


-- Tarif

create table tarif_actuel (
    id serial primary key,
    id_trajet int not null references trajet(id),
    id_type_voyage int not null references type_voyage(id),
    montant decimal(15,2) not null check (montant > 0),
    created_at timestamp default current_timestamp
);

create table historique_tarif (
    id serial primary key,
    ancien_montant decimal(15,2) not null,
    nouveau_montant decimal(15,2) not null,
    id_trajet int not null references trajet(id),
    id_type_voyage int not null references type_voyage(id),
    created_at timestamp default current_timestamp
);

-- client
create table client (
    id serial primary key,
    nom varchar(200),
    contact varchar(20), 
    created_at timestamp default current_timestamp
);

-- Reservation
create table reservation_status (
    id serial primary key,
    status varchar(50) not null CHECK (status IN ('EN ATTENTE', 'COMPLET', 'CONFIRME', 'ANNULE'))
);

create table reservation (
    id serial primary key,
    id_voyage_details int not null references voyage_details(id),
    id_client int not null references client(id),
    nb_place int not null check (nb_place > 0),
    total_amount decimal(15,2) not null check (total_amount >= 0),
    id_status int not null references reservation_status(id),
    date_reservation timestamp,
    created_at timestamp default current_timestamp
);

create table reservation_siege (
    id serial primary key,
    id_reservation int not null references reservation(id),
    numero_place int not null
);

-- Date_depart : na izy client tsotra amzao na izy client navita reservation
create table voyage_passager(
    id serial primary key,
    id_voyage_details int not null references voyage_details(id),
    id_client int not null references client(id),
    id_reservation int not null references reservation(id)
);

create table voyage_passager_details (
    id serial primary key,
    id_voyage_passager int not null references voyage_passager(id),
    numero_place int not null
);

-- Paiement
create table mode_paiement (
    id serial primary key,
    mode varchar(100) not null
);

create table paiement (
    id serial primary key,
    id_voyage_passager int not null references voyage_passager(id),
    id_mode_paiement int not null references mode_paiement(id),
    montant decimal(15,2) not null check (montant > 0),
    date_paiement timestamp default current_timestamp
);

CREATE INDEX idx_voiture_created_at ON voiture(created_at);
CREATE INDEX idx_gare_routiere_nom ON gare_routiere(nom);
CREATE INDEX idx_gare_routiere_ville ON gare_routiere(ville);
CREATE INDEX idx_trajet_gare_depart ON trajet(gare_depart);
CREATE INDEX idx_trajet_gare_arrivee ON trajet(gare_arrivee);
CREATE INDEX idx_voyage_id_trajet ON voyage(id_trajet);
CREATE INDEX idx_voyage_date ON voyage(date_voyage);
CREATE INDEX idx_voyage_created_at ON voyage(created_at);
CREATE INDEX idx_voyage_details_voyage ON voyage_details(id_voyage);
CREATE INDEX idx_voyage_details_voiture ON voyage_details(id_voiture);
CREATE INDEX idx_voyage_details_type ON voyage_details(id_type_voyage);
CREATE INDEX idx_voyage_details_heure ON voyage_details(heure_depart);
CREATE INDEX idx_tarif_actuel_trajet ON tarif_actuel(id_trajet);
CREATE INDEX idx_tarif_actuel_type ON tarif_actuel(id_type_voyage);
CREATE UNIQUE INDEX idx_tarif_actuel_unique 
ON tarif_actuel(id_trajet, id_type_voyage);
CREATE INDEX idx_hist_tarif_trajet ON historique_tarif(id_trajet);
CREATE INDEX idx_hist_tarif_type ON historique_tarif(id_type_voyage);
CREATE INDEX idx_hist_tarif_date ON historique_tarif(created_at);
CREATE INDEX idx_client_nom ON client(nom);
CREATE INDEX idx_client_contact ON client(contact);
CREATE INDEX idx_client_created_at ON client(created_at);
CREATE UNIQUE INDEX idx_reservation_status_unique 
ON reservation_status(status);
CREATE INDEX idx_reservation_voyage_details ON reservation(id_voyage_details);
CREATE INDEX idx_reservation_client ON reservation(id_client);
CREATE INDEX idx_reservation_status ON reservation(id_status);
CREATE INDEX idx_reservation_date ON reservation(date_reservation);
CREATE INDEX idx_reservation_created_at ON reservation(created_at);
CREATE INDEX idx_reservation_siege_reservation 
ON reservation_siege(id_reservation);
CREATE UNIQUE INDEX idx_reservation_siege_unique
ON reservation_siege(id_reservation, numero_place);
CREATE INDEX idx_voyage_passager_voyage_details 
ON voyage_passager(id_voyage_details);
CREATE INDEX idx_voyage_passager_client 
ON voyage_passager(id_client);
CREATE INDEX idx_voyage_passager_reservation 
ON voyage_passager(id_reservation);
CREATE INDEX idx_voyage_passager_details_passager 
ON voyage_passager_details(id_voyage_passager);
CREATE UNIQUE INDEX idx_voyage_passager_details_unique
ON voyage_passager_details(id_voyage_passager, numero_place);
CREATE UNIQUE INDEX idx_mode_paiement_mode 
ON mode_paiement(mode);
CREATE INDEX idx_paiement_passager 
ON paiement(id_voyage_passager);
CREATE INDEX idx_paiement_mode 
ON paiement(id_mode_paiement);
CREATE INDEX idx_paiement_date 
ON paiement(date_paiement);