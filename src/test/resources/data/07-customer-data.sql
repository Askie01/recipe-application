INSERT INTO customer (username, password, first_name, last_name, image, email, mobile_number, created_at, created_by,
                      version)
VALUES ('user', '{noop}user', 'jan', 'kowalski', X'00', 'jan.kowalski@gmail.com', '123 456 789', now(), 'system', 0);

INSERT INTO customer (username, password, first_name, last_name, image, email, mobile_number, created_at, created_by,
                      version)
VALUES ('admin', '{noop}admin', 'adam', 'pawlowski', X'00', 'adam.pawlowski@gmail.com', '987 654 321', now(), 'system',
        0);

INSERT INTO customer (username, password, first_name, last_name, image, email, mobile_number, created_at, created_by,
                      version)
VALUES ('askie01', '{bcrypt}$2a$12$flBxjcsLvuDsV8F0C3vSJ.J4WVRT8Qq6AS2OS1pdB3wX9ARG7VxFq', 'askie', 'developer', X'00',
        'askie.developer@gmail.com', '111 111 111', now(), 'system', 0);