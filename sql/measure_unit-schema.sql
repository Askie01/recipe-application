CREATE TABLE measure_unit
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(255) NOT NULL,
    created_at TIMESTAMP    NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    updated_at TIMESTAMP    DEFAULT NULL,
    updated_by VARCHAR(255) DEFAULT NULL
);