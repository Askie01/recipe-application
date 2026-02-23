CREATE TABLE customer_recipe
(
    customer_id BIGINT NOT NULL,
    recipe_id   BIGINT NOT NULL,
    PRIMARY KEY (customer_id, recipe_id)
);

ALTER TABLE customer_recipe
    ADD CONSTRAINT fk_customer_id_in_customer_recipe_table
        FOREIGN KEY (customer_id) REFERENCES customer (id);

ALTER TABLE customer_recipe
    ADD CONSTRAINT fk_recipe_id_in_customer_recipe_table
        FOREIGN KEY (recipe_id) REFERENCES recipe (id);