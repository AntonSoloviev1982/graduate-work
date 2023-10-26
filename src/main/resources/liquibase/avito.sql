-- liquibase formatted sql

--changeset alexander:create_users
DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    id             INTEGER   PRIMARY KEY AUTO_INCREMENT,
    user_name      TEXT      NOT NULL,
    first_name     TEXT      NOT NULL,
    last_name      TEXT,
    phone          TEXT      NOT NULL,
    role           BOOLEAN   NOT NULL,
    image          TEXT,
);

--changeset alexander:create_ad
DROP TABLE IF EXISTS ad;
CREATE TABLE ad
(
    id             INTEGER   PRIMARY KEY AUTO_INCREMENT,
    user_id        INTEGER   NOT NULL,
    title          TEXT      NOT NULL,
    price          INTEGER   NOT NULL,
    description    TEXT      NOT NULL,
    image          TEXT,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

--changeset alexander:create_comments
DROP TABLE IF EXISTS comments;
CREATE TABLE comments
(
    id             INTEGER   PRIMARY KEY AUTO_INCREMENT,
    text           TEXT      NOT NULL,
    created_at     TIMESTAMP   NOT NULL,
    ad_id          INTEGER   NOT NULL,
    user_id        INTEGER   NOT NULL,
    FOREIGN KEY (ad_id) REFERENCES ad (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);