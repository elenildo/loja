CREATE TABLE products(
    id SERIAL PRIMARY KEY,
    title VARCHAR(50) UNIQUE NOT NULL,
    description TEXT,
    price DECIMAL(10,2),
    category_id BIGINT NOT NULL,
    creation_date TIMESTAMP,
    alter_date TIMESTAMP,

    CONSTRAINT prod_cat_fk
         FOREIGN KEY (category_id)
         REFERENCES categories (id)
);
CREATE INDEX prod_title_idx ON products (title);