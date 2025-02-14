CREATE TABLE google_drive_config
(
    user_id    TEXT PRIMARY KEY,
    config_file BLOB,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);