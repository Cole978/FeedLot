CREATE TABLE animales (
  Id int(11) NOT NULL AUTO_INCREMENT,
  tag int(11) NOT NULL DEFAULT '0',
  fecha_ingreso date NOT NULL,
  raza varchar(255) NOT NULL,
  id_proveedores int(11) NOT NULL,
  id_categorias int(11) NOT NULL,
  precio_compra double(8,2) NOT NULL DEFAULT '0.00',
  precio_venta double(8,2) DEFAULT NULL,
  descripcion varchar(100) DEFAULT NULL,
  estado enum('ACTIVO','INACTIVO','ENFERMO','MUERTO') NOT NULL DEFAULT 'ACTIVO',
  PRIMARY KEY (Id),
  KEY Animal_tiene_categoria (id_categorias),
  KEY animal_tiene_proveedor (id_proveedores),
  CONSTRAINT Animal_tiene_categoria FOREIGN KEY (id_categorias) REFERENCES categorias (Id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT animal_tiene_proveedor FOREIGN KEY (id_proveedores) REFERENCES proveedores (Id) ON DELETE NO ACTION ON UPDATE NO ACTION
) AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;