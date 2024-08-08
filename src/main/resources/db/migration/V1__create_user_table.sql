-- V1__create_users_table.sql

CREATE TABLE users
(
    id TEXT NOT NULL PRIMARY KEY,
    user_name             TEXT NOT NULL UNIQUE,
    email                 TEXT NOT NULL UNIQUE,
    password              TEXT NOT NULL,
    creation_date_time    TEXT    DEFAULT (datetime('now')),
    created_by            TEXT NOT NULL,
    last_update_date_time TEXT    DEFAULT (datetime('now')),
    last_updated_by       TEXT NOT NULL,
    version               INTEGER DEFAULT 0
);

CREATE UNIQUE INDEX idx_users_email ON users (email);
CREATE UNIQUE INDEX idx_users_username ON users (user_name);
