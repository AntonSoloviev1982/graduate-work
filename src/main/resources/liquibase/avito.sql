-- liquibase formatted sql
--changeset alexander:create_user
DROP TABLE IF EXISTS public.user;
CREATE TABLE public.user
(
    id             SERIAL PRIMARY KEY,
    username       VARCHAR(32)     NOT NULL UNIQUE,
    password       VARCHAR(16)     NOT NULL,
    first_name     VARCHAR(16)     NOT NULL,
    last_name      VARCHAR(16),
    phone          VARCHAR(16),
    role           INTEGER         NOT NULL,
    image          VARCHAR(40)
);
INSERT INTO users(id, username, password, first_name, last_name, phone, role, image)
VALUES (1, 'user@gmail.com', '$2a$12$XvqqmuYW/nXxUmLZvpl1AuJEDJO96BWfKLDlFkYNiQdiTXQEJ5CQC',
        'Ivan', 'Ivanov', '+7956-456-78-12', 'USER', null);
        
--changeset alexander:create_ad
DROP TABLE IF EXISTS ad;
CREATE TABLE ad
(
    id             SERIAL PRIMARY KEY,
    user_id        INTEGER         NOT NULL,
    title          VARCHAR(32)     NOT NULL,
    price          INTEGER         NOT NULL,
    description    VARCHAR(64)     NOT NULL,
    image          VARCHAR(40),
    FOREIGN KEY (user_id) REFERENCES public.user (id)
);

--changeset alexander:create_comment
DROP TABLE IF EXISTS comment;
CREATE TABLE comment
(
    id             SERIAL PRIMARY KEY,
    text           VARCHAR(64)      NOT NULL,
    created_at     TIMESTAMP        NOT NULL,
    ad_id          INTEGER          NOT NULL,
    user_id        INTEGER          NOT NULL,
    FOREIGN KEY (ad_id) REFERENCES ad (id),
    FOREIGN KEY (user_id) REFERENCES public.user (id)
);

--changeset pavel:create_image
DROP TABLE IF EXISTS image;
CREATE TABLE image
(
    id             SERIAL PRIMARY KEY,
    image          BLOB NOT NULL,
    created_at     TIMESTAMP        NOT NULL,
    ad_id          INTEGER          NOT NULL,
    user_id        INTEGER          NOT NULL,
    FOREIGN KEY (ad_id) REFERENCES ad (id),
    FOREIGN KEY (user_id) REFERENCES public.user (id)
);
