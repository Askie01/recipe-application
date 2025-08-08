CREATE TABLE recipe_category
(
    recipe_id   BIGINT NOT NULL,
    category_id BIGINT NOT NULL
);

ALTER TABLE recipe_category
    ADD CONSTRAINT pk_recipe_category
        PRIMARY KEY (recipe_id, category_id);

ALTER TABLE recipe_category
    ADD CONSTRAINT fk_recipe_id_in_recipe_category_table
        FOREIGN KEY (recipe_id) REFERENCES recipe (id);

ALTER TABLE recipe_category
    ADD CONSTRAINT fk_category_id_in_recipe_category_table
        FOREIGN KEY (category_id) REFERENCES category (id);