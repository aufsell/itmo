-- dropping enum types
DROP TYPE IF EXISTS nationality_enum CASCADE;
DROP TYPE IF EXISTS planet_enum CASCADE;

-- dropping tables
DROP TABLE IF EXISTS Communication_channel CASCADE;
DROP TABLE IF EXISTS Employees CASCADE;
DROP TABLE IF EXISTS Human CASCADE;
DROP TABLE IF EXISTS human_origin CASCADE;
DROP TABLE IF EXISTS Planet CASCADE;
DROP TABLE IF EXISTS Planet_type CASCADE;
DROP TABLE IF EXISTS Ship CASCADE;
DROP TABLE IF EXISTS Ship_type CASCADE;

-- dropping domains
DROP DOMAIN IF EXISTS positive_integer CASCADE;
DROP DOMAIN IF EXISTS positive_decimal CASCADE;
DROP DOMAIN IF EXISTS max_speed_constraint CASCADE;
DROP DOMAIN IF EXISTS range_constraint CASCADE;

-- creating enums
CREATE TYPE nationality_enum AS ENUM ('American', 'British', 'Canadian', 'Chinese', 'French', 'German', 'Indian', 'Japanese', 'Russian', 'Spanish');
CREATE TYPE planet_enum AS ENUM ('Rocky planets', 'Gas giants', 'Ice giants', 'Dwarf planets', 'Planets in the ocean', 'Interstellar planets', 'Brown', 'Spanish');

-- create domains
CREATE DOMAIN positive_integer AS INTEGER CHECK (VALUE > 0);
CREATE DOMAIN positive_decimal AS DECIMAL CHECK (VALUE > 0);
CREATE DOMAIN max_speed_constraint AS positive_integer;
CREATE DOMAIN range_constraint AS positive_integer;

-- creating tables
CREATE TABLE IF NOT EXISTS Ship_type (
    id SERIAL PRIMARY KEY,
    ship_type TEXT NOT NULL,
    capacity INTEGER NOT NULL,
    max_speed max_speed_constraint NOT NULL,
    range range_constraint NOT NULL
);
CCREATE TABLE IF NOT EXISTS Ship (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    ship_type INTEGER REFERENCES Ship_type (id) NOT NULL,
    connect_earth BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS Planet_type (
    id SERIAL PRIMARY KEY,
    type_planet planet_enum NOT NULL,
    type_of_surface TEXT NOT NULL,
    temperature TEXT NOT NULL
);
CREATE TABLE IF NOT EXISTS Planet (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    remoteness INTEGER NOT NULL,
    weight INTEGER NOT NULL,
    satelittes_number INTEGER NOT NULL,
    type_planet_id INTEGER REFERENCES Planet_type (id) NOT NULL
);
CREATE TABLE IF NOT EXISTS Communication_channel (
    Ship_id INTEGER REFERENCES Ship (id) NOT NULL,
    Planet_id INTEGER REFERENCES Planet (id) NOT NULL,
    number_of_channels INTEGER NOT NULL,
    warranty_time INTEGER NOT NULL,
    PRIMARY KEY (Ship_id, Planet_id)
);
CREATE TABLE IF NOT EXISTS human_origin (
    id SERIAL PRIMARY KEY,
    country TEXT NOT NULL,
    type_of_government TEXT NOT NULL,
    city TEXT NOT NULL
);
CREATE TABLE IF NOT EXISTS Human (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    lastname TEXT NOT NULL,
    age INTEGER NOT NULL,
    nationality nationality_enum NOT NULL,
    human_origin_id INTEGER REFERENCES human_origin (id) NOT NULL
);
CREATE TABLE IF NOT EXISTS Employees (
    Ship_id INTEGER REFERENCES Ship (id) NOT NULL,
    Human_id INTEGER REFERENCES Human (id) NOT NULL,
    position TEXT NOT NULL,
    start_date TEXT NOT NULL,
    PRIMARY KEY (Ship_id, Human_id)
);
-- insert data into tables
INSERT INTO Ship_type (ship_type, capacity, max_speed, range)
VALUES
    ('Cargo Ship', 500, 50, 1000),
    ('Passenger Ship', 200, 40, 800),
    ('Exploration Ship', 100, 60, 1500);

INSERT INTO Ship (name, ship_type, connect_earth)
VALUES
    ('Ship 1', 1, true),
    ('Ship 2', 2, false),
    ('Ship 3', 3, true);

INSERT INTO Planet_type (type_planet, type_of_surface, temperature)
VALUES
    ('Rocky planets', 'Solid', 'Varies'),
    ('Gas giants', 'Gas', 'High'),
    ('Ice giants', 'Ice and Gas', 'Very Cold'),
    ('Dwarf planets', 'Solid and Ice', 'Cold'),
    ('Planets in the ocean', 'Water', 'Moderate'),
    ('Interstellar planets', 'Varies', 'Varies'),
    ('Brown', 'Varies', 'Varies'),
    ('Spanish', 'Varies', 'Varies');

INSERT INTO Planet (name, remoteness, weight, satelittes_number, type_planet_id)
VALUES
    ('Earth', 1, 100, 1, 1),
    ('Mars', 2, 90, 2, 1),
    ('Jupiter', 5, 1000, 79, 2);

INSERT INTO Communication_channel (Ship_id, Planet_id, number_of_channels, warranty_time)
VALUES
    (1, 1, 10, 2),
    (2, 1, 5, 1),
    (3, 2, 8, 3);

INSERT INTO human_origin (country, type_of_government, city)
VALUES
    ('USA', 'Democratic', 'New York'),
    ('Russia', 'Federal Republic', 'Moscow'),
    ('China', 'Communist', 'Beijing');

INSERT INTO Human (name, lastname, age, nationality, human_origin_id)
VALUES
    ('John', 'Doe', 30, 'American', 1),
    ('Ivan', 'Ivanov', 35, 'Russian', 2),
    ('Li', 'Chen', 28, 'Chinese', 3);

INSERT INTO Employees (Ship_id, Human_id, position, start_date)
VALUES
    (1, 1, 'Captain', '2022-01-01'),
    (1, 2, 'Engineer', '2022-02-01'),
    (2, 3, 'Pilot', '2022-03-01');
 
