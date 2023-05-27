--liquibase formatted sql

--changeset sbukaevsky:1
ALTER TABLE images DROP COLUMN data;

--changeset sbukaevsky:2
ALTER TABLE images
    ADD COLUMN data bytea;