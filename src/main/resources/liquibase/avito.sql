-- liquibase formatted sql

-- changeset anton:create_users
DROP TABLE IF EXISTS state;
CREATE TABLE users
(
    id         INTEGER PRIMARY KEY,
    username   VARCHAR(30)  NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    first_name VARCHAR(30),
    last_name  VARCHAR(30),
    phone      VARCHAR(30),
    role       ENUM,
    image      VARCHAR(30)
);

INSERT INTO users(id, username, password, first_name, last_name, phone, role, image)
VALUES (1, 'user@gmail.com', '$2a$12$XvqqmuYW/nXxUmLZvpl1AuJEDJO96BWfKLDlFkYNiQdiTXQEJ5CQC',
        'Ivan', 'Ivanov', '+7956-456-78-12', 'USER', null);