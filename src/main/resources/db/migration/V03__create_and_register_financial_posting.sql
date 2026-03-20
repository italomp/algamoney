CREATE TABLE financial_posting (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(50) NOT NULL,
    expiration_date DATE NOT NULL,
    payment_date DATE,
    price DECIMAL(10,2) NOT NULL,
    note VARCHAR(100),
    type VARCHAR(20) NOT NULL,
    category_id BIGINT(20) NOT NULL,
    person_id BIGINT(20) NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category(id),
    FOREIGN KEY (person_id) REFERENCES person(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO financial_posting
(description, expiration_date, payment_date, price, note, type, category_id, person_id)
VALUES
('Salary March', '2026-03-05', '2026-03-05', 8500.00, 'Monthly salary payment', 'INCOME', 1, 1),
('Office Rent', '2026-03-10', '2026-03-08', 3200.00, 'Company headquarters rent', 'INCOME', 2, 1),
('Internet Bill', '2026-03-15', NULL, 180.50, 'Business internet provider', 'EXPENSE', 3, 1),
('Freelance Project', '2026-03-20', '2026-03-21', 2500.00, 'Website development project', 'INCOME', 1, 2),
('Electricity Bill', '2026-03-18', '2026-03-17', 420.75, 'Office electricity consumption', 'EXPENSE', 3, 1),
('Software Subscription', '2026-03-25', NULL, 99.90, 'Monthly SaaS subscription', 'EXPENSE', 2, 2),
('Consulting Service', '2026-03-28', '2026-03-29', 4700.00, 'IT consulting service', 'INCOME', 1, 2),
('Marketing Campaign', '2026-03-22', NULL, 1500.00, 'Online ads campaign', 'EXPENSE', 1, 1),
('Equipment Purchase', '2026-03-12', '2026-03-12', 2750.00, 'New laptops for team', 'EXPENSE', 3, 2),
('Bonus Payment', '2026-03-30', '2026-03-30', 1200.00, 'Performance bonus', 'INCOME', 1, 1);