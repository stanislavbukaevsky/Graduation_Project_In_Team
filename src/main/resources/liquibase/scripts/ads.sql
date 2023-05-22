-- liquibase formatted sql

CREATE TABLE users
(
    id         SERIAL NOT NULL PRIMARY KEY,
    email      TEXT,
    first_name TEXT,
    last_name  TEXT,
    phone      TEXT,
    reg_date   DATE   NOT NULL,
    image_id   INTEGER,
    password   TEXT,
    username   TEXT
);


create table ads
(
    id          SERIAL PRIMARY KEY,
    title       TEXT,
    price       INTEGER,
    description TEXT,
    image_id    INTEGER,
    author_id   INTEGER REFERENCES users (id)
);


CREATE TABLE avatars
(
    id      SERIAL PRIMARY KEY,
    user_id INTEGER,
    path    TEXT
);

CREATE TABLE posters
(
    id     SERIAL PRIMARY KEY,
    ads_id INTEGER,
    path   TEXT
);


Create TABLE comments
(
    id         SERIAL NOT NULL PRIMARY KEY,
    text       TEXT,
    created_at DATE,
    author_id  INTEGER REFERENCES users (id),
    ads_id     INTEGER REFERENCES ads (id)
);


ALTER TABLE users ADD COLUMN enabled BOOLEAN DEFAULT TRUE;

CREATE TABLE authorities
(
    id        SERIAL PRIMARY KEY,
    username  varchar(30) NOT NULL UNIQUE,
    authority varchar(30) NOT NULL
);


ALTER TABLE comments
    ALTER COLUMN created_at TYPE TIMESTAMP USING created_at::TIMESTAMP;