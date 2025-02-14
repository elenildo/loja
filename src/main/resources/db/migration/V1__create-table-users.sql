CREATE TABLE users(
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    lastname VARCHAR(50),
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    roles VARCHAR[] NOT NULL,
    creation_date TIMESTAMP,
    alter_date TIMESTAMP
);
CREATE INDEX username_idx ON users (username);