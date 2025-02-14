CREATE TABLE categories(
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    creation_date TIMESTAMP,
    alter_date TIMESTAMP
);
CREATE INDEX cat_name_idx ON categories (name);