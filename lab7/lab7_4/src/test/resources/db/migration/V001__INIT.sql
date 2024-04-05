CREATE TABLE cars (
  carId BIGSERIAL PRIMARY KEY,
  maker varchar(255),
  model varchar(255)
);

INSERT INTO cars (maker, model) VALUES
    ('Toyota', 'Camry'),
    ('Honda', 'Accord'),
    ('Ford', 'Fusion'),
    ('Chevrolet', 'Malibu'),
    ('Nissan', 'Altima'),
    ('BMW', '3 Series'),
    ('Mercedes-Benz', 'C-Class'),
    ('Audi', 'A4'),
    ('Tesla', 'Model 3'),
    ('Volkswagen', 'Passat');