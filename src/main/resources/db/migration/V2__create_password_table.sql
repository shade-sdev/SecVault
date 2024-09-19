-- V2__create_password_table.sql

CREATE TABLE passwords
(
    id                    TEXT PRIMARY KEY,
    user_id TEXT NOT NULL,
    name                  TEXT     NOT NULL,
    username              TEXT,
    email                 TEXT,
    password              TEXT     NOT NULL,
    website               TEXT,
    website_icon          TEXT,
    favorite              INTEGER           DEFAULT 0,
    deleted               INTEGER           DEFAULT 0,
    creation_date_time    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by            TEXT,
    last_update_date_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_updated_by       TEXT,
    version INTEGER DEFAULT 1,
    FOREIGN KEY (user_id) REFERENCES users (id)
);