CREATE TABLE IF NOT EXISTS connection (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    available_seats integer NOT NULL,
    bus_name character varying(255),
    date date,
    duration time(6) without time zone,
    end_city character varying(255),
    number_of_seats integer NOT NULL,
    price double precision NOT NULL,
    seat_map boolean[],
    starting_city character varying(255)
);

CREATE TABLE IF NOT EXISTS person (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    name character varying(255)
);

CREATE TABLE IF NOT EXISTS reservation (
    id uuid NOT NULL PRIMARY KEY,
    reservation_date date,
    status character varying(255),
    person_id BIGSERIAL NOT NULL REFERENCES person(id)
);

CREATE TABLE IF NOT EXISTS trip(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    assigned_seat_number integer NOT NULL,
    status character varying(255),
    connection_id BIGSERIAL NOT NULL REFERENCES connection(id),
    reservation_id uuid REFERENCES reservation(id)
);
