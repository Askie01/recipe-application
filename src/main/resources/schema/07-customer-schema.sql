CREATE TABLE customer
(
    id            BIGINT PRIMARY KEY AUTO_INCREMENT,
    username      VARCHAR(255) NOT NULL,
    password      VARCHAR(255) NOT NULL,
    first_name    VARCHAR(255) NOT NULL,
    last_name     VARCHAR(255) NOT NULL,
    image         LONGBLOB,
    email         VARCHAR(255) NOT NULL,
    mobile_number VARCHAR(255),
    created_at    TIMESTAMP    NOT NULL,
    created_by    VARCHAR(255) NOT NULL,
    updated_at    TIMESTAMP    DEFAULT NULL,
    updated_by    VARCHAR(255) DEFAULT NULL,
    version       BIGINT       NOT NULL
);