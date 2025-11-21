CREATE TABLE employees (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    surname VARCHAR(50),
    mail VARCHAR(100) UNIQUE,
    corporation VARCHAR(100),
    position VARCHAR(50),
    salary FLOAT,
    status VARCHAR(20),
    photo VARCHAR(255)
);