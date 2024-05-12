CREATE TABLE _user
(
    id         SERIAL PRIMARY KEY,
    fullName   VARCHAR(255)        NOT NULL,
    username   VARCHAR(255) UNIQUE NOT NULL,
    password   VARCHAR(255)        NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
