DROP TABLE IF EXISTS reg_card;
DROP TABLE IF EXISTS document;
DROP TABLE IF EXISTS catalog;

CREATE TABLE catalog (
    catalog_id SERIAL,
    catalog_name TEXT NOT NULL,
    PRIMARY KEY(catalog_id)
);

CREATE TABLE document (
	document_id SERIAL,
	catalog_id INTEGER NOT NULL,
	document_name TEXT NOT NULL,
	author VARCHAR(200) NOT NULL,
	content BYTEA NOT NULL,
	PRIMARY KEY(document_id),
    FOREIGN KEY (catalog_id) REFERENCES catalog(catalog_id) ON DELETE RESTRICT
);

CREATE TABLE reg_card (
	reg_card_id SERIAL,
	documented INTEGER NOT NULL,
	document_intro_number VARCHAR(100) NOT NULL,
	document_extern_number VARCHAR(100),
	date_intro TIMESTAMP NOT NULL,
	date_extern TIMESTAMP,
	PRIMARY KEY(reg_card_id),
	FOREIGN KEY (documented) REFERENCES document(document_id) ON DELETE RESTRICT
);

INSERT INTO catalog VALUES (1, 'Catalog');

CREATE TABLE users (
    user_id SERIAL,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    PRIMARY KEY(user_id)
);

CREATE TABLE roles (
    role_id SERIAL,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY(role_id)
);

INSERT INTO roles VALUES (1, 'ROLE_USER');
INSERT INTO roles VALUES (2, 'ROLE_ADMIN');