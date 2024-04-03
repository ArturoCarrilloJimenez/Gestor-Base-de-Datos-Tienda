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