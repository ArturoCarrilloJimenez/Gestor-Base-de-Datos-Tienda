DROP DATABASE IF EXISTS ventas;
CREATE DATABASE ventas;
USE ventas;

CREATE TABLE cliente (
	id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido1 VARCHAR(50) NOT NULL,
    apellido2 VARCHAR(50) NOT NULL,
    telefono VARCHAR(12) NOT NULL
);

CREATE TABLE producto (
	id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(500) NOT NULL,
    pvp DECIMAL(10,2) NOT NULL
);

CREATE TABLE compras (
	id_cliente INT UNSIGNED NOT NULL,
    id_producto INT UNSIGNED NOT NULL,
    cantidad INT UNSIGNED NOT NULL,
    fecha_hora DATETIME NOT NULL DEFAULT NOW(),
    
    PRIMARY KEY (id_cliente,id_producto),
    FOREIGN KEY (id_cliente) REFERENCES cliente (id),
    FOREIGN KEY (id_producto) REFERENCES producto (id)
);

INSERT INTO cliente (nombre, apellido1, apellido2, telefono) VALUES ('Juan', 'Pérez', 'Gómez', '1234567890');
INSERT INTO cliente (nombre, apellido1, apellido2, telefono) VALUES ('María', 'López', 'Fernández', '0987654321');
INSERT INTO cliente (nombre, apellido1, apellido2, telefono) VALUES ('Carlos', 'Jiménez', 'Ruiz', '1122334455');
INSERT INTO cliente (nombre, apellido1, apellido2, telefono) VALUES ('Ana', 'Martínez', 'Morales', '5566778899');
INSERT INTO cliente (nombre, apellido1, apellido2, telefono) VALUES ('Luis', 'García', 'Hernández', '2233445566');

INSERT INTO producto (nombre, descripcion, pvp) VALUES ('Televisor', 'Televisor 4K de 55 pulgadas', 899.99);
INSERT INTO producto (nombre, descripcion, pvp) VALUES ('Lavadora', 'Lavadora de carga frontal 10kg', 599.99);
INSERT INTO producto (nombre, descripcion, pvp) VALUES ('Smartphone', 'Smartphone 6.5 pulgadas, 128GB', 499.99);
INSERT INTO producto (nombre, descripcion, pvp) VALUES ('Laptop', 'Laptop 15 pulgadas, 8GB RAM, 1TB SSD', 1099.99);
INSERT INTO producto (nombre, descripcion, pvp) VALUES ('Frigorífico', 'Frigorífico con congelador, 340L', 789.99);

INSERT INTO compras (id_cliente, id_producto, cantidad) VALUES (1, 1, 2);
INSERT INTO compras (id_cliente, id_producto, cantidad) VALUES (2, 3, 1);
INSERT INTO compras (id_cliente, id_producto, cantidad) VALUES (3, 2, 1);
INSERT INTO compras (id_cliente, id_producto, cantidad) VALUES (4, 5, 1);
INSERT INTO compras (id_cliente, id_producto, cantidad) VALUES (5, 4, 1);

SELECT *
FROM cliente;

SELECT *
FROM producto;

SELECT *
FROM compras;

TRUNCATE TABLE compras;