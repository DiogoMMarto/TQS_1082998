CREATE TABLE employee (
  employeeId BIGSERIAL PRIMARY KEY,
  name varchar(255),
  company varchar(255)
);

INSERT INTO employee (name, company) VALUES ('John Doe', 'ABC Company');
INSERT INTO employee (name, company) VALUES ('Jane Smith', 'XYZ Corporation');