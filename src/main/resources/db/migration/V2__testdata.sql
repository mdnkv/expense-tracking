INSERT INTO users_user (email, password_hash, first_name, last_name) values
    ('el96grafiti@auhit.com', '$2a$10$crAXJ0bPQaA0No7DvvXTj.vZiKP6kcSI846Y/R2YwDFoNx0km.4vy', 'Marko', 'Schwarz'),
    ('tysonusarmy@trimart.xyz', '$2a$10$Qksbtz0hy00.SSm0KTeQ9eGEfu3.8RcUh1KCsToJhwgk4o61qtdAC', 'Christian', 'Keller'),
    ('chrihurs@likebaiviet.com', '$2a$10$8CdLtdD064Mep.CRF1lSY.FKH3EM3W4WDakNDNMAxZHbzaVJYbwl2', 'Vanessa', 'Hoffman'),
    ('tdstasa@nelcoapps.com', '$2a$10$GpR60PKd2HDLeNXsLSGw1.5nxnwmGEM9pu47tHU4axwlLkIRzgB9y', 'Jonas', 'Peter'),
    ('osamu0926@shopcobe.com', '$2a$10$NJUkdYpUwRN3.wZUcL6KluH0fCPuR/1sxVqkdtEzUBQqwizdvIQBy', 'Anke', 'Miller');

INSERT INTO accounts_account (account_name, account_type, user_id) values
    ('My cash account', 'CASH', 1),
    ('My credit card', 'CREDIT_CARD', 1),
    ('My bank account', 'BANK_ACCOUNT', 1),
    ('My cash account', 'CASH', 2),
    ('My credit card', 'CREDIT_CARD', 2),
    ('My bank account', 'BANK_ACCOUNT', 2),
    ('My cash account', 'CASH', 3),
    ('My credit card', 'CREDIT_CARD', 3),
    ('My bank account', 'BANK_ACCOUNT', 3),
    ('My cash account', 'CASH', 4),
    ('My credit card', 'CREDIT_CARD', 4),
    ('My bank account', 'BANK_ACCOUNT', 4),
    ('My cash account', 'CASH', 5),
    ('My credit card', 'CREDIT_CARD', 5),
    ('My bank account', 'BANK_ACCOUNT', 5);

INSERT INTO operations_operation
(user_id, account_id, operation_type, operation_currency, operation_description, operation_amount, operation_date)
values
    (1, 3, 'INCOME', 'EUR', 'Received salary for October', 1500.00, '2024-11-10'),
    (1, 1, 'EXPENSE', 'EUR', 'Paid rent for my apartment', 3500.00, '2024-11-02'),
    (1, 1, 'INCOME', 'EUR', 'Sold my old laptop', 320.00, '2024-11-05'),
    (1, 2, 'EXPENSE', 'EUR', 'Bought food', 100.00, '2024-11-15'),
    (1, 1, 'INCOME', 'EUR', 'Christian paid a debt', 120.00, '2024-11-17'),
    (2, 6, 'EXPENSE', 'EUR', 'Paid my rent for October', 1500.00, '2024-11-02'),
    (2, 6, 'INCOME', 'EUR', 'Received payment from client logo design', 65.95, '2024-11-16'),
    (2, 6, 'INCOME', 'EUR', 'Received payment from client for website', 115.00, '2024-11-12'),
    (2, 4, 'EXPENSE', 'EUR', 'Bought a coffee', 3.50, '2024-11-02'),
    (2, 4, 'EXPENSE', 'EUR', 'Paid my debt to Marko', 120.00, '2024-11-17');