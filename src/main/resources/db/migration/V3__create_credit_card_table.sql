-- V3__create_credit_card_table.sql

CREATE TABLE credit_cards
(
    id                    TEXT PRIMARY KEY,
    user_id               TEXT     NOT NULL,
    owner_id              TEXT     NOT NULL,
    name                  TEXT     NOT NULL,
    "number"              TEXT,
    cvc                   TEXT,
    pin                   TEXT,
    expiry_date           TEXT,
    notes                 TEXT,
    favorite              INTEGER DEFAULT 0,
    deleted               INTEGER DEFAULT 0,
    creation_date_time    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by            TEXT,
    last_update_date_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_updated_by       TEXT,
    version               INTEGER DEFAULT 1,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (owner_id) REFERENCES users (id)
);