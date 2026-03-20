CREATE TABLE person (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    active TINYINT(1),
    address_street VARCHAR(255),
    address_number VARCHAR(50),
    address_neighbor VARCHAR(100),
    address_city VARCHAR(100),
    address_state VARCHAR(50),
    address_cep VARCHAR(20)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO person
(name, active, address_street, address_number, address_neighbor, address_city, address_state, address_cep)
VALUES
('João Silva', 1, 'Rua das Flores', '123', 'Centro', 'São Paulo', 'SP', '01000-000'),
('Maria Oliveira', 1, 'Av. Brasil', '456', 'Jardim América', 'Rio de Janeiro', 'RJ', '20000-000'),
('Carlos Souza', 0, 'Rua A', '789', 'Copacabana', 'Rio de Janeiro', 'RJ', '22000-000');