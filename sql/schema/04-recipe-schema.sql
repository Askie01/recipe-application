CREATE TABLE recipe
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    image        LONGBLOB,
    name         VARCHAR(255)                    NOT NULL,
    description  VARCHAR(255)                    NOT NULL,
    difficulty   ENUM ('EASY', 'MEDIUM', 'HARD') NOT NULL,
    servings     DECIMAL(10, 2)                  NOT NULL,
    cooking_time INT                             NOT NULL,
    instructions VARCHAR(1000)                   NOT NULL,
    created_at   TIMESTAMP                       NOT NULL,
    created_by   VARCHAR(255)                    NOT NULL,
    updated_at   TIMESTAMP    DEFAULT NULL,
    updated_by   VARCHAR(255) DEFAULT NULL,
    version      BIGINT                          NOT NULL
);

ALTER TABLE recipe
    ADD FULLTEXT INDEX fulltext_index_recipe_name_description (name, description);