CREATE TABLE ingredient
(
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    name            VARCHAR(255)   NOT NULL,
    amount          DECIMAL(10, 2) NOT NULL,
    measure_unit_id BIGINT         NOT NULL,
    created_at      TIMESTAMP      NOT NULL,
    created_by      VARCHAR(255)   NOT NULL,
    updated_at      TIMESTAMP    DEFAULT NULL,
    updated_by      VARCHAR(255) DEFAULT NULL,
    version         BIGINT         NOT NULL
);

ALTER TABLE ingredient
    ADD CONSTRAINT fk_measure_unit_id_in_ingredient_table
        FOREIGN KEY (measure_unit_id) REFERENCES measure_unit (id);