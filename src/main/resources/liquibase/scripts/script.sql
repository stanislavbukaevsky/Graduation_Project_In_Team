--liquibase formatted sql

--changeset :1
ALTER TABLE images DROP COLUMN data;

--changeset :2
ALTER TABLE images
    ADD COLUMN data bytea;