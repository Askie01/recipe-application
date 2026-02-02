CREATE TABLE recipe_ingredient
(
    recipe_id     BIGINT NOT NULL,
    ingredient_id BIGINT NOT NULL
);

ALTER TABLE recipe_ingredient
    ADD CONSTRAINT pk_recipe_ingredient
        PRIMARY KEY (recipe_id, ingredient_id);

ALTER TABLE recipe_ingredient
    ADD CONSTRAINT fk_recipe_id_in_recipe_ingredient_table
        FOREIGN KEY (recipe_id) REFERENCES recipe (id);

ALTER TABLE recipe_ingredient
    ADD CONSTRAINT fk_ingredient_id_in_recipe_ingredient_table
        FOREIGN KEY (ingredient_id) REFERENCES ingredient (id)
            ON DELETE CASCADE;